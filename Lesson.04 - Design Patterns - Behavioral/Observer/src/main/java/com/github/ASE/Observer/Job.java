package com.github.ASE.Observer;

public class Job {
    private final String title;
    private final String company;

    public Job(String title, String company) {
        this.title = title;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return title + " at " + company;
    }
}
