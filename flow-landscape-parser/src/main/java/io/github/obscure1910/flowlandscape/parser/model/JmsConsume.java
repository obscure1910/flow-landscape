package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

import java.util.Objects;

public class JmsConsume implements AsyncConsumeHolder {

    private final String destinationName;
    private final ConnectionRegistry connectionRegistry;

    public JmsConsume(String destinationName, ConnectionRegistry connectionRegistry) {
        this.destinationName = destinationName;
        this.connectionRegistry = connectionRegistry;
    }

    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean hasSameDestination(AsyncPublishHolder other) {
        return connectionRegistry.hasSameDestination(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JmsConsume that = (JmsConsume) o;

        return Objects.equals(destinationName, that.destinationName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (destinationName != null ? destinationName.hashCode() : 0);
        return result;
    }

}
