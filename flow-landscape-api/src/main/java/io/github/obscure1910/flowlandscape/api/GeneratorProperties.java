package io.github.obscure1910.flowlandscape.api;

import java.nio.file.Path;
import java.util.Objects;

public class GeneratorProperties {

    private final Path outputDirectory;
    private final Double spaceBetweenElements;
    private final String headLine;

    private final String fontName;

    public GeneratorProperties(Path outputDirectory, Double spaceBetweenElements, String headLine, String fontName) {
        this.outputDirectory = outputDirectory;
        this.spaceBetweenElements = spaceBetweenElements;
        this.headLine = headLine;
        this.fontName = fontName;
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

    public String getFontName() {
        return fontName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneratorProperties)) return false;
        GeneratorProperties that = (GeneratorProperties) o;
        return Objects.equals(outputDirectory, that.outputDirectory) && Objects.equals(spaceBetweenElements, that.spaceBetweenElements) && Objects.equals(headLine, that.headLine) && Objects.equals(fontName, that.fontName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputDirectory, spaceBetweenElements, headLine, fontName);
    }
}
