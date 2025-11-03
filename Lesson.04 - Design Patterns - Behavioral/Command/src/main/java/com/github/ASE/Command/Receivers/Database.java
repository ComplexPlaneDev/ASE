package com.github.ASE.Command.Receivers;

public class Database {
    public void backup(String dbName) {
        System.out.println("Backing up database: " + dbName);
    }

    public void restore(String dbName) {
        System.out.println("Restoring database: " + dbName);
    }
}
