package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;

public final class VmConsume extends AsyncConsumeHolder {

    public VmConsume(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static VmConsume create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new VmConsume(destinationName, connectionRegistry);
    }

}