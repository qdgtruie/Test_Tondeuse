package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Position;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

class InvalidMoveEventArgTest {


    @Test
    void InstanceCanBeCreatedThroughFactory() {
        Tuple tuple = new Tuple().invoke();
        InvalidMoveEventArg instance = tuple.getInstance();

        assertEquals(tuple.getId(),instance.getMowerID(),"ID should be similar");
        assertEquals(tuple.getPosition(),instance.getTargetPosition(),"Position should be similar");

    }

    @Test
    void canSetMowerID() {
        Tuple tuple = new Tuple().invoke();

        var id = UUID.randomUUID();
        tuple.instance.setMowerID(id);
        assertEquals(id,tuple.instance.getMowerID(),"ID should be similar");

    }

    @Test
    void canSetTargetPosition() {

        Tuple tuple = new Tuple().invoke();
        var target = Position.locatedAt(5,5);
        tuple.instance.setTargetPosition(target);
        assertEquals(target,tuple.instance.getTargetPosition(),"Position should be similar");

    }

    @Test
    void canGetMowerID() {
        Tuple tuple = new Tuple().invoke();
        assertEquals(tuple.id,tuple.instance.getMowerID(),"ID should be similar");
    }

    @Test
    void canGetTargetPosition() {
        Tuple tuple = new Tuple().invoke();
        assertEquals(tuple.getPosition(),tuple.instance.getTargetPosition(),"Position should be similar");

    }

    private class Tuple {
        private UUID id;
        private Position position;
        private InvalidMoveEventArg instance;

        UUID getId() {
            return id;
        }

        Position getPosition() {
            return position;
        }

        InvalidMoveEventArg getInstance() {
            return instance;
        }

        Tuple invoke() {
            id = UUID.randomUUID();
            position = Position.locatedAt(0, 0);
            instance = InvalidMoveEventArg.from(id, position);
            return this;
        }
    }
}