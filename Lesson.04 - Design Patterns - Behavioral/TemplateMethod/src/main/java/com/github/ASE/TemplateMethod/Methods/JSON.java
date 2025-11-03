package com.github.ASE.TemplateMethod.Methods;

import com.github.ASE.TemplateMethod.DataMigrator;

public class JSON extends DataMigrator {
    @Override
    protected String extractData() {
        System.out.println("Extracting JSON data...");
        return "{\"users\":[{\"id\":1,\"name\":\"Alice\"},{\"id\":2,\"name\":\"Charlie\"}]}";
    }

    @Override
    protected String transformData(String rawData) {
        System.out.println("JSON-specific transformation...");
        return rawData.replace("name", "username");
    }
}
