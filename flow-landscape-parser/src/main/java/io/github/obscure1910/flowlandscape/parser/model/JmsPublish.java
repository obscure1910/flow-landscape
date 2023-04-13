package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

public final class JmsPublish extends AsyncPublishHolder {

    public JmsPublish(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static JmsPublish create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new JmsPublish(destinationName, connectionRegistry);
    }

}
