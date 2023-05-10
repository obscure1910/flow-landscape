package io.github.obscure1910.flowlandscape.api.connection;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

import java.util.Objects;

public class ConnectionDefinition {

    private final Class<? extends AsyncConsumeHolder> source;
    private final Class<? extends AsyncPublishHolder> target;

    private ConnectionDefinition(Class<? extends AsyncConsumeHolder> source, Class<? extends AsyncPublishHolder> target) {
        this.source = source;
        this.target = target;
    }

    public static ConnectionDefinition create(Class<? extends AsyncConsumeHolder> source, Class<? extends AsyncPublishHolder> target) {
        return new ConnectionDefinition(source, target);
    }

    public Class<? extends AsyncConsumeHolder> getSource() {
        return source;
    }

    public Class<? extends AsyncPublishHolder> getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionDefinition)) return false;
        ConnectionDefinition that = (ConnectionDefinition) o;
        return Objects.equals(source, that.source) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }

    @Override
    public String toString() {
        return "ConnectionDefinition{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
