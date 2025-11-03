package com.github.ASE.Observer.Observers;

import java.util.List;

import com.github.ASE.Observer.Job;
import com.github.ASE.Observer.JobSeeker;
import com.github.ASE.Observer.Subjects.JobBoard;

public class MarketingJobSeeker implements JobSeeker {
    private final String name;

    public MarketingJobSeeker(String name) {
        this.name = name;
    }

    @Override
    public void update(JobBoard jobBoard) {
        List<Job> recentJobs = jobBoard.getRecentJobs();
        Job latestJob = recentJobs.get(recentJobs.size() - 1);
        if (latestJob.getTitle().toLowerCase().contains("marketing")) {
            System.out.println(name + ": New marketing job posted - " + latestJob.getTitle());
        }
    }
}
