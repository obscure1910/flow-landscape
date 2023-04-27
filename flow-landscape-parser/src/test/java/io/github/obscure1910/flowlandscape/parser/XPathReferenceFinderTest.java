package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinder;
import io.github.obscure1910.flowlandscape.api.ReferenceFinderProperties;
import io.github.obscure1910.flowlandscape.parser.model.Configuration;
import io.github.obscure1910.flowlandscape.parser.model.Flow;
import io.github.obscure1910.flowlandscape.parser.model.FlowReference;
import io.github.obscure1910.flowlandscape.parser.model.LookupReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
                new File("src/test/resources/" + muleVersion + "/ref/configurations/").toPath(),
                new File("src/test/resources/" + muleVersion + "/ref/mappings/").toPath(),
                false
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

        assertThat(configurations, hasItems(expected.toArray(new ConfigurationHolder[0])));
    }

    @Test
    void findAsyncReferencesMule4Test() {
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(
                new File("src/test/resources/mule4/jms/configurations/").toPath(),
                new File("src/test/resources/mule4/jms/mappings/").toPath(),
                true
        );

        List<ConfigurationHolder> configurations = ctbt.findReferences(referenceFinderProperties);
        System.out.println("sd");


    }

}