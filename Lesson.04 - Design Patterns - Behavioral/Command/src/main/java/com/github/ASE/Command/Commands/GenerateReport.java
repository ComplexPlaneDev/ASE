package com.github.ASE.Command.Commands;

import com.github.ASE.Command.Receivers.Report;

public class GenerateReport extends Schedulable {
    private final Report reportGenerator;
    private final String reportType;
    private String reportId;

    public GenerateReport(Report reportGenerator, String reportType) {
        this.reportGenerator = reportGenerator;
        this.reportType = reportType;
        this.reportId = "RPT-" + System.currentTimeMillis();
    }

    @Override
    public void execute() {
        String content = reportGenerator.generate(reportType);
        System.out.println("Report ID: " + reportId);
    }

    @Override
    public void undo() {
        reportGenerator.archive(reportId);
    }
}
