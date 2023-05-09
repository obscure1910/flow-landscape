package io.github.obscure1910.flowlandscape.api.connection;

import io.github.obscure1910.flowlandscape.api.ref.AsyncConsumeHolder;
import io.github.obscure1910.flowlandscape.api.ref.AsyncPublishHolder;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectionRegistryTest {

    @Test
    void isCompatibleTest() {
        AsyncPublishHolder p = createAsyncPublishHolder("myqueue");
        AsyncConsumeHolder c = createAsyncConsumeHolder("myqueue");
        ConnectionRegistry cr = new ConnectionRegistry(
                singletonList(
                        ConnectionDefinition.create(c.getClass(), p.getClass())
                )) {
        };
        assertTrue(cr.isCompatible(c.getClass(), p.getClass()));
    }

    @Test
    void isNotCompatibleTest() {
        AsyncPublishHolder p = createAsyncPublishHolder("myqueue");
        AsyncConsumeHolder c1 = createAsyncConsumeHolder("myqueue");
        AsyncConsumeHolder c2 = createOtherAsyncConsumeHolder("myqueue");
        ConnectionRegistry cr = new ConnectionRegistry(
                singletonList(
                        ConnectionDefinition.create(c1.getClass(), p.getClass())
                )) {
        };
        assertFalse(cr.isCompatible(c2.getClass(), p.getClass()));
    }

    @Test
    void hasSameDestinationTest() {
        AsyncPublishHolder p = createAsyncPublishHolder("myqueue");
        AsyncConsumeHolder c = createAsyncConsumeHolder("myqueue");
        ConnectionRegistry cr = new ConnectionRegistry(
                singletonList(
                        ConnectionDefinition.create(c.getClass(), p.getClass())
                )) {
        };
        assertTrue(cr.hasSameDestination(c, p));
    }

    @Test
    void hasNotSameDestinationTest() {
        AsyncPublishHolder p = createAsyncPublishHolder("myqueue");
        AsyncConsumeHolder c = createAsyncConsumeHolder("other_myqueue");
        ConnectionRegistry cr = new ConnectionRegistry(
                singletonList(
                        ConnectionDefinition.create(c.getClass(), p.getClass())
                )) {
        };
        assertFalse(cr.hasSameDestination(c, p));
    }

    AsyncPublishHolder createAsyncPublishHolder(String destinationName) {
        return new AsyncPublishHolder(null, null) {
            @Override
            public String getDestinationName() {
                return destinationName;
            }
        };
    }

    AsyncConsumeHolder createAsyncConsumeHolder(String destinationName) {
        return new AsyncConsumeHolder(null, null) {

            @Override
            public String getDestinationName() {
                return destinationName;
            }
        };
    }

    AsyncConsumeHolder createOtherAsyncConsumeHolder(String destinationName) {
        return new AsyncConsumeHolder(null, null) {

            @Override
            public String getDestinationName() {
                return destinationName;
            }
        };
    }


}