package io.github.obscure1910.flowlandscape.parser.model;

import io.github.obscure1910.flowlandscape.api.FlowHolder;
import io.github.obscure1910.flowlandscape.api.ReferenceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Flow implements FlowHolder {

    private final List<ReferenceHolder> flowReferences;
    private final String name;

    public Flow(String name, List<ReferenceHolder> flowReferences) {
        this.flowReferences = flowReferences;
        this.name = name;
    }

    @Override
    public List<ReferenceHolder> getFlowReferences() {
        return new ArrayList<>(this.flowReferences);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flow flow = (Flow) o;

        if (!Objects.equals(flowReferences, flow.flowReferences))
            return false;
        return Objects.equals(name, flow.name);
    }

    @Override
    public int hashCode() {
        int result = flowReferences != null ? flowReferences.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flow{" +
                "flowReferences=" + flowReferences +
                ", name='" + name + '\'' +
                '}';
    }
}
