package de.obscure;

import io.github.obscure1910.flowlandscape.api.*;
import io.github.obscure1910.flowlandscape.generator.GraphvizFlowLandscape;
import io.github.obscure1910.flowlandscape.parser.XPathReferenceFinder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.COMPILE)
public class GenerateMojo extends AbstractMojo {

    @Parameter(property = "imageSpaceBetweenElements", defaultValue = "1.5")
    Double imageSpaceBetweenElements;

    @Parameter(property = "imageHeadline", defaultValue = "FlowLandscape")
    String imageHeadline;

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
        GeneratorProperties generatorProperties = new GeneratorProperties(generatorOutputDirectory, imageSpaceBetweenElements, imageHeadline);

        ReferenceFinder finder = new XPathReferenceFinder();
        List<ConfigurationHolder> configurations = finder.findReferences(referenceFinderProperties);
        FlowLandscapeGenerator generator = new GraphvizFlowLandscape();
        generator.generateConfigurations(configurations, generatorProperties);
    }
}
