package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.ref.LookupReferenceHolder;

import java.util.Objects;

public class LookupReference implements LookupReferenceHolder {

    private final String destinationName;

    public LookupReference(String referenceToFlowName) {
        this.destinationName = referenceToFlowName;
    }

    static public LookupReferenceHolder create(String destinationName) {
        return new LookupReference(destinationName);
    }
    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LookupReference that = (LookupReference) o;

        return Objects.equals(destinationName, that.destinationName);
    }

    @Override
    public int hashCode() {
        return destinationName != null ? destinationName.hashCode() : 0;
    }

}
