package com.github.ASE.Observer.Subjects;

import java.util.ArrayList;
import java.util.List;

import com.github.ASE.Observer.Job;
import com.github.ASE.Observer.JobPosting;
import com.github.ASE.Observer.JobSeeker;

public class JobBoard implements JobPosting {
    private final List<JobSeeker> observers = new ArrayList<>();
    private final List<Job> jobList = new ArrayList<>();

    @Override
    public void registerObserver(JobSeeker observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(JobSeeker observer) {
        observers.remove(observer);
    }

    @Override
    public void addJob(Job job) {
        jobList.add(job);
        System.out.println("New job posted: " + job.getTitle());
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (JobSeeker observer : observers) {
            observer.update(this);
        }
    }

    public List<Job> getRecentJobs() {
        return new ArrayList<>(jobList);
    }
}
