package com.github.ASE.TemplateMethod;

public abstract class DataMigrator {
    // Final template method
    public final void migrate() {
        connect();
        validateFormat();
        String rawData = extractData();
        String transformedData = transformData(rawData);
        loadData(transformedData);
    }

    // Common steps
    protected void connect() {
        System.out.println("Connecting to data source...");
    }

    protected void validateFormat() {
        System.out.println("Validating data format...");
    }

    protected void loadData(String data) {
        System.out.println("Loading " + data + " into database...");
    }

    // Abstract method to be implemented by subclasses
    protected abstract String extractData();

    // Hook method (optional override)
    protected String transformData(String rawData) {
        System.out.println("Transforming data...");
        return rawData.trim().toUpperCase(); // Default transformation
    }
}
