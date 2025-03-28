package com.unrise.webapp;

import java.io.File;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void traverseDirectory(File directory, int level) {
        if (directory.isDirectory()) {
            System.out.println("\t".repeat(level) + directory.getName());
            level++;
            int levelFinal = level;
            File[] files = directory.listFiles();

            if (files == null)
                return;

            Stream.of(files)
                    .filter(File::isFile)
                    .forEach(file -> System.out.println("\t".repeat(levelFinal) + file.getName()));

            Stream.of(files)
                    .filter(File::isDirectory)
                    .forEach(file -> traverseDirectory(file, levelFinal));
        }
    }

    public static void main(String[] args) {
//        File currentDir = new File("./src");
//        traverseDirectory(currentDir, 0);

        int uniqueMin = getUniqueMin(1, 2, 3, 3, 2, 3);
        System.out.println(uniqueMin);
        int uniqueMin1 = getUniqueMin(9, 8);
        System.out.println(uniqueMin1);

        List<Integer> integers = oddOrEven(List.of(1, 2, 3, 4));
        System.out.println(integers);
        List<Integer> integers1 = oddOrEven(List.of(1, 2, 3, 4, 5, 6));
        System.out.println(integers1);
    }


    public static int getUniqueMin(int... arr) {
        OptionalInt collect = IntStream.of(arr)
                .distinct()
                .sorted()
                .reduce((a, b) -> a * 10 + b);

        return collect.orElse(0);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {

       return integers.stream()
                .collect(
                        Collectors.collectingAndThen(Collectors.summingInt(Integer::intValue),
                                summ -> integers.stream()
                                        .filter(n -> isOdd(summ) && !isOdd(n) || !isOdd(summ) && isOdd(n))
                                        .collect(Collectors.toList())));
    }
    private static boolean isOdd(Integer n){
        return n % 2 == 0;
    }
}
