package com.github.ASE.Observer;

import com.github.ASE.Observer.Observers.GeneralJobSeeker;
import com.github.ASE.Observer.Observers.MarketingJobSeeker;
import com.github.ASE.Observer.Observers.SoftwareJobSeeker;
import com.github.ASE.Observer.Subjects.JobBoard;

public class App {

    public static void main(String[] args) {
        // Create job board
        JobBoard jobBoard = new JobBoard();

        // Register job seekers
        JobSeeker seeker1 = new SoftwareJobSeeker("Alice", "Engineer");
        JobSeeker seeker2 = new MarketingJobSeeker("Bob");
        JobSeeker seeker3 = new GeneralJobSeeker("Charlie");

        jobBoard.registerObserver(seeker1);
        jobBoard.registerObserver(seeker2);
        jobBoard.registerObserver(seeker3);

        // Company posts jobs
        System.out.println("\n--- Posting Software Engineer Job ---");
        jobBoard.addJob(new Job("Software Engineer", "Google"));

        System.out.println("\n--- Posting Marketing Manager Job ---");
        jobBoard.addJob(new Job("Marketing Manager", "Meta"));

        System.out.println("\n--- Posting Data Scientist Job ---");
        jobBoard.addJob(new Job("Data Scientist", "Amazon"));

        // Charlie unsubscribes
        System.out.println("\n--- Charlie unsubscribes ---");
        jobBoard.removeObserver(seeker3);

        System.out.println("\n--- Posting Senior Java Developer Job ---");
        jobBoard.addJob(new Job("Senior Java Developer", "SAP"));
    }
}
