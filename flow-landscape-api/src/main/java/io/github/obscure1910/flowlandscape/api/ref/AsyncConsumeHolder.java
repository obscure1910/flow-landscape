package io.github.obscure1910.flowlandscape.api.ref;

public interface AsyncConsumeHolder extends AsyncReferenceHolder {

    boolean hasSameDestination(AsyncPublishHolder other);

}
