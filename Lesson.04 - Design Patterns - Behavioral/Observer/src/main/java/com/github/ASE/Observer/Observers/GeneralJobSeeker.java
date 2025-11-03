package com.github.ASE.Observer.Observers;

import java.util.List;

import com.github.ASE.Observer.Job;
import com.github.ASE.Observer.JobSeeker;
import com.github.ASE.Observer.Subjects.JobBoard;

public class GeneralJobSeeker implements JobSeeker {
    private final String name;

    public GeneralJobSeeker(String name) {
        this.name = name;
    }

    @Override
    public void update(JobBoard jobBoard) {
        List<Job> recentJobs = jobBoard.getRecentJobs();
        Job latestJob = recentJobs.get(recentJobs.size() - 1);
        System.out.println(name + ": New job posted - " + latestJob.getTitle());
    }
}
