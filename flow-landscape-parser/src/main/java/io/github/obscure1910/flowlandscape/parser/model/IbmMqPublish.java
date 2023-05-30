package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

public class IbmMqPublish extends AsyncPublishHolder {

    public IbmMqPublish(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static IbmMqPublish create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new IbmMqPublish(destinationName, connectionRegistry);
    }

}