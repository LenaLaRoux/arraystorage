package com.unrise.webapp;

import java.io.File;

public class Main {
    public static void traverseDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        traverseDirectory(file);
                    } else {
                        System.out.println(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        File currentDir = new File("./src");
        traverseDirectory(currentDir);
    }
}
