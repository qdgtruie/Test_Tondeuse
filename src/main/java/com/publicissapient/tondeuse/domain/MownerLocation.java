package com.publicissapient.tondeuse.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * Define the location (orientation + position) of a mowner
 */
@Slf4j
@ToString(exclude = "positionListener")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "with")
public class MownerLocation {

    /**
     * Current position of the location
     */
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private Position position;

    /**
     * Current Orientation of the location
     */
    @Getter
    @NonNull
    private Orientation orientation;


    /**
     * Queue of hanlder for position change notoification
     */
    private final Queue<Predicate<Position>> positionListener = new LinkedList<>();

    /**
     * Change orientation to the right
     */
    void shiftRight() {

        switch (orientation) {
            case N:
                orientation = Orientation.E;
                break;
            case E:
                orientation = Orientation.S;
                break;
            case S:
                orientation = Orientation.W;
                break;
            case W:
                orientation = Orientation.N;
                break;
        }

    }

    /**
     * Change orientation to the right
     */
    void shiftLeft() {

        switch (orientation) {
            case N:
                orientation = Orientation.W;
                break;
            case E:
                orientation = Orientation.N;
                break;
            case S:
                orientation = Orientation.E;
                break;
            case W:
                orientation = Orientation.S;
                break;
        }
    }

    /**
     * Change position by moving forward
     */
     void shiftForward() {

        int nextX = position.getX();
        int nextY = position.getY();

        switch (orientation) {
            case N:
                nextY = position.getY() + 1;
                break;
            case E:
                nextX = position.getX() + 1;
                break;
            case S:
                nextY = position.getY() - 1;
                break;
            case W:
                nextX = position.getX() - 1;
                break;
            default:
                break;
        }

        //triger event prior to actually changing position
        if (onMovingTo(Position.locatedAt(nextX, nextY))) {
            position.setY(nextY);
            position.setX(nextX);
        }
        else
            if(log.isDebugEnabled())
                log.debug("Illegal position transition was attemped from ({},{}) to ({},{})",
                    position.getX(),position.getY(), nextX,nextY);

    }

    /**
     * Trigger event associated to the moving attempt
     * @param movingTo location to move to
     * @return whether, at least, a listener prevented the move
     */
    private boolean onMovingTo(Position movingTo) {

        return positionListener.stream().allMatch(check -> check.test(movingTo));

    }

    /**
     * Register a listener on move attempt event
     * @param check a handler for the event
     */
     void addPositionListener(Predicate<Position> check){
        positionListener.add(check);
    }

    /**
     * Test equallity between two Location
     * @param o an object to test against.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof MownerLocation)) return false;
        MownerLocation other = (MownerLocation) o;
        if (!other.canEqual(this)) return false;
        if (this.getPosition() == null ? other.getPosition() != null : !this.getPosition().equals(other.getPosition())) return false;
        return this.getOrientation().equals(other.getOrientation());

    }

    private boolean canEqual(Object other) {
        return other instanceof MownerLocation;
    }

    /**
     * Compute hash code
     * @return a hash code value for the object.
     */
    @Override public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = (result*PRIME) + super.hashCode();
        result = (result*PRIME) + this.getPosition().hashCode();
        result = (result*PRIME) + this.getOrientation().hashCode();
        return result;
    }

}
