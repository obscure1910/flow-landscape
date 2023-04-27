package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.flow.AsyncFlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;

import java.util.List;
import java.util.Objects;

public class JmsConsume extends Flow implements AsyncFlowHolder {

    private final String destinationName;

    public JmsConsume(String destinationName, String flowName, List<ReferenceHolder> flowReferences) {
        super(flowName, flowReferences);
        this.destinationName = destinationName;
    }

    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JmsConsume asyncFlow = (JmsConsume) o;

        return Objects.equals(destinationName, asyncFlow.destinationName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (destinationName != null ? destinationName.hashCode() : 0);
        return result;
    }
}
