package com.unrise.webapp;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
        deadBlockExample();
    }

    private static void deadBlockExample() {
        String account1 = "account1";
        String account2 = "account2";
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Runnable task1 = () -> {
                synchronized (account1) {
                    System.out.println("thread1: " + account1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (account2) {
                        System.out.println("thread1: " + account2);
                    }
                }
            };

            Runnable task2 = () -> {
                synchronized (account2) {
                    System.out.println("thread2: " + account2);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (account1) {
                        System.out.println("thread2: " + account1);
                    }
                }
            };

            executor.submit(task1);
            executor.submit(task2);

            executor.shutdown();
        }
    }

}
