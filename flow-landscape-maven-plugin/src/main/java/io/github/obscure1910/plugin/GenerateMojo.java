package io.github.obscure1910.plugin;

import io.github.obscure1910.flowlandscape.api.*;
import io.github.obscure1910.flowlandscape.generator.GraphvizFlowLandscape;
import io.github.obscure1910.flowlandscape.parser.XPathReferenceFinder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateMojo extends AbstractMojo {

    private static final Logger logger = LoggerFactory.getLogger("Flow-Landscape");

    @Parameter(property = "imageSpaceBetweenElements", defaultValue = "2.0")
    Double imageSpaceBetweenElements;

    @Parameter(property = "imageHeadline", defaultValue = "FlowLandscape")
    String imageHeadline;

    @Parameter(property = "imageFont", defaultValue = "Arial")
    String imageFont;

    @Parameter(property = "imageTotalMemory", defaultValue = "33554432")
    int imageTotalMemory;

    @Parameter(property = "imageFormat", defaultValue = "PNG")
    String imageFormat;

    @Parameter(property = "imageWidth", defaultValue = "12000")
    int imageWidth;

    @Parameter(property = "imageOutputDirectory", defaultValue = "flowlandscape/")
    String imageOutputDirectory;

    @Parameter(property = "parserSourceDirectory", defaultValue = "src/main/mule/")
    String parserSourceDirectory;

    @Parameter(property = "parserResourceDirectory", defaultValue = "src/main/resources/")
    String parserResourceDirectory;

    @Override
    public void execute() {
        Path sourceDirectory = new File(parserSourceDirectory).toPath();
        Path resourceDirectory = new File(parserResourceDirectory).toPath();
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(sourceDirectory, resourceDirectory);
        Path generatorOutputDirectory = new File(imageOutputDirectory).toPath();
        GeneratorProperties generatorProperties = new GeneratorProperties(generatorOutputDirectory, imageSpaceBetweenElements, imageHeadline, imageFont, ImageFormat.valueOf(imageFormat), imageTotalMemory, imageWidth);

        ReferenceFinder finder = new XPathReferenceFinder();
        List<ConfigurationHolder> configurations = finder.findReferences(referenceFinderProperties);
        FlowLandscapeGenerator generator = new GraphvizFlowLandscape();
        generator.generateConfigurations(configurations, generatorProperties);
        logger.info("Successfully generated image with Flow-Landscape! The generated image has been copied to the folder: {}", imageOutputDirectory);
    }
}
