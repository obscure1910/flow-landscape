package io.github.obscure1910.flowlandscape.api.ref;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;

public abstract class AsyncConsumeHolder extends AsyncReferenceHolder {

    private final ConnectionRegistry connectionRegistry;

    public AsyncConsumeHolder(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName);
        this.connectionRegistry = connectionRegistry;
    }

    public boolean hasSameDestination(AsyncPublishHolder other) {
        return connectionRegistry.hasSameDestination(this, other);
    }

}
