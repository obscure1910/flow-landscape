package io.github.obscure1910.flowlandscape.api.connection;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;

import java.util.ArrayList;
import java.util.List;

public class ConnectionRegistry {

    private final List<ConnectionDefinition<? extends AsyncConsumeHolder, ? extends AsyncConsumeHolder >> knownConnections;

    public ConnectionRegistry(List<ConnectionDefinition<? extends AsyncConsumeHolder, ? extends AsyncConsumeHolder>> knownConnections) {
        this.knownConnections = new ArrayList<>(knownConnections);
    }



}
