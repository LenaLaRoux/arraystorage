package com.unrise.webapp;

import java.io.File;
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
        File currentDir = new File("./src");
        traverseDirectory(currentDir, 0);
    }
}
