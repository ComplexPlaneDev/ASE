package com.github.ASE.Command.Commands;

import com.github.ASE.Command.Receivers.Database;

public class BackupDatabase extends Schedulable {
    private final Database databaseManager;
    private final String dbName;

    public BackupDatabase(Database databaseManager, String dbName) {
        this.databaseManager = databaseManager;
        this.dbName = dbName;
    }

    @Override
    public void execute() {
        databaseManager.backup(dbName);
    }

    @Override
    public void undo() {
        databaseManager.restore(dbName);
    }
}
