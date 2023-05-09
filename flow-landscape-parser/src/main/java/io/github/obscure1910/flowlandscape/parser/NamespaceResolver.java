package io.github.obscure1910.flowlandscape.parser;

import org.w3c.dom.Document;

import javax.xml.namespace.NamespaceContext;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class NamespaceResolver implements NamespaceContext {
    //Store the source document to search the namespaces
    private final Document sourceDocument;

    private final Namespace[] namespaces = new Namespace[]{
            new Namespace("jms", "http://www.mulesoft.org/schema/mule/jms")
    };

    private Optional<String> findReverse(String str) {
        return Arrays.stream(namespaces)
                .map(ns -> ns.reverse(str))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    public NamespaceResolver(Document document) {
        sourceDocument = document;
    }

    //The lookup for the namespace uris is delegated to the stored document.
    public String getNamespaceURI(String prefix) {
        return findReverse(prefix)
                .orElseGet(() -> sourceDocument.lookupNamespaceURI(prefix));
    }

    public String getPrefix(String namespaceURI) {
        return findReverse(namespaceURI)
                .orElseGet(() -> sourceDocument.lookupPrefix(namespaceURI));
    }

    @SuppressWarnings("rawtypes")
    public Iterator getPrefixes(String namespaceURI) {
        return null;
    }

}