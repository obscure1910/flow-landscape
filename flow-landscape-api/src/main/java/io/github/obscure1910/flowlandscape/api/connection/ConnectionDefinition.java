package io.github.obscure1910.flowlandscape.parser.model.connection;

import io.github.obscure1910.flowlandscape.api.flow.AsyncFlowHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncReferenceHolder;

import java.util.Objects;

public class ConnectionDefinition<A extends AsyncReferenceHolder, B extends AsyncFlowHolder> {

    private final Class<A> source;
    private final Class<B> target;

    private ConnectionDefinition(Class<A> source, Class<B> target) {
        this.source = source;
        this.target = target;
    }

    public static <A extends AsyncReferenceHolder, B extends AsyncFlowHolder> ConnectionDefinition<A, B> create(Class<A> source, Class<B> target) {
        return new ConnectionDefinition<>(source, target);
    }

    public Class<A> getSource() {
        return source;
    }

    public Class<B> getTarget() {
        return target;
    }

    public boolean isCompatibleSource(Class<?> source) {
        return this.source.isAssignableFrom(source);
    }

    public boolean isCompatibleTarget(Class<?> target) {
        return this.target.isAssignableFrom(target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConnectionDefinition<?, ?> that = (ConnectionDefinition<?, ?>) o;

        if (!Objects.equals(source, that.source)) return false;
        return Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (target != null ? target.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConnectionDefinition{" +
                "source=" + source +
                ", target=" + target +
                '}';
    }
}
