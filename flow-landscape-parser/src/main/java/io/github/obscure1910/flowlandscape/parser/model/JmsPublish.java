package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

public class JmsPublish extends AsyncPublishHolder {

    public JmsPublish(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

}
