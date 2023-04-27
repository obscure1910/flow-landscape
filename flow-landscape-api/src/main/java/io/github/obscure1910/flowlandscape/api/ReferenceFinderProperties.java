package io.github.obscure1910.flowlandscape.api;

import java.nio.file.Path;
import java.util.Objects;

public class ReferenceFinderProperties {

    private final Path sourceDirectory;
    private final Path resourceDirectory;
    private final boolean findAsyncReferences;

    public ReferenceFinderProperties(Path sourceDirectory, Path resourceDirectory, boolean findAsyncReferences) {
        this.sourceDirectory = sourceDirectory;
        this.resourceDirectory = resourceDirectory;
        this.findAsyncReferences = findAsyncReferences;
    }

    public Path getSourceDirectory() {
        return sourceDirectory;
    }

    public Path getResourceDirectory() {
        return resourceDirectory;
    }

    public boolean isFindAsyncReferences() {
        return findAsyncReferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceFinderProperties that = (ReferenceFinderProperties) o;

        if (findAsyncReferences != that.findAsyncReferences) return false;
        if (!Objects.equals(sourceDirectory, that.sourceDirectory))
            return false;
        return Objects.equals(resourceDirectory, that.resourceDirectory);
    }

    @Override
    public int hashCode() {
        int result = sourceDirectory != null ? sourceDirectory.hashCode() : 0;
        result = 31 * result + (resourceDirectory != null ? resourceDirectory.hashCode() : 0);
        result = 31 * result + (findAsyncReferences ? 1 : 0);
        return result;
    }
}
