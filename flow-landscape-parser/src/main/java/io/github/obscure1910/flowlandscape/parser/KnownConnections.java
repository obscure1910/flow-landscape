package io.github.obscure1910.flowlandscape.parser;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionDefinition;
import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;
import io.github.obscure1910.flowlandscape.parser.model.*;

import java.util.Arrays;

public class KnownConnections extends ConnectionRegistry {

    public KnownConnections() {
        super(Arrays.asList(
                ConnectionDefinition.create(JmsConsume.class, JmsPublish.class),
                ConnectionDefinition.create(VmConsume.class, VmPublish.class),
                ConnectionDefinition.create(IbmMqConsume.class, IbmMqPublish.class)
        ));
    }



}
