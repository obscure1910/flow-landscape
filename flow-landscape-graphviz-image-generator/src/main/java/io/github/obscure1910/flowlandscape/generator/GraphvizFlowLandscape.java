package io.github.obscure1910.flowlandscape.generator;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.*;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.LinkSource;
import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.FlowLandscapeGenerator;
import io.github.obscure1910.flowlandscape.api.GeneratorProperties;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static guru.nidi.graphviz.attribute.GraphAttr.SplineMode.ORTHO;
import static guru.nidi.graphviz.attribute.GraphAttr.splines;
import static guru.nidi.graphviz.attribute.Label.Justification.LEFT;
import static guru.nidi.graphviz.attribute.Label.Location.TOP;
import static guru.nidi.graphviz.attribute.Rank.sepEqually;
import static guru.nidi.graphviz.model.Factory.graph;
import static io.github.obscure1910.flowlandscape.generator.StreamFunctions.foldLeft;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.*;

public class GraphvizFlowLandscape implements FlowLandscapeGenerator {

    @Override
    public void generateConfigurations(List<ConfigurationHolder> configurations, GeneratorProperties generatorProperties) {
        Set<GraphvizFlow> allNodes = configurations.stream().flatMap(configuration -> configuration.getFlows().stream().map(flow -> {
            String clusterName = configuration.getName().trim();
            return new GraphvizFlow(flow, "[ " + clusterName + " ]", generatorProperties.getFontName()); //with brackets because otherwise the global.xml will be ignored
        })).collect(toSet());

        Stream<GraphvizFlow> allNodesWithClusterInternalLinks = foldLeft(allNodes.stream(), empty(), (acc, graphvizFlow) ->
                concat(acc,
                        of(allNodes.stream()
                                .filter(o -> o.isReferencedBy(graphvizFlow) && o.clusterName.equals(graphvizFlow.clusterName))
                                .reduce(graphvizFlow, GraphvizFlow::addLinkTo))
                ).distinct()
        );

        Stream<GraphvizFlow> allNodesWithClusterExternalLinks = foldLeft(allNodes.stream(), empty(), (acc, graphvizFlow) ->
                concat(acc,
                        of(allNodes.stream()
                                .filter(o -> o.isReferencedBy(graphvizFlow) && !o.clusterName.equals(graphvizFlow.clusterName))
                                .reduce(graphvizFlow, GraphvizFlow::addLinkTo))
                ).distinct()
        );

        LinkSource[] clusters = concat(allNodesWithClusterInternalLinks
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
                }), allNodesWithClusterExternalLinks.map(x -> x.node)).toArray(LinkSource[]::new);

        Graph g = graph(generatorProperties.getHeadLine())
                .directed()
                .graphAttr().with(
                        Label.of(generatorProperties.getHeadLine()).locate(TOP),
                        Font.name(generatorProperties.getFontName()),
                        splines(ORTHO),
                        sepEqually(generatorProperties.getSpaceBetweenElements())
                )
                .with(clusters);

        try {
            Format format = Format.valueOf(generatorProperties.getFormat().getType());
            String extension = generatorProperties.getFormat().getExtension();
            int totalMemory = generatorProperties.getTotalMemory();
            int width = generatorProperties.getWidth();
            Graphviz.useEngine(Arrays.asList(new GraphvizV8Engine(), new GraphvizCmdLineEngine()));
            Graphviz.fromGraph(g).totalMemory(totalMemory).width(width).render(format).toFile(generatorProperties.getOutputDirectory().resolve("flowlandscape." + extension).toFile());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
