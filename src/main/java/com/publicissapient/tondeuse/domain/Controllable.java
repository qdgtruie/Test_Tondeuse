package com.publicissapient.tondeuse.domain;

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
