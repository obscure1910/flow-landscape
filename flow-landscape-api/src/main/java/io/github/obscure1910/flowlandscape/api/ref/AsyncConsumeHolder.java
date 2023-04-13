package io.github.obscure1910.flowlandscape.api.ref;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;

import java.util.Objects;

public abstract class AsyncConsumeHolder extends AsyncReferenceHolder {

    private final ConnectionRegistry connectionRegistry;

    public AsyncConsumeHolder(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName);
        this.connectionRegistry = connectionRegistry;
    }

    public boolean hasSameDestination(AsyncPublishHolder other) {
        return connectionRegistry.hasSameDestination(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsyncConsumeHolder)) return false;
        if (!super.equals(o)) return false;
        AsyncConsumeHolder that = (AsyncConsumeHolder) o;
        return Objects.equals(connectionRegistry, that.connectionRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), connectionRegistry);
    }
}
