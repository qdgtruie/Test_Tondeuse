package com.publicissapient.tondeuse.domain.configuration;


import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import com.publicissapient.tondeuse.domain.Position;
import lombok.ToString;
import lombok.Value;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.function.Consumer;

@Value
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "endsAt")
public class GardenConfiguration {

    private Queue<Consumer<InvalidMoveEventArg>> consummers = new LinkedList<>();
    private final Position upperRight;

    public boolean isValideMove(UUID mownerID, Position target) {
        boolean valid = target.isWithinRectangle(upperRight);
        if(!valid)
            for (var consummer:consummers) {
                consummer.accept(InvalidMoveEventArg.from(mownerID,target));
            }
        return valid;
    }

    public void addFenceViolationListener(Consumer<InvalidMoveEventArg> supplier) {
        consummers.add(supplier);
    }



}
