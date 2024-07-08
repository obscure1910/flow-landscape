package io.github.obscure1910.flowlandscape.generator;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Node;
import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import io.github.obscure1910.flowlandscape.api.ref.FlowRefReferenceHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;

import java.util.Optional;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class GraphvizFlow {

    public final FlowHolder flowHolder;
    public final Node node;
    public final String clusterName;
    public final String fontName;

    public GraphvizFlow(FlowHolder flowHolder, String clusterName, String fontName) {
        this.flowHolder = flowHolder;
        this.node = node(flowHolder.getFlowName()).with(Shape.RECTANGLE, Color.WHEAT1.striped(), Font.name(fontName));
        this.clusterName = clusterName;
        this.fontName = fontName;
    }

    private GraphvizFlow(FlowHolder flowHolder, Node node, String clusterName, String fontName) {
        this.flowHolder = flowHolder;
        this.node = node;
        this.clusterName = clusterName;
        this.fontName = fontName;
    }

    public GraphvizFlow addLinkTo(GraphvizFlow other) {
        Optional<AsyncPublishHolder> optArh = this.flowHolder
                .getAsyncPublisher()
                .stream()
                .filter(p ->
                        other.flowHolder
                                .getAsyncConsumer()
                                .stream()
                                .anyMatch(s -> s.hasSameDestination(p))
                )
                .findFirst();
        Optional<ReferenceHolder> optRh = this.flowHolder.getFlowReferences().stream()
                .filter(fh -> fh.getDestinationName().equals(other.flowHolder.getFlowName()))
                .findFirst();

        return optRh
                .map(rh -> {
                    if (rh instanceof FlowRefReferenceHolder) {
                        return new GraphvizFlow(this.flowHolder, node.link(to(other.node.asLinkTarget())), this.clusterName, this.fontName);
                    } else {
                        return new GraphvizFlow(this.flowHolder, node.link(to(other.node.asLinkTarget()).with(Color.RED, Style.DASHED)), this.clusterName, this.fontName);
                    }
                })
                .orElseGet(() ->
                        optArh
                                .map(arh -> new GraphvizFlow(this.flowHolder, node.link(to(other.node.asLinkTarget()).with(Color.CYAN)), this.clusterName, this.fontName))
                                .orElse(this)
                );
    }

    public boolean isReferencedBy(GraphvizFlow other) {
        boolean hasAsyncReference = this.flowHolder
                .getAsyncConsumer()
                .stream()
                .anyMatch(s ->
                        other.flowHolder
                                .getAsyncPublisher()
                                .stream()
                                .anyMatch(p -> p.hasSameDestination(s))
                );
        boolean hasFlowReference = other.flowHolder.getFlowReferences().stream()
                .anyMatch(referenceHolder -> referenceHolder.getDestinationName().equals(flowHolder.getFlowName()));
        return hasFlowReference || hasAsyncReference;
    }


}
