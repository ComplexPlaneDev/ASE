package com.github.ASE.Interpreter;

import java.time.LocalTime;

public class SmartHomeContext {
    private final LocalTime currentTime;
    private boolean motionDetected;
    private double currentTemperature;

    public SmartHomeContext(LocalTime currentTime, boolean motionDetected, double currentTemperature) {
        this.currentTime = currentTime;
        this.motionDetected = motionDetected;
        this.currentTemperature = currentTemperature;
    }

    // Simulated smart home devices
    public void turnOn(String device) {
        System.out.println("Turning on: " + device);
    }

    public void turnOff(String device) {
        System.out.println("Turning off: " + device);
    }

    public void lock(String device) {
        System.out.println("Locking: " + device);
    }

    public void unlock(String device) {
        System.out.println("Unlocking: " + device);
    }

    public void notifyUser(String message) {
        System.out.println("Notification: " + message);
    }

    // Context state getters
    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    // Simulated setters for dynamic behavior
    public void setMotionDetected(boolean motionDetected) {
        this.motionDetected = motionDetected;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
}
