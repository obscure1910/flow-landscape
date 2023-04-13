package io.github.obscure1910.flowlandscape.api.flow;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import io.github.obscure1910.flowlandscape.api.ref.ReferenceHolder;

import java.util.List;

public interface FlowHolder {

    List<ReferenceHolder> getFlowReferences();
    List<AsyncConsumeHolder> getAsyncConsumer();
    List<AsyncPublishHolder> getAsyncPublisher();
    String getFlowName();

}
