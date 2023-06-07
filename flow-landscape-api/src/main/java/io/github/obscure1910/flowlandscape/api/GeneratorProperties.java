package io.github.obscure1910.flowlandscape.api;

import java.nio.file.Path;
import java.util.Objects;

public class GeneratorProperties {

    private final Path outputDirectory;
    private final Double spaceBetweenElements;
    private final String headLine;

    private final String fontName;

    private final ImageFormat format;

    private final int totalMemory;

    private final int width;

    public GeneratorProperties(Path outputDirectory, Double spaceBetweenElements, String headLine, String fontName, ImageFormat imageFormat, int totalMemory, int width) {
        this.outputDirectory = outputDirectory;
        this.spaceBetweenElements = spaceBetweenElements;
        this.headLine = headLine;
        this.fontName = fontName;
        this.format = imageFormat;
        this.totalMemory = totalMemory;
        this.width = width;
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

    public int getWidth() {
        return width;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeneratorProperties)) return false;
        GeneratorProperties that = (GeneratorProperties) o;
        return totalMemory == that.totalMemory && width == that.width && Objects.equals(outputDirectory, that.outputDirectory) && Objects.equals(spaceBetweenElements, that.spaceBetweenElements) && Objects.equals(headLine, that.headLine) && Objects.equals(fontName, that.fontName) && format == that.format;
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputDirectory, spaceBetweenElements, headLine, fontName, format, totalMemory, width);
    }
}
