package io.github.obscure1910.flowlandscape.generator;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Node;
import io.github.obscure1910.flowlandscape.api.ReferenceHolder;
import io.github.obscure1910.flowlandscape.api.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceCallType;

import java.util.Objects;

import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class GraphvizFlow {

    public final FlowHolder flowHolder;
    public final Node node;
    public final String clusterName;

    public GraphvizFlow(FlowHolder flowHolder, String clusterName) {
        this.flowHolder = flowHolder;
        this.node = node(flowHolder.getName()).with(Shape.RECTANGLE, Color.WHEAT1.striped());
        this.clusterName = clusterName;
    }

    private GraphvizFlow(FlowHolder flowHolder, Node node, String clusterName) {
        this.flowHolder = flowHolder;
        this.node = node;
        this.clusterName = clusterName;
    }

    public GraphvizFlow addLinkTo(GraphvizFlow other) {
        ReferenceCallType rfct = this.flowHolder.getFlowReferences().stream()
                .filter(fh -> fh.getReferenceToFlowName().equals(other.flowHolder.getName()))
                .map(ReferenceHolder::getReferenceCallType)
                .findFirst()
                .get();
        if(rfct == ReferenceCallType.FLOW) {
            return new GraphvizFlow(this.flowHolder, node.link(to(other.node)), this.clusterName);
        } else {
            return new GraphvizFlow(this.flowHolder, node.link(to(other.node).with(Color.RED, Style.DASHED)), this.clusterName);
        }
    }

    public boolean isReferencedBy(GraphvizFlow other) {
        return other.flowHolder.getFlowReferences().stream()
                .anyMatch(referenceHolder -> referenceHolder.getReferenceToFlowName().equals(flowHolder.getName()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphvizFlow that = (GraphvizFlow) o;

        return Objects.equals(flowHolder, that.flowHolder);
    }

    @Override
    public int hashCode() {
        return flowHolder != null ? flowHolder.hashCode() : 0;
    }
}
