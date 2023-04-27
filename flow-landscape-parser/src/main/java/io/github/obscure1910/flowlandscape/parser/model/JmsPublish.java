package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.ref.AsyncReferenceHolder;

import java.util.Objects;

public class JmsPublish implements AsyncReferenceHolder {

    private final String destinationName;

    public JmsPublish(String referenceToFlowName) {
        this.destinationName = referenceToFlowName;
    }

    static public AsyncReferenceHolder create(String destinationName) {
        return new JmsPublish(destinationName);
    }
    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JmsPublish that = (JmsPublish) o;

        return Objects.equals(destinationName, that.destinationName);
    }

    @Override
    public int hashCode() {
        return destinationName != null ? destinationName.hashCode() : 0;
    }
}
