package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Flow implements FlowHolder {

    private final List<ReferenceHolder> flowReferences;
    private final List<AsyncConsumeHolder> asyncConsumer;
    private final List<AsyncPublishHolder> asyncPublisher;
    private final String flowName;

    public Flow(String flowName,
                List<ReferenceHolder> flowReferences,
                List<AsyncConsumeHolder> asyncConsumer,
                List<AsyncPublishHolder> asyncPublisher) {
        this.flowReferences = flowReferences;
        this.asyncConsumer = asyncConsumer;
        this.asyncPublisher = asyncPublisher;
        this.flowName = flowName;
    }

    @Override
    public List<ReferenceHolder> getFlowReferences() {
        return new ArrayList<>(this.flowReferences);
    }

    @Override
    public List<AsyncConsumeHolder> getAsyncConsumer() {
        return new ArrayList<>(this.asyncConsumer);
    }

    @Override
    public List<AsyncPublishHolder> getAsyncPublisher() {
        return new ArrayList<>(this.asyncPublisher);
    }

    @Override
    public String getFlowName() {
        return this.flowName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flow)) return false;
        Flow flow = (Flow) o;
        return Objects.equals(flowReferences, flow.flowReferences) && Objects.equals(asyncConsumer, flow.asyncConsumer) && Objects.equals(asyncPublisher, flow.asyncPublisher) && Objects.equals(flowName, flow.flowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowReferences, asyncConsumer, asyncPublisher, flowName);
    }

    @Override
    public String toString() {
        return "Flow{" +
                "flowReferences=" + flowReferences +
                ", asyncConsumer=" + asyncConsumer +
                ", asyncPublisher=" + asyncPublisher +
                ", flowName='" + flowName + '\'' +
                '}';
    }
}
