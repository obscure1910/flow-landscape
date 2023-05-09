package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;

public class JmsConsume extends AsyncConsumeHolder {

    public JmsConsume(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

}
