package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

import java.util.Objects;

public class JmsPublish implements AsyncPublishHolder {

    private final String destinationName;
    private final ConnectionRegistry connectionRegistry;

    public JmsPublish(String destinationName, ConnectionRegistry connectionRegistry) {
        this.destinationName = destinationName;
        this.connectionRegistry = connectionRegistry;
    }

    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean hasSameDestination(AsyncConsumeHolder other) {
        return this.connectionRegistry.hasSameDestination(other, this);
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
