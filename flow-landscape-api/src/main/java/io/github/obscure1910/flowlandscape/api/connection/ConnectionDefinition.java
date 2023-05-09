package io.github.obscure1910.flowlandscape.api.connection;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;

import java.util.Objects;

public class ConnectionDefinition<A extends AsyncConsumeHolder, B extends AsyncPublishHolder> {

    private final Class<A> source;
    private final Class<B> target;

    private ConnectionDefinition(Class<A> source, Class<B> target) {
        this.source = source;
        this.target = target;
    }

    public static <A extends AsyncConsumeHolder, B extends AsyncPublishHolder> ConnectionDefinition<A, B> create(Class<A> source, Class<B> target) {
        return new ConnectionDefinition<>(source, target);
    }

    public Class<A> getSource() {
        return source;
    }

    public Class<B> getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionDefinition)) return false;
        ConnectionDefinition<?, ?> that = (ConnectionDefinition<?, ?>) o;
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
