package io.github.obscure1910.flowlandscape.api;

import java.util.List;

public interface FlowLandscapeGenerator {

    void generateConfigurations(List<ConfigurationHolder> configurations, GeneratorProperties generatorProperties);

}
