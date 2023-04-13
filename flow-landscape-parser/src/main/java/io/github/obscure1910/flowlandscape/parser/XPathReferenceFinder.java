package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.*;
import io.github.obscure1910.flowlandscape.parser.model.Configuration;
import io.github.obscure1910.flowlandscape.parser.model.Flow;
import io.github.obscure1910.flowlandscape.parser.model.Reference;
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

import static java.util.stream.Collectors.toList;
import static javax.xml.xpath.XPathConstants.NODESET;

public class XPathReferenceFinder implements ReferenceFinder {

    private final Pattern lookupsInString = Pattern.compile("(?<=((Mule::|\\s|\\[|\\()lookup)(\\(\")).*(?=\")");

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
                        return new Flow(getNameOfFlow(n), references);
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
                                        .map(Reference::referenceCallViaLookup)
                        )
        );
    }

    protected Stream<ReferenceHolder> getReferencesInAttribute(Node node) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::*//@*[contains(., 'Mule::lookup') or contains(., 'lookup')]").evaluate(node, NODESET))
                        .flatMap(n ->
                                asStream(lookupsInString.matcher(n.getNodeValue()))
                                        .map(Reference::referenceCallViaLookup)
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
                                        .map(Reference::referenceCallViaLookup);
                            } catch (IOException ex) {
                                return Stream.empty();
                            }
                        })
        );
    }

    protected Stream<ReferenceHolder> getReferencesInFlowRef(Node node) {
        return withXPath(xPath ->
                asStream((NodeList) xPath.compile("descendant::flow-ref//@name").evaluate(node, NODESET))
                        .filter(n -> !(n.getNodeName().equals("doc:name")))
                        .map(n -> Reference.referenceCallViaFlowRef(n.getNodeValue()))
        );
    }

    protected Document getDocument(File file) {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(Files.newInputStream(file.toPath()));
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            throw new RuntimeException(ex);
        }
    }

}
