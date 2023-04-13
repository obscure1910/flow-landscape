package io.github.obscure1910.flowlandscape.api.ref;


import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;

import java.util.Objects;

public abstract class AsyncPublishHolder extends AsyncReferenceHolder {

    private final ConnectionRegistry connectionRegistry;

    public AsyncPublishHolder(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName);
        this.connectionRegistry = connectionRegistry;
    }

    public boolean hasSameDestination(AsyncConsumeHolder other) {
        return connectionRegistry.hasSameDestination(other, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsyncPublishHolder)) return false;
        if (!super.equals(o)) return false;
        AsyncPublishHolder that = (AsyncPublishHolder) o;
        return Objects.equals(connectionRegistry, that.connectionRegistry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), connectionRegistry);
    }
}
