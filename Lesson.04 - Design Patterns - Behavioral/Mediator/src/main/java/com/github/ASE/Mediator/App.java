package com.github.ASE.Mediator;

public class App {

    public static void main(String[] args) {
        // Create mediator
        TowerController tower = new AirTrafficControl();

        // Create aircraft
        Aircraft flight1 = new Aircraft("Flight 100", tower);
        Aircraft flight2 = new Aircraft("Flight 200", tower);
        Aircraft drone1 = new Aircraft("Drone X", tower);

        // Simulate flight operations
        System.out.println("\n--- Simulating Flight Requests ---");
        flight1.requestTakeoff(); // Cleared immediately
        flight2.requestLanding(); // Cleared immediately

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- Emergency Landing Request ---");
        drone1.emergencyLandingRequest(); // Should preempt queue

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--- More Requests During Busy Runway ---");
        Aircraft flight3 = new Aircraft("Flight 300", tower);
        Aircraft flight4 = new Aircraft("Flight 400", tower);
        Aircraft drone2 = new Aircraft("Drone Y", tower);

        flight3.requestLanding(); // Queued
        drone2.requestTakeoff(); // Queued
        flight4.requestLanding(); // Queued
    }
}
