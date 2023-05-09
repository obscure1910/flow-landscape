package io.github.obscure1910.flowlandscape.api.connection;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

abstract public class ConnectionRegistry {

    private final List<ConnectionDefinition<? extends AsyncConsumeHolder, ? extends AsyncPublishHolder>> knownConnections;

    public <X extends AsyncConsumeHolder, Y extends AsyncPublishHolder> ConnectionRegistry(List<ConnectionDefinition<X , Y>> knownConnections) {
        this.knownConnections = Collections.synchronizedList(new ArrayList<>(knownConnections));
    }

    public boolean isCompatible(Class<? extends AsyncConsumeHolder> source, Class<? extends AsyncPublishHolder> target) {
        return knownConnections.stream()
                .anyMatch(cd -> cd.getSource().equals(source) && cd.getTarget().equals(target));
    }

    public <X extends AsyncConsumeHolder, Y extends AsyncPublishHolder> boolean hasSameDestination(X source, Y target) {
        return isCompatible(source.getClass(), target.getClass())
                && source.getDestinationName().equals(target.getDestinationName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionRegistry)) return false;
        ConnectionRegistry that = (ConnectionRegistry) o;
        return Objects.equals(knownConnections, that.knownConnections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knownConnections);
    }
}
