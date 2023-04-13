package io.github.obscure1910.flowlandscape.parser;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

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

}
