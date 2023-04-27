package io.github.obscure1910.flowlandscape.parser;

import org.w3c.dom.Document;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.Iterator;

class XPathFunctions {

    static <A> A withXPath(ThrowingFunction<XPath, A, Exception> f) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            return f.apply(xPath);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    static <A> A withXPathNS(Document document, ThrowingFunction<XPath, A, Exception> f) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        xPath.setNamespaceContext(new NamespaceResolver(document));
        try {
            return f.apply(xPath);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @FunctionalInterface
    interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    private static class NamespaceResolver implements NamespaceContext {
        //Store the source document to search the namespaces
        private final Document sourceDocument;

        public NamespaceResolver(Document document) {
            sourceDocument = document;
        }

        //The lookup for the namespace uris is delegated to the stored document.
        public String getNamespaceURI(String prefix) {
            if("jms".equals(prefix)) {
                return "http://www.mulesoft.org/schema/mule/jms";
            } else {
                return sourceDocument.lookupNamespaceURI(prefix);
            }
        }

        public String getPrefix(String namespaceURI) {
            if("http://www.mulesoft.org/schema/mule/jms".equals(namespaceURI)) {
                return "jms";
            } else {
                return sourceDocument.lookupPrefix(namespaceURI);
            }
        }

        @SuppressWarnings("rawtypes")
        public Iterator getPrefixes(String namespaceURI) {
            return null;
        }
    }

}
