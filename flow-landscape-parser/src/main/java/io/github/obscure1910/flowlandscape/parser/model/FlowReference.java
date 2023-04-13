package io.github.obscure1910.flowlandscape.parser.model;


import io.github.obscure1910.flowlandscape.api.ref.FlowRefReferenceHolder;

import java.util.Objects;

public final class FlowReference implements FlowRefReferenceHolder {
    private final String destinationName;

    private FlowReference(String referenceToFlowName) {
        this.destinationName = referenceToFlowName;
    }

    static public FlowRefReferenceHolder create(String destinationName) {
        return new FlowReference(destinationName);
    }
    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlowReference)) return false;
        FlowReference that = (FlowReference) o;
        return Objects.equals(destinationName, that.destinationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationName);
    }

    @Override
    public String toString() {
        return "FlowReference{" +
                "destinationName='" + destinationName + '\'' +
                '}';
    }
}
