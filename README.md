# Mule Flow Landscape

This plugin creates a component overview of all flows and shows their dependencies. 
- Dependencies via "flow-ref" are black arrows. 
- Dependencies via "lookup" are red dashed arrows 
- Dependencies via "jms", "vm" or "ibm-mq" are blue arrows.

This plugin works for Mule3 and Mule4.

## use cli

1. clone the project
2. change directory to flow-landscape-cli
3. build the project

        mvn clean install
4. create a function in your ~/.zshrc

        flowlandscape() {
            output=${PWD##*/}
            java -jar /c/flow-landscape/flow-landscape-cli/target/flowlandscape.jar -o ../flowlandscape/"$output" "$@"
        }
5. run the app in your mule project

        >flowlandscape --help
        
        Usage: flowlandscape [-HV] [-b=<imageSpaceBetweenElements>] [-f=<imageFont>]
                            [-h=<imageHeadline>] [-m=<imageTotalMemory>]
                            [-o=<imageOutputDirectory>] [-r=<parserResourceDirectory>]
                            [-s=<parserSourceDirectory>] [-t=<imageFormat>]
                            [-w=<imageWidth>]
        Creates a component overview of all flows and shows their dependencies
        -b, --imageSpaceBetweenElements=<imageSpaceBetweenElements>
                        Space between flows. Default: 1.5
        -f, --imageFont=<imageFont>
                        Font used in the diagram. Default: Times-Roman
        -h, --imageHeadline=<imageHeadline>
                        Headline that is generated into the image. Default:
                            flow-landscape
        -H, --help      display this help message
        -m, --imageTotalMemory=<imageTotalMemory>
                        How much memory can be used by the generator in order to
                            create the image. Default: 33554432
        -o, --imageOutputDirectory=<imageOutputDirectory>
                        Directory where the generated image should be saved. Default:
                            flowlandscape/
        -r, --parserResourceDirectory=<parserResourceDirectory>
                        Directory of resource files. Default: src/main/resources/
        -s, --parserSourceDirectory=<parserSourceDirectory>
                        Directory of mule configuration files. Default: src/main/mule/
        -t, --imageFormat=<imageFormat>
                        Supported image formats (SVG or PNG). Default: PNG
        -V, --version   display version info
        -w, --imageWidth=<imageWidth>
                        Width. Default: 12000

## use maven plugin

All released versions are available via [mvnrepository.com](https://mvnrepository.com/search?q=io.github.obscure1910)


Add this plugin to your pom.xml

    <plugin>
        <groupId>io.github.obscure1910</groupId>
        <artifactId>flow-landscape-maven-plugin</artifactId>
        <version>1.1.5</version>
        <executions>
            <execution>
                <goals>
                    <goal>generate</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <imageHeadline>${project.name}</imageHeadline>
            <imageSpaceBetweenElements>1.5</imageSpaceBetweenElements>
            <imageOutputDirectory>flowlandscape/</imageOutputDirectory>
            <imageFont>Times-Roman</imageFont>
            <parserSourceDirectory>src/main/mule/</parserSourceDirectory>
            <parserResourceDirectory>src/main/resources/</parserResourceDirectory>
        </configuration>
    </plugin>

### run the plugin

    mvn flow-landscape:generate

## example

In the Folder `flowlandscape-example` is a Mule4 application that generates the following diagram

<img src="flow-landscape-example/flowlandscape/flowlandscape.png"
     alt="Markdown Monster icon"
     style="height: 600px; margin-left: auto; margin-right: auto; display:block; text-align:center" />

## limitations

- only static dependencies via flow-ref or lookup can be detected (no variables as target)
- lookups in DataWeave files do not go recursive through the import statements
