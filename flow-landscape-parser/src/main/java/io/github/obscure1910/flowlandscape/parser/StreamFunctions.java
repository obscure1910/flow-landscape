package io.github.obscure1910.flowlandscape.parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class StreamFunctions {

    static Stream<String> asStream(final Matcher matcher) {
        Iterable<String> iterable = () -> new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return matcher.find();
            }

            @Override
            public String next() {
                return matcher.group();
            }
        };
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    static Stream<Node> asStream(final NodeList nodeList) {
        Iterable<Node> iterable = () -> new Iterator<Node>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < nodeList.getLength();
            }

            @Override
            public Node next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return nodeList.item(index++);
            }
        };
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    @SafeVarargs
    static <A> Stream<A> streamConcat(Stream<A>... streams) {
        return Arrays.stream(streams).reduce(Stream.empty(), Stream::concat);
    }
}
