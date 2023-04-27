package io.github.obscure1910.flowlandscape.api.flow;

import io.github.obscure1910.flowlandscape.api.ReferenceHolder;

import java.util.List;

public interface FlowHolder {

    List<ReferenceHolder> getFlowReferences();
    String getName();

}
