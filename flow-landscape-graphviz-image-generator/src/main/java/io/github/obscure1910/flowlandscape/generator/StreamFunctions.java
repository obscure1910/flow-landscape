package io.github.obscure1910.flowlandscape.generator;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;

class StreamFunctions {

    static <T, U> U foldLeft(Stream<T> stream, U seed, BiFunction<U, ? super T, U> function) {
        final Iterator<T> it = stream.iterator();
        U result = seed;

        while (it.hasNext())
            result = function.apply(result, it.next());

        return result;
    }

}
