package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Mower;
import com.publicissapient.tondeuse.domain.MowerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class MowerTest {


    private Mower buildMower(int x, int y, Orientation orientation) {
        UUID id = UUID.randomUUID();

        return Mower.initialLocation(id, MowerLocation.with(Position.locatedAt(x, y), orientation));
    }

    @Test
    void mowerCanTurnLeft() {

        Mower mower = buildMower(1, 1, Orientation.N);
        mower.turnLeft();
        assertEquals( Orientation.W, mower.getCurrentLocation().getOrientation(), "Orientation should be 'W' ");
        mower.turnLeft();
        assertEquals( Orientation.S, mower.getCurrentLocation().getOrientation(),"Orientation should be 'S' ");
        mower.turnLeft();
        assertEquals( Orientation.E, mower.getCurrentLocation().getOrientation(), "Orientation should be 'E' ");
        mower.turnLeft();
        assertEquals( Orientation.N, mower.getCurrentLocation().getOrientation(), "Orientation should be 'N' ");

    }

    @Test
    void mowerCanTurnRight() {

        Mower mower = buildMower(1, 1, Orientation.N);
        mower.turnRight();
        assertEquals( Orientation.E, mower.getCurrentLocation().getOrientation(),"Orientation should be 'E' ");
        mower.turnRight();
        assertEquals( Orientation.S, mower.getCurrentLocation().getOrientation(),"Orientation should be 'S' ");
        mower.turnRight();
        assertEquals( Orientation.W, mower.getCurrentLocation().getOrientation(), "Orientation should be 'W' ");
        mower.turnRight();
        assertEquals( Orientation.N, mower.getCurrentLocation().getOrientation(),"Orientation should be 'N' ");

    }

    @Test
    void mowerCanMoveForward() {
        Mower mower = buildMower(1, 1, Orientation.N);

        mower.addPositionChecker(position-> position.getX() <= 5 && position.getY() <= 5);
        mower.moveForward();

        var result = MowerLocation.with(Position.locatedAt(1,2), Orientation.N);

        assertEquals( result, mower.getCurrentLocation(), "Y should be '2' ");
    }

    @Test
    void mowerCanNotStepOnEachOtherToes() {

        Mower mower1 = buildMower(0, 0, Orientation.N);
        Mower mower2 = buildMower(1, 0, Orientation.W);

        mower1.addPositionChecker(position->mower2.checkCollision(mower1.getId(),position));
        mower2.addPositionChecker(position->mower1.checkCollision(mower2.getId(),position));

        mower2.moveForward();
        var mower2Result = MowerLocation.with(Position.locatedAt(1,0), Orientation.W);
        assertEquals( mower2Result, mower2.getCurrentLocation(),
                "Mower2 should not have moved : position should still be [1,1] ");


    }

    private boolean detected = false;

    @Test
    void mowerRaiseNotificationWhenCollisionIsDetected() {

        Mower mower1 = buildMower(0, 0, Orientation.N);
        Mower mower2 = buildMower(1, 0, Orientation.W);

        mower1.addPositionChecker(position->mower2.checkCollision(mower1.getId(),position));
        mower2.addPositionChecker(position->mower1.checkCollision(mower2.getId(),position));

        mower1.addCollisionListener(x->detected=true);
        mower2.addCollisionListener(x->detected=true);

        mower2.moveForward();

        assertTrue( detected, "Mower2 should have detected collision ");


    }
}
