package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.ReferenceCallType;
import io.github.obscure1910.flowlandscape.api.ReferenceHolder;

import java.util.Objects;

public class Reference implements ReferenceHolder {

    private final ReferenceCallType referenceCallType;
    private final String referenceToFlowName;

    private Reference(ReferenceCallType referenceCallType, String referenceToFlowName) {
        this.referenceCallType = referenceCallType;
        this.referenceToFlowName = referenceToFlowName;
    }

    public static Reference referenceCallViaFlowRef(String referenceToFlowName) {
        return new Reference(ReferenceCallType.FLOW, referenceToFlowName);
    }

    public static Reference referenceCallViaLookup(String referenceToFlowName) {
        return new Reference(ReferenceCallType.LOOKUP, referenceToFlowName);
    }

    @Override
    public ReferenceCallType getReferenceCallType() {
        return this.referenceCallType;
    }

    @Override
    public String getReferenceToFlowName() {
        return this.referenceToFlowName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (referenceCallType != reference.referenceCallType) return false;
        return Objects.equals(referenceToFlowName, reference.referenceToFlowName);
    }

    @Override
    public int hashCode() {
        int result = referenceCallType != null ? referenceCallType.hashCode() : 0;
        result = 31 * result + (referenceToFlowName != null ? referenceToFlowName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "referenceCallType=" + referenceCallType +
                ", referenceToFlowName='" + referenceToFlowName + '\'' +
                '}';
    }
}
