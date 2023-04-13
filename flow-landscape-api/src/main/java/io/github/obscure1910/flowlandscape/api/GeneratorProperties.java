package io.github.obscure1910.flowlandscape.api;

import java.nio.file.Path;
import java.util.Objects;

public class GeneratorProperties {

    private final Path outputDirectory;
    private final Double spaceBetweenElements;

    private final String headLine;

    public GeneratorProperties(Path outputDirectory, Double spaceBetweenElements, String headLine) {
        this.outputDirectory = outputDirectory;
        this.spaceBetweenElements = spaceBetweenElements;
        this.headLine = headLine;
    }

    public Path getOutputDirectory() {
        return outputDirectory;
    }

    public Double getSpaceBetweenElements() {
        return spaceBetweenElements;
    }

    public String getHeadLine() {
        return headLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneratorProperties that = (GeneratorProperties) o;

        if (!Objects.equals(outputDirectory, that.outputDirectory))
            return false;
        if (!Objects.equals(spaceBetweenElements, that.spaceBetweenElements))
            return false;
        return Objects.equals(headLine, that.headLine);
    }

    @Override
    public int hashCode() {
        int result = outputDirectory != null ? outputDirectory.hashCode() : 0;
        result = 31 * result + (spaceBetweenElements != null ? spaceBetweenElements.hashCode() : 0);
        result = 31 * result + (headLine != null ? headLine.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GeneratorConfiguration{" +
                "outputDirectory='" + outputDirectory + '\'' +
                ", spaceBetweenElements=" + spaceBetweenElements +
                ", headLine='" + headLine + '\'' +
                '}';
    }
}
