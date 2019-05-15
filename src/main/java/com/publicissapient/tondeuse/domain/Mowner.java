package com.publicissapient.tondeuse.domain;


import lombok.*;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
    private UUID ID;

    /**
     * Current position & orientation of the mowner
     */
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private MownerLocation currentLocation;


    public void addOffBoundChecker(Predicate<Position> positionCheck ){ //Predicate<Position> check

        getCurrentLocation().addPositionListener(positionCheck);
    }

    /**
     * Request the Mowner to turn Right
     */
    @Override
    public void turnRight() {
        getCurrentLocation().ShiftRight();
    }

    @Override
    public void turnLeft() {
        getCurrentLocation().ShiftLeft();
    }

    @Override
    public void moveForward(){

        getCurrentLocation().ShiftForward();
    }



    /*@Override
    public void reportOnDuty(@NonNull Consumer<PositionProvider> positionConsumer) {
        positionConsumer.accept(this);
        log.debug("[Mowner "+this.ID.toString()+"] Job complete : position is "+this.getCurrentLocation().toString());
    }*/
}
