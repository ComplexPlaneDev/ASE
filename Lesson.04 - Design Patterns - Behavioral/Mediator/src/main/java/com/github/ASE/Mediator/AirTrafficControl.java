package com.github.ASE.Mediator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class AirTrafficControl implements TowerController {
    private final List<Aircraft> aircraftList = new ArrayList<>();
    private final Queue<Aircraft> takeoffQueue = new LinkedList<>();
    private final Queue<Aircraft> landingQueue = new PriorityQueue<>(
            Comparator.comparing(Aircraft::isEmergency).reversed().thenComparing(Objects::hashCode));
    private boolean runwayInUse = false;

    @Override
    public void registerAircraft(Aircraft aircraft) {
        aircraftList.add(aircraft);
        System.out.println("Aircraft " + aircraft.getId() + " registered with ATC");
    }

    @Override
    public void requestTakeoff(Aircraft aircraft) {
        if (runwayInUse) {
            System.out.println("ATC: Runway busy. " + aircraft.getId() + " must wait for takeoff clearance.");
            takeoffQueue.add(aircraft);
        } else {
            grantTakeoffClearance(aircraft);
        }
    }

    @Override
    public void requestLanding(Aircraft aircraft) {
        if (runwayInUse) {
            System.out.println("ATC: Runway busy. " + aircraft.getId() + " must hold for landing.");
            landingQueue.add(aircraft);
        } else {
            grantLandingClearance(aircraft);
        }
    }

    @Override
    public void emergencyLanding(Aircraft aircraft) {
        aircraft.setEmergency(true);
        System.out.println("ATC: Emergency landing requested by " + aircraft.getId());
        if (runwayInUse) {
            // Insert at front of landing queue
            landingQueue.remove(aircraft); // Avoid duplicates
            landingQueue.add(aircraft);
        } else {
            grantLandingClearance(aircraft);
        }
    }

    private void grantTakeoffClearance(Aircraft aircraft) {
        runwayInUse = true;
        System.out.println("ATC: " + aircraft.getId() + " cleared for takeoff.");
        aircraft.startTakeoff(() -> {
            runwayInUse = false;
            System.out.println("Runway now free.");
            processQueues();
        });
    }

    private void grantLandingClearance(Aircraft aircraft) {
        runwayInUse = true;
        System.out.println("ATC: " + aircraft.getId() + " cleared for landing.");
        aircraft.startLanding(() -> {
            runwayInUse = false;
            System.out.println("Runway now free.");
            processQueues();
        });
    }

    private void processQueues() {
        if (!landingQueue.isEmpty()) {
            grantLandingClearance(landingQueue.poll());
        } else if (!takeoffQueue.isEmpty()) {
            grantTakeoffClearance(takeoffQueue.poll());
        }
    }
}
