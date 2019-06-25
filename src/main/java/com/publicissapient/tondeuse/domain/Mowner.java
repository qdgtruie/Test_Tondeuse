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
 * Reification of a mowner which can be controlled to move and can prtovide its position
 */
@Slf4j
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC,staticName = "initialLocation")
public class Mowner implements Controllable, PositionProvider {

    /**
     * Maintain listener for collision
     */
    private Queue<Consumer<InvalidMoveEventArg>> consummers = new LinkedList<>();

    /**
     * ID of the Mowner
     */
    @EqualsAndHashCode.Include
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private UUID id;

    /**
     * Current position & orientation of the mowner
     */
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private MownerLocation currentLocation;


    /**
     * Add a mown boundary checker to the mowner
     * @param positionCheck predicate to perform the check
     */
    public void addPositionChecker(Predicate<Position> positionCheck ){

        getCurrentLocation().addPositionListener(positionCheck);
    }

    /**
     * Request the Mowner to turn Right
     */
    @Override
    public void turnRight() {
        getCurrentLocation().shiftRight();
    }

    /**
     * Request the Mowner to turn Left
     */
    @Override
    public void turnLeft() {
        getCurrentLocation().shiftLeft();
    }

    /**
     * Request the Mowner to move forward.
     */
    @Override
    public void moveForward(){

        getCurrentLocation().shiftForward();
    }


    /**
     * Check whether a given position conflict with current mowner position
     * @param mownerID id of the mowner attempting a move
     * @param targetPosition position to be check against
     * @return true if the position do not create a conflict
     */
    public boolean checkcollision(UUID mownerID, Position targetPosition) {
        boolean valid =  ! targetPosition.equals(this.currentLocation.getPosition());

        if(!valid)
            for (var consummer:consummers)
                consummer.accept(InvalidMoveEventArg.from(mownerID,targetPosition));

        return valid;
    }

    public void addCollisionListener(Consumer<InvalidMoveEventArg> supplier) {
        consummers.add(supplier);
    }

}
