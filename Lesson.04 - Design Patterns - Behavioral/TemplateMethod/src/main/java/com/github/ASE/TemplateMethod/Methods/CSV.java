package com.github.ASE.TemplateMethod.Methods;

import com.github.ASE.TemplateMethod.DataMigrator;

public class CSV extends DataMigrator {
    @Override
    protected String extractData() {
        System.out.println("Extracting CSV data...");
        return "id,name,email\n1,John,john@example.com\n2,Bob,bob@example.com";
    }

    @Override
    protected String transformData(String rawData) {
        System.out.println("CSV-specific transformation...");
        // Custom CSV parsing logic
        return rawData.replace("\n", " | ");
    }
}
