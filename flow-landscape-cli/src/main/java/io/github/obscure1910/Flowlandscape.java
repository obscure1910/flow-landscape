package io.github.obscure1910;

import io.github.obscure1910.flowlandscape.api.*;
import io.github.obscure1910.flowlandscape.generator.GraphvizFlowLandscape;
import io.github.obscure1910.flowlandscape.parser.XPathReferenceFinder;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "flowlandscape", mixinStandardHelpOptions = true, version = "flowlandscape cli  1.1",
        description = "Creates a component overview of all flows and shows their dependencies")
public class Flowlandscape implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--imageFont"},
            defaultValue = "Times-Roman",
            description = "Font used in the diagram.")
    private String imageFont;

    @CommandLine.Option(names = {"-h", "--imageHeadline"},
            defaultValue = "flow-landscape",
            description = "Headline that is generated into the image.")
    private String imageHeadline;

    @CommandLine.Option(names = {"-o", "--imageOutputDirectory"},
            defaultValue = "flowlandscape/",
            description = "Directory where the generated image should be saved.")
    private String imageOutputDirectory;

    @CommandLine.Option(names = {"-s", "--parserSourceDirectory"},
            defaultValue = "src/main/mule/",
            description = "Directory of mule configuration files.")
    private String parserSourceDirectory;

    @CommandLine.Option(names = {"-r", "--parserResourceDirectory"},
            defaultValue = "src/main/resources/",
            description = "Directory of resource files.")
    private String parserResourceDirectory;

    @CommandLine.Option(names = {"-b", "--imageSpaceBetweenElements"},
            defaultValue = "1.5",
            description = "Space between flows.")
    private Double imageSpaceBetweenElements;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "display version info")
    boolean versionInfoRequested;

    @CommandLine.Option(names = {"-H", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Override
    public Integer call() throws Exception {
        Path sourceDirectory = new File(parserSourceDirectory).toPath();
        Path resourceDirectory = new File(parserResourceDirectory).toPath();
        ReferenceFinderProperties referenceFinderProperties = new ReferenceFinderProperties(sourceDirectory, resourceDirectory);
        Path generatorOutputDirectory = new File(imageOutputDirectory).toPath();
        GeneratorProperties generatorProperties = new GeneratorProperties(generatorOutputDirectory, imageSpaceBetweenElements, imageHeadline, imageFont);

        ReferenceFinder finder = new XPathReferenceFinder();
        List<ConfigurationHolder> configurations = finder.findReferences(referenceFinderProperties);
        FlowLandscapeGenerator generator = new GraphvizFlowLandscape();
        generator.generateConfigurations(configurations, generatorProperties);
        return 1;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Flowlandscape()).execute(args);
        System.exit(exitCode);
    }


}
