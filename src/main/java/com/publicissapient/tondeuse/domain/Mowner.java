package com.publicissapient.tondeuse.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "initialLocation")
public class Mowner implements Controllable {

    private UUID ID;
    @Getter(AccessLevel.PRIVATE)
    private MownerLocation currentLocation;

    @Override
    public void turnRight() {
        getCurrentLocation().ShiftRight();
    }

    @Override
    public void turnLeft() {
        getCurrentLocation().ShiftLeft();
    }

    @Override
    public void moveForward() {
        getCurrentLocation().ShiftForward();
    }

    @Override
    public void reportOnDuty() {
        log.info("[Mowner "+this.ID.toString()+"] Job complete : position is "+this.getCurrentLocation().toString());
    }
}
