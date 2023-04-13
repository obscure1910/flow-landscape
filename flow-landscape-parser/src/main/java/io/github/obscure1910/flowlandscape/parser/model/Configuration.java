package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.flow.FlowHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Configuration implements ConfigurationHolder {

    private final String name;
    private final List<FlowHolder> flows;

    public Configuration(String name, List<FlowHolder> flows) {
        this.name = name;
        this.flows = flows;
    }

    public String getName() {
        return this.name;
    }

    public List<FlowHolder> getFlows() {
        return new ArrayList<>(this.flows);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration)) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(name, that.name) && Objects.equals(flows, that.flows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, flows);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", flows=" + flows +
                '}';
    }
}
