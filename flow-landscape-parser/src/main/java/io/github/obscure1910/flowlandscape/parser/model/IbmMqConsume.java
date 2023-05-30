package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;

public class IbmMqConsume extends AsyncConsumeHolder {

    public IbmMqConsume(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static IbmMqConsume create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new IbmMqConsume(destinationName, connectionRegistry);
    }

}
