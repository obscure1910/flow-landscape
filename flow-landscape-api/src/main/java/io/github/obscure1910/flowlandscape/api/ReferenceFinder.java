package io.github.obscure1910.flowlandscape.api;

import java.util.List;

public interface ReferenceFinder {

    List<ConfigurationHolder> findReferences(ReferenceFinderProperties referenceFinderProperties);

}
