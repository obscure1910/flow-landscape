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
        if (!(o instanceof ReferenceFinderProperties)) return false;
        ReferenceFinderProperties that = (ReferenceFinderProperties) o;
        return Objects.equals(sourceDirectory, that.sourceDirectory) && Objects.equals(resourceDirectory, that.resourceDirectory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceDirectory, resourceDirectory);
    }
}
