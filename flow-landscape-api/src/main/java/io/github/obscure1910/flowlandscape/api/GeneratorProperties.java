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
        if (!(o instanceof GeneratorProperties)) return false;
        GeneratorProperties that = (GeneratorProperties) o;
        return Objects.equals(outputDirectory, that.outputDirectory) && Objects.equals(spaceBetweenElements, that.spaceBetweenElements) && Objects.equals(headLine, that.headLine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputDirectory, spaceBetweenElements, headLine);
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
