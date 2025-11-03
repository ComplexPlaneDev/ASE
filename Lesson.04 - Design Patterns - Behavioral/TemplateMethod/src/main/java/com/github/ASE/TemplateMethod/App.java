package com.github.ASE.TemplateMethod;

import com.github.ASE.TemplateMethod.Methods.CSV;
import com.github.ASE.TemplateMethod.Methods.JSON;
import com.github.ASE.TemplateMethod.Methods.XML;

public class App {

    public static void main(String[] args) {
        // Migrate CSV data
        System.out.println("== CSV Migration ==");
        runMigration(new CSV());

        // Migrate JSON data
        System.out.println("== JSON Migration ==");
        runMigration(new JSON());

        // Migrate XML data
        System.out.println("== XML Migration ==");
        runMigration(new XML());
    }

    private static void runMigration(DataMigrator migrator) {
        System.out.println("Starting migration...");
        migrator.migrate();
        System.out.println("Migration completed.\n");
    }
}
