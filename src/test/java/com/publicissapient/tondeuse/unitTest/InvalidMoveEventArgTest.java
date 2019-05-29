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

        assertEquals(tuple.getId(),instance.getMownerID(),"ID should be similar");
        assertEquals(tuple.getPosition(),instance.getTargetPosition(),"Positon should be similar");

    }

    @Test
    void setMownerID() {
        Tuple tuple = new Tuple().invoke();

        var id = UUID.randomUUID();
        tuple.instance.setMownerID(id);
        assertEquals(id,tuple.instance.getMownerID(),"ID should be similar");

    }

    @Test
    void setTargetPosition() {

        Tuple tuple = new Tuple().invoke();
        var target = Position.locatedAt(5,5);
        tuple.instance.setTargetPosition(target);
        assertEquals(target,tuple.instance.getTargetPosition(),"Positon should be similar");

    }

    @Test
    void getMownerID() {
        Tuple tuple = new Tuple().invoke();
        assertEquals(tuple.id,tuple.instance.getMownerID(),"ID should be similar");
    }

    @Test
    void getTargetPosition() {
        Tuple tuple = new Tuple().invoke();
        assertEquals(tuple.getPosition(),tuple.instance.getTargetPosition(),"Positon should be similar");

    }

    private class Tuple {
        private UUID id;
        private Position position;
        private InvalidMoveEventArg instance;

        public UUID getId() {
            return id;
        }

        public Position getPosition() {
            return position;
        }

        public InvalidMoveEventArg getInstance() {
            return instance;
        }

        public Tuple invoke() {
            id = UUID.randomUUID();
            position = Position.locatedAt(0, 0);
            instance = InvalidMoveEventArg.from(id, position);
            return this;
        }
    }
}