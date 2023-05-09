package io.github.obscure1910.flowlandscape.api.ref;


import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;

public abstract class AsyncPublishHolder extends AsyncReferenceHolder {

    private final ConnectionRegistry connectionRegistry;

    public AsyncPublishHolder(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName);
        this.connectionRegistry = connectionRegistry;
    }

    public boolean hasSameDestination(AsyncConsumeHolder other) {
        return connectionRegistry.hasSameDestination(other, this);
    }

}
