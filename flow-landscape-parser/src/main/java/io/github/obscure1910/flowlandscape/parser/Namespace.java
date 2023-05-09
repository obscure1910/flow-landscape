package io.github.obscure1910.flowlandscape.parser;

import java.util.Objects;
import java.util.Optional;

public class Namespace {

    private final String prefix;
    private final String uri;

    public Namespace(String prefix, String uri) {
        this.prefix = prefix;
        this.uri = uri;
    }

    public Optional<String> reverse(String str) {
        if(prefix.equals(str)) {
            return Optional.of(uri);
        } else if(uri.equals(str)) {
            return Optional.of(prefix);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Namespace)) return false;
        Namespace namespace = (Namespace) o;
        return Objects.equals(prefix, namespace.prefix) && Objects.equals(uri, namespace.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, uri);
    }
}
