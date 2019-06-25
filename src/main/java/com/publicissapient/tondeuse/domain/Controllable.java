package com.publicissapient.tondeuse.domain;

/**
 * Common set of feature supported by all the mowers from MowItNow
 */
public interface Controllable {


    /**
     * Request the Controllable to turn Right
     */
    void turnRight();

    /**
     * Request the Controllable to turn Left
     */
    void turnLeft();

    /**
     * Request the Controllable to move forward
     */
    void moveForward();

}
