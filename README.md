# Mule Flow Landscape

This plugin creates a component overview of all flows and shows their dependencies. Dependencies via "flow-ref" are black arrows. Dependencies via "lookup" are red dashed arrows. This plugin works for Mule3 and Mule4.

## install

All released versions are available via [mvnrepository.com](https://mvnrepository.com/search?q=io.github.obscure1910)


Add this plugin to your pom.xml

    <plugin>
        <groupId>io.github.obscure1910</groupId>
        <artifactId>flow-landscape-maven-plugin</artifactId>
        <version>1.0.1</version>
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
            <parserSourceDirectory>src/main/mule/</parserSourceDirectory>
            <parserResourceDirectory>src/main/resources/</parserResourceDirectory>
        </configuration>
    </plugin>

## run the plugin

    mvn flow-landscape:generate

## example

In the Folder `flowlandscape-example` is a Mule4 application that generates the following diagram

<img src="flowlandscape-example/flowlandscape/flowlandscape.png"
     alt="Markdown Monster icon"
     style="height: 600px; margin-left: auto; margin-right: auto; display:block; text-align:center" />

## limitations

- only static dependencies via flow-ref or lookup can be detected (no variables as target)
- lookups in DataWeave files do not go recursive through the import statements
