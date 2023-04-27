package io.github.obscure1910.flowlandscape.api.ref;

public interface AsyncPublishHolder extends AsyncReferenceHolder {

    boolean hasSameDestination(AsyncConsumeHolder other);

}
