package io.github.obscure1910.flowlandscape.api;

import java.nio.file.Path;
import java.util.Objects;

public class ReferenceFinderProperties {

    private final Path sourceDirectory;
    private final Path resourceDirectory;

    public ReferenceFinderProperties(Path sourceDirectory, Path resourceDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.resourceDirectory = resourceDirectory;
    }

    public Path getSourceDirectory() {
        return sourceDirectory;
    }

    public Path getResourceDirectory() {
        return resourceDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReferenceFinderProperties that = (ReferenceFinderProperties) o;

        if (!Objects.equals(sourceDirectory, that.sourceDirectory))
            return false;
        return Objects.equals(resourceDirectory, that.resourceDirectory);
    }

    @Override
    public int hashCode() {
        int result = sourceDirectory != null ? sourceDirectory.hashCode() : 0;
        result = 31 * result + (resourceDirectory != null ? resourceDirectory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReferenceFinderProperties{" +
                "sourceDirectory=" + sourceDirectory +
                ", resourceDirectory=" + resourceDirectory +
                '}';
    }
}
