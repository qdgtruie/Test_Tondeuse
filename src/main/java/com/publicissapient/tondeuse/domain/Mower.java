package com.publicissapient.tondeuse.domain;


import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Reification of a mower which can be controlled to move and can provide its position
 */
@Slf4j
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC,staticName = "initialLocation")
public class Mower implements Controllable, PositionProvider {

    /**
     * Maintain listener for collision
     */
    private final Queue<Consumer<InvalidMoveEventArg>> consumers = new LinkedList<>();

    /**
     * ID of the Mower
     */
    @EqualsAndHashCode.Include
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private UUID id;

    /**
     * Current position & orientation of the mower
     */
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private MowerLocation currentLocation;


    /**
     * Add a mow boundary checker to the mower
     * @param positionCheck predicate to perform the check
     */
    public void addPositionChecker(Predicate<Position> positionCheck ){

        getCurrentLocation().addPositionListener(positionCheck);
    }

    /**
     * Request the Mower to turn Right
     */
    @Override
    public void turnRight() {
        getCurrentLocation().shiftRight();
    }

    /**
     * Request the Mower to turn Left
     */
    @Override
    public void turnLeft() {
        getCurrentLocation().shiftLeft();
    }

    /**
     * Request the Mower to move forward.
     */
    @Override
    public void moveForward(){

        getCurrentLocation().shiftForward();
    }


    /**
     * Check whether a given position conflict with current mower position
     * @param mowerID id of the mower attempting a move
     * @param targetPosition position to be check against
     * @return true if the position do not create a conflict
     */
    public boolean checkCollision(UUID mowerID, Position targetPosition) {
        boolean valid =  ! targetPosition.equals(this.currentLocation.getPosition());

        if(!valid)
            for (var consumer: consumers)
                consumer.accept(InvalidMoveEventArg.from(mowerID,targetPosition));

        return valid;
    }

    public void addCollisionListener(Consumer<InvalidMoveEventArg> supplier) {
        consumers.add(supplier);
    }

}
