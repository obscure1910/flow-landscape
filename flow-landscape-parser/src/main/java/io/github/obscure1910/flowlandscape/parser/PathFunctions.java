package io.github.obscure1910.flowlandscape.parser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PathFunctions {

    static <A> List<A> withXmlFiles(Path path, Function<File, A> f) {
        try (Stream<Path> paths = Files.walk(path, Integer.MAX_VALUE)) {
            Stream<File> fileStream = paths
                    .flatMap(p -> {
                        File[] files = p.toFile().listFiles();
                        return (files != null) ? Arrays.stream(files) : Stream.empty();})
                    .filter(file -> file.getName().endsWith(".xml"));
            return fileStream.map(f).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
