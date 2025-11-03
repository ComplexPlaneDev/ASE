package com.github.ASE.Observer.Observers;

import java.util.List;

import com.github.ASE.Observer.Job;
import com.github.ASE.Observer.JobSeeker;
import com.github.ASE.Observer.Subjects.JobBoard;

public class SoftwareJobSeeker implements JobSeeker {
    private final String name;
    private final String interestedIn;

    public SoftwareJobSeeker(String name, String interestedIn) {
        this.name = name;
        this.interestedIn = interestedIn;
    }

    @Override
    public void update(JobBoard jobBoard) {
        List<Job> recentJobs = jobBoard.getRecentJobs();
        Job latestJob = recentJobs.get(recentJobs.size() - 1);
        if (latestJob.getTitle().contains(interestedIn)) {
            System.out.println(name + ": New job matching your interest - " + latestJob.getTitle());
        }
    }
}
