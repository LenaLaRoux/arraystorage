package com.unrise.webapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainDeadlock {
    public final static Account account1 = new Account(1, 200d);
    public final static Account account2 = new Account(2, 150d);

    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            Runnable task1 = () -> withdraw(account1, account2, 50d);
            Runnable task2 = () -> withdraw(account2, account1, 70d);
            executor.submit(task1);
            executor.submit(task2);
            executor.shutdown();
        }
    }
    public static void withdraw(Account accountFrom, Account accountTo, double amount){
        synchronized (accountFrom) {
            accountFrom.amount -= amount;
            System.out.println("Account id = " + accountFrom.id + " new amount " + accountFrom.amount);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (accountTo) {
                accountTo.amount += amount;
                System.out.println("Account id = " + accountTo.id + " new amount " + accountTo.amount);
            }
        }
    }

    public static class Account {
        double amount;
        int id;
        public  Account (int id, double amount)
        {
            this.id = id;
            this.amount = amount;
        }
    }
}
