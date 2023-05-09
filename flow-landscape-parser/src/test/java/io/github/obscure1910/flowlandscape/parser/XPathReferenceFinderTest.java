package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinderProperties;
import io.github.obscure1910.flowlandscape.api.ref.AsyncReferenceHolder;
import io.github.obscure1910.flowlandscape.parser.model.*;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class XPathReferenceFinderTest {

    ReferenceFinder ctbt = new XPathReferenceFinder();

    @ParameterizedTest
    @ValueSource(strings = {"mule3", "mule4"})
    void findReferencesTest(String muleVersion) throws IOException {
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(
                new File("src/test/resources/" + muleVersion + "/ref/configurations/").toPath(),
                new File("src/test/resources/" + muleVersion + "/ref/mappings/").toPath()
        );

        List<ConfigurationHolder> configurations = ctbt.findReferences(referenceFinderProperties);

        List<ConfigurationHolder> expected = asList(
                new Configuration("example1.xml", asList(
                        new Flow("flow1",
                                asList(FlowReference.create("flow2"), FlowReference.create("flow4")),
                                emptyList(),
                                emptyList()),
                        new Flow("flow2",
                                singletonList(FlowReference.create("flow3")),
                                emptyList(),
                                emptyList()),
                        new Flow("flow3",
                                emptyList(),
                                emptyList(),
                                emptyList()),
                        new Flow("flow4",
                                singletonList(FlowReference.create("flow5")),
                                emptyList(),
                                emptyList())
                )),
                new Configuration("example2.xml", asList(
                        new Flow("flow5",
                                asList(LookupReference.create("flow6"), LookupReference.create("flow8"), LookupReference.create("flow7")),
                                emptyList(),
                                emptyList()),
                        new Flow("flow6",
                                emptyList(),
                                emptyList(),
                                emptyList()),
                        new Flow("flow7",
                                singletonList(LookupReference.create("flow8")),
                                emptyList(),
                                emptyList()),
                        new Flow("flow8",
                                emptyList(),
                                emptyList(),
                                emptyList())
                )));

        assertThat(configurations).hasSameElementsAs(expected);
    }

    @Test
    void findAsyncReferencesMule4Test() {
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(
                new File("src/test/resources/mule4/jms/configurations/").toPath(),
                new File("src/test/resources/mule4/jms/mappings/").toPath()
        );

        List<ConfigurationHolder> configurations = ctbt.findReferences(referenceFinderProperties);

        List<Tuple> configurationsAsTuples = configurations.stream().flatMap(c ->
                c.getFlows().stream().map(f ->
                        tuple(
                                f.getFlowName(),
                                f.getFlowReferences(),
                                f.getAsyncConsumer().stream().map(AsyncReferenceHolder::getDestinationName).collect(Collectors.toList()),
                                f.getAsyncPublisher().stream().map(AsyncReferenceHolder::getDestinationName).collect(Collectors.toList())
                        )
                )
        ).collect(Collectors.toList());

        List<Tuple> expected = asList(
                //FlowName, FlowReferences, AsyncConsumer, AsyncPublisher
                tuple("jms-flow1", emptyList(), emptyList(), singletonList("myqueue")),
                tuple("jms-flow2", emptyList(), singletonList("myqueue"), emptyList()),
                tuple("jms-flow3", emptyList(), singletonList("myqueue"), emptyList())
        );

        assertThat(configurationsAsTuples).hasSameElementsAs(expected);
    }

    @Test
    public void equalsHashCodeContractTest() {
        EqualsVerifier
                .forPackage("io.github.obscure1910.flowlandscape.parser")
                .except(XPathReferenceFinder.class, NamespaceResolver.class)
                .suppress(Warning.STRICT_INHERITANCE, Warning.INHERITED_DIRECTLY_FROM_OBJECT)
                .verify();
    }

}