package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;

public final class JmsConsume extends AsyncConsumeHolder {

    public JmsConsume(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static JmsConsume create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new JmsConsume(destinationName, connectionRegistry);
    }

}
