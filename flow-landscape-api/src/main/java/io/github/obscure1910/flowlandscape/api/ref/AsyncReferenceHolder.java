package io.github.obscure1910.flowlandscape.api.ref;

import io.github.obscure1910.flowlandscape.api.connection.ConnectionRegistry;

import java.util.Objects;

public abstract class AsyncReferenceHolder implements ReferenceHolder {

    private final String destinationName;

    public AsyncReferenceHolder(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AsyncReferenceHolder that = (AsyncReferenceHolder) o;

        return Objects.equals(destinationName, that.destinationName);
    }

    @Override
    public int hashCode() {
        return destinationName != null ? destinationName.hashCode() : 0;
    }
}
