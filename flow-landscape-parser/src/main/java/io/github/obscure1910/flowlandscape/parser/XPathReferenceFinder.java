package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinderProperties;
import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;
import io.github.obscure1910.flowlandscape.parser.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.github.obscure1910.flowlandscape.parser.PathFunctions.withXmlFiles;
import static io.github.obscure1910.flowlandscape.parser.StreamFunctions.asStream;
import static io.github.obscure1910.flowlandscape.parser.StreamFunctions.streamConcat;
import static io.github.obscure1910.flowlandscape.parser.XPathFunctions.withXPath;
import static io.github.obscure1910.flowlandscape.parser.XPathFunctions.withXPathNS;
import static java.util.stream.Collectors.toList;
import static javax.xml.xpath.XPathConstants.NODESET;

public class XPathReferenceFinder implements ReferenceFinder {

    private final Pattern lookupsInString = Pattern.compile("(?<=((Mule::|\\s|\\[|\\()lookup)(\\(\")).*(?=\")");
    private final ConnectionRegistry connectionRegistry = new KnownConnections();

    @Override
    public List<ConfigurationHolder> findReferences(ReferenceFinderProperties referenceFinderProperties) {
        List<ConfigurationHolder> chList = withXmlFiles(referenceFinderProperties.getSourceDirectory(), muleConfigurationFile -> {
            Document document = getDocument(muleConfigurationFile);
            List<FlowHolder> flows = getFlows(document)
                    .map(n -> {
                        List<ReferenceHolder> references = streamConcat(
                                getReferencesInText(n),
                                getReferencesInFlowRef(n),
                                getReferencesInAttribute(n),
                                getReferencesInDataweaveFile(n, referenceFinderProperties.getResourceDirectory())
                        ).collect(toList());
                        List<AsyncPublishHolder> asyncPublishHolders = getAsyncPublisher(n, document).collect(toList());
                        List<AsyncConsumeHolder> asyncConsumeHolders = getAsyncConsumer(n, document).collect(toList());
                        return new Flow(getNameOfFlow(n), references, asyncConsumeHolders, asyncPublishHolders);
                    })
                    .collect(toList());
            return new Configuration(muleConfigurationFile.getName(), flows);
        });
        return chList;
    }

    protected Stream<Node> getFlows(Document document) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("//*[local-name()='flow' or local-name()='sub-flow']").evaluate(document, NODESET))
        );
    }

    protected String getNameOfFlow(Node node) {
        return withXPath(xPath ->
                xPath.compile("@name").evaluate(node)
        );
    }

    protected Stream<ReferenceHolder> getReferencesInText(Node node) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::*//text()[contains(., 'Mule::lookup') or contains(., 'lookup')]").evaluate(node, NODESET))
                        .flatMap(n ->
                                asStream(lookupsInString.matcher(n.getNodeValue()))
                                        .map(LookupReference::new)
                        )
        );
    }

    protected Stream<ReferenceHolder> getReferencesInAttribute(Node node) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::*//@*[contains(., 'Mule::lookup') or contains(., 'lookup')]").evaluate(node, NODESET))
                        .flatMap(n ->
                                asStream(lookupsInString.matcher(n.getNodeValue()))
                                        .map(LookupReference::new)
                        )
        );
    }

    protected Stream<ReferenceHolder> getReferencesInDataweaveFile(Node node, Path resourceDirectory) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::*//@resource[substring(., string-length(.) - 3) = '.dwl']").evaluate(node, NODESET))
                        .flatMap(nodeWithResource -> {
                            String[] fileName = nodeWithResource
                                    .getNodeValue()
                                    .split(":");
                            String f = fileName[fileName.length - 1];
                            try {
                                byte[] bytes = Files.readAllBytes(resourceDirectory.resolve(f));
                                String content = new String(bytes, StandardCharsets.UTF_8);
                                return asStream(lookupsInString.matcher(content))
                                        .map(LookupReference::new);
                            } catch (IOException ex) {
                                return Stream.empty();
                            }
                        })
        );
    }

    protected Stream<ReferenceHolder> getReferencesInFlowRef(Node node) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::*[local-name()='flow-ref']/@name").evaluate(node, NODESET))
                        .filter(n -> !(n.getNodeName().equals("doc:name")))
                        .map(n -> FlowReference.create(n.getNodeValue()))
        );
    }

    protected Stream<AsyncPublishHolder> getAsyncPublisher(Node node, Document document) {

        return withXPathNS(document, xPath ->
                streamConcat(
                        asStream((NodeList) xPath.compile("(descendant::jms:publish/@destination) | (descendant::jms:outbound-endpoint/@queue)").evaluate(node, NODESET))
                                .map(n -> JmsPublish.create(n.getNodeValue(), connectionRegistry)),
                        asStream((NodeList) xPath.compile("descendant::vm:publish/@queueName").evaluate(node, NODESET))
                                .map(n -> VmPublish.create(n.getNodeValue(), connectionRegistry))
                )
        );
    }

    protected Stream<AsyncConsumeHolder> getAsyncConsumer(Node node, Document document) {
        return withXPathNS(document, xPath ->
                streamConcat(
                        asStream((NodeList) xPath.compile("(descendant::jms:consume/@destination) | (descendant::jms:listener/@destination) | (descendant::jms:inbound-endpoint/@queue)").evaluate(node, NODESET))
                                .map(n -> JmsConsume.create(n.getNodeValue(), connectionRegistry)),
                        asStream((NodeList) xPath.compile("(descendant::vm:consume/@queueName) | (descendant::vm:listener/@queueName)").evaluate(node, NODESET))
                                .map(n -> VmConsume.create(n.getNodeValue(), connectionRegistry))
                )
        );
    }

    protected Document getDocument(File file) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
            dbf.setXIncludeAware(false);
            dbf.setNamespaceAware(true);
            DocumentBuilder builder = dbf.newDocumentBuilder();
            return builder.parse(Files.newInputStream(file.toPath()));
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }


}
