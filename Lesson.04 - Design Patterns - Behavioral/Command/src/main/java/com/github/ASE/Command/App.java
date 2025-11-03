package com.github.ASE.Command;

import com.github.ASE.Command.Commands.BackupDatabase;
import com.github.ASE.Command.Commands.GenerateReport;
import com.github.ASE.Command.Commands.Macro;
import com.github.ASE.Command.Commands.SendEmail;
import com.github.ASE.Command.Receivers.Database;
import com.github.ASE.Command.Receivers.Email;
import com.github.ASE.Command.Receivers.Report;

public class App {

    public static void main(String[] args) {
        // Initialize receivers
        Email emailService = new Email();
        Report reportGenerator = new Report();
        Database databaseManager = new Database();

        // Create individual commands
        Command sendEmail = new SendEmail(emailService, "client@example.com", "Monthly Report",
                "Attached is your monthly report.");
        Command generateReport = new GenerateReport(reportGenerator, "Sales");
        Command backupDB = new BackupDatabase(databaseManager, "CustomerDB");

        // Create macro command (group of commands)
        Macro macro = new Macro();
        macro.addCommand(generateReport);
        macro.addCommand(sendEmail);
        macro.addCommand(backupDB);

        // Use invoker to execute commands
        Invoker invoker = new Invoker();
        System.out.println("== Executing Macro Command ==");
        invoker.executeCommand(macro);

        // Undo the entire macro
        System.out.println("\n== Undoing All Tasks ==");
        invoker.undoLastCommand();

        // Schedule a backup for later
        System.out.println("\n== Scheduling Backup for 3 seconds later ==");
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(new BackupDatabase(databaseManager, "UserDB"), 3000);

    }
}
