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
        if (o == null || getClass() != o.getClass()) return false;

        Namespace namespace = (Namespace) o;

        if (!Objects.equals(prefix, namespace.prefix)) return false;
        return Objects.equals(uri, namespace.uri);
    }

    @Override
    public int hashCode() {
        int result = prefix != null ? prefix.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
