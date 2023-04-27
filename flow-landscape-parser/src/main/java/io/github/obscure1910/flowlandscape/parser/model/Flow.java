package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flow implements FlowHolder {

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
        if (o == null || getClass() != o.getClass()) return false;

        Flow flow = (Flow) o;

        if (!Objects.equals(flowReferences, flow.flowReferences))
            return false;
        if (!Objects.equals(asyncConsumer, flow.asyncConsumer))
            return false;
        return Objects.equals(flowName, flow.flowName);
    }

    @Override
    public int hashCode() {
        int result = flowReferences != null ? flowReferences.hashCode() : 0;
        result = 31 * result + (asyncConsumer != null ? asyncConsumer.hashCode() : 0);
        result = 31 * result + (flowName != null ? flowName.hashCode() : 0);
        return result;
    }
}
