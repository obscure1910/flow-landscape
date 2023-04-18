package io.github.obscure1910.flowlandscape.generator;

import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.FlowLandscapeGenerator;
import io.github.obscure1910.flowlandscape.api.GeneratorProperties;


import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static io.github.obscure1910.flowlandscape.generator.StreamFunctions.foldLeft;
import static guru.nidi.graphviz.attribute.GraphAttr.SplineMode.ORTHO;
import static guru.nidi.graphviz.attribute.GraphAttr.splines;
import static guru.nidi.graphviz.attribute.Label.Justification.LEFT;
import static guru.nidi.graphviz.attribute.Label.Location.TOP;
import static guru.nidi.graphviz.attribute.Rank.sepEqually;
import static guru.nidi.graphviz.model.Factory.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.*;

public class GraphvizFlowLandscape implements FlowLandscapeGenerator {

    @Override
    public void generateConfigurations(List<ConfigurationHolder> configurations, GeneratorProperties generatorProperties) {
        Set<GraphvizFlow> allNodes = configurations.stream().flatMap(configuration -> configuration.getFlows().stream().map(flow -> {
            String clusterName = configuration.getName();
            return new GraphvizFlow(flow, "[ " + clusterName + " ]"); //with a brackets because otherwise the global.xml will be ignored
        })).collect(toSet());

        Stream<GraphvizFlow> allNodesWithLinks = foldLeft(allNodes.stream(), empty(), (acc, graphvizFlow) ->
                concat(acc,
                        of(allNodes.stream()
                                .filter(o -> o.isReferencedBy(graphvizFlow))
                                .reduce(graphvizFlow, GraphvizFlow::addLinkTo))
                ).distinct()
        );

        LinkSource[] clusters = allNodesWithLinks
                .collect(groupingBy(gf -> gf.clusterName))
                .entrySet().stream().map(entry -> {
                    List<LinkSource> ls = entry.getValue().stream().map(gf -> gf.node).collect(toList());
                    return graph(entry.getKey())
                            .cluster()
                            .directed()
                            .graphAttr().with(
                                    Label.of(entry.getKey()).locate(TOP).justify(LEFT),
                                    Font.size(10),
                                    Style.wedged(Color.BLUE4))
                            .with(ls);
                }).toArray(LinkSource[]::new);

        Graph g = graph(generatorProperties.getHeadLine())
                .directed()
                .graphAttr().with(
                        Label.of(generatorProperties.getHeadLine()).locate(TOP),
                        splines(ORTHO),
                        sepEqually(generatorProperties.getSpaceBetweenElements())
                )
                .with(clusters);

        try {
            Graphviz.fromGraph(g).width(12000).render(Format.PNG).toFile(generatorProperties.getOutputDirectory().resolve("flowlandscape.png").toFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
