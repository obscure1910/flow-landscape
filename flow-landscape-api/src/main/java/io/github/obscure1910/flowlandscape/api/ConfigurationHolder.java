package io.github.obscure1910.flowlandscape.api;

import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;

import java.util.List;

public interface ConfigurationHolder {

    String getName();
    List<? extends FlowHolder> getFlows();

}
