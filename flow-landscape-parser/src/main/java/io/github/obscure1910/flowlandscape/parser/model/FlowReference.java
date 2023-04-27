package io.github.obscure1910.flowlandscape.parser.model;


import io.github.obscure1910.flowlandscape.api.ref.FlowRefReferenceHolder;
import io.github.obscure1910.flowlandscape.api.ref.LookupReferenceHolder;

import java.util.Objects;

public class FlowReference implements FlowRefReferenceHolder {

    private final ReferenceCallType referenceCallType;
    private final String referenceToFlowName;

    private FlowReference(ReferenceCallType referenceCallType, String referenceToFlowName) {
        this.referenceCallType = referenceCallType;
        this.referenceToFlowName = referenceToFlowName;
    }

    public static FlowReference referenceCallViaFlowRef(String referenceToFlowName) {
        return new FlowReference(ReferenceCallType.FLOW, referenceToFlowName);
    }

    public static FlowReference referenceCallViaLookup(String referenceToFlowName) {
        return new FlowReference(ReferenceCallType.LOOKUP, referenceToFlowName);
    }

    public static FlowReference asyncReferenceCall(String referenceToQueue) {
        return new FlowReference(ReferenceCallType.ASYNC, referenceToQueue);
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

        FlowReference reference = (FlowReference) o;

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
