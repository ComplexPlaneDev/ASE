package com.github.ASE.Mediator;

public interface TowerController {
    void registerAircraft(Aircraft aircraft);

    void requestTakeoff(Aircraft aircraft);

    void requestLanding(Aircraft aircraft);

    void emergencyLanding(Aircraft aircraft);
}
