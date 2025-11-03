package com.github.ASE.TemplateMethod.Methods;

import com.github.ASE.TemplateMethod.DataMigrator;

public class XML extends DataMigrator {
    @Override
    protected String extractData() {
        System.out.println("Extracting XML data...");
        return "<users><user id=\"1\">John</user><user id=\"2\">Doe</user></users>";
    }

    @Override
    protected String transformData(String rawData) {
        System.out.println("XML-specific transformation...");
        return rawData.replace("user", "customer");
    }
}
