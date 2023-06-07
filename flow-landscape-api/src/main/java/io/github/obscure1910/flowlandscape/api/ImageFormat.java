package io.github.obscure1910.flowlandscape.api;

public enum ImageFormat {
    SVG("SVG", "svg"), PNG("PNG", "png");

    private final String type;
    private final String extension;

    ImageFormat(String type, String extension) {
        this.type = type;
        this.extension = extension;
    }

    public String getType() {
        return this.type;
    }

    public String getExtension() {
        return this.extension;
    }

}
