package com.publicissapient.tondeuse.domain;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Reification of a mowner which can be controlled to move and can prtovide its position
 */
@Slf4j
@ToString
@RequiredArgsConstructor(access = AccessLevel.PUBLIC,staticName = "initialLocation")
public class Mowner implements Controllable, PositionProvider {

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
    public void addOffBoundChecker(Predicate<Position> positionCheck ){

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


}
