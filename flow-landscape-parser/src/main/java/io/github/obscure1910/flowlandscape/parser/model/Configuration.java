package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.ConfigurationHolder;
import io.github.obscure1910.flowlandscape.api.FlowHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Configuration implements ConfigurationHolder {

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
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(flows, that.flows);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (flows != null ? flows.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", flows=" + flows +
                '}';
    }
}
