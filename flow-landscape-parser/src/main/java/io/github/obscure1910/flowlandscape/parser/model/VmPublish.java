package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

public class VmPublish extends AsyncPublishHolder {

    public VmPublish(String destinationName, ConnectionRegistry connectionRegistry) {
        super(destinationName, connectionRegistry);
    }

    public static VmPublish create(String destinationName, ConnectionRegistry connectionRegistry) {
        return new VmPublish(destinationName, connectionRegistry);
    }

}