package com.github.ASE.Command.Receivers;

public class Report {
    public String generate(String type) {
        System.out.println("Generating " + type + " report");
        return "Report Content";
    }

    public void archive(String reportId) {
        System.out.println("Archiving report: " + reportId);
    }
}
