package com.github.ASE.Observer;

public interface JobPosting {
    void registerObserver(JobSeeker observer);

    void removeObserver(JobSeeker observer);

    void notifyObservers();

    void addJob(Job job);
}
