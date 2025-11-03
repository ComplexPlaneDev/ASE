package com.github.ASE.Mediator;

public class Aircraft {
    private final String id;
    private final TowerController tower;
    private boolean isEmergency = false;

    public Aircraft(String id, TowerController tower) {
        this.id = id;
        this.tower = tower;
        tower.registerAircraft(this);
    }

    public void requestTakeoff() {
        System.out.println(id + ": Request takeoff");
        tower.requestTakeoff(this);
    }

    public void requestLanding() {
        System.out.println(id + ": Request landing");
        tower.requestLanding(this);
    }

    public void emergencyLandingRequest() {
        System.out.println(id + ": Request EMERGENCY LANDING!!");
        tower.emergencyLanding(this);
    }

    public void startTakeoff(Runnable onCompletion) {
        System.out.println(id + ": Taking off now.");
        simulateRunwayUsage(4000, onCompletion);
    }

    public void startLanding(Runnable onCompletion) {
        System.out.println(id + ": Landing now.");
        simulateRunwayUsage(5000, onCompletion);
    }

    private void simulateRunwayUsage(int delayMs, Runnable callback) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMs); // Simulate runway usage time
                callback.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean isEmergency) {
        this.isEmergency = isEmergency;
    }

    public String getId() {
        return id;
    }
}
