package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinderProperties;
import io.github.obscure1910.flowlandscape.parser.model.Configuration;
import io.github.obscure1910.flowlandscape.parser.model.Flow;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.github.obscure1910.flowlandscape.parser.model.Reference.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

class XPathReferenceFinderTest {

    ReferenceFinder ctbt = new XPathReferenceFinder();

    @ParameterizedTest
    @ValueSource(strings = {"mule3", "mule4"})
    void findReferencesTest(String muleVersion) throws IOException {
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(
                new File("src/test/resources/"+muleVersion+"/configurations/").toPath(),
                new File("src/test/resources/"+muleVersion+"/mappings/").toPath()
        );

        List<ConfigurationHolder> configurations = ctbt.findReferences(referenceFinderProperties);

        List<ConfigurationHolder> expected = asList(
                new Configuration("example1.xml", asList(
                        new Flow("flow1", asList(
                                referenceCallViaFlowRef("flow2"),
                                referenceCallViaFlowRef("flow4"))),
                        new Flow("flow2", singletonList(
                                referenceCallViaFlowRef("flow3"))),
                        new Flow("flow3", emptyList()),
                        new Flow("flow4", singletonList(
                                referenceCallViaFlowRef("flow5")))
                )),
                new Configuration("example2.xml", asList(
                        new Flow("flow5", asList(
                                referenceCallViaLookup("flow6"),
                                referenceCallViaLookup("flow8"),
                                referenceCallViaLookup("flow7")
                        )),
                        new Flow("flow6", emptyList()),
                        new Flow("flow7", singletonList(
                                referenceCallViaLookup("flow8"))),
                        new Flow("flow8", emptyList())
                )));

        assertThat(configurations, hasItems(expected.toArray(new ConfigurationHolder[2])));
    }
}