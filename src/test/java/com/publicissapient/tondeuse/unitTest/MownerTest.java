package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Mowner;
import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class MownerTest {


    private Mowner buildMowner(int x, int y, Orientation orientation) {
        UUID id = UUID.randomUUID();

        return Mowner.initialLocation(id, MownerLocation.with(Position.locatedAt(x, y), orientation));
    }

    @Test
    void mownerCanTurnLeft() {

        Mowner mowner = buildMowner(1, 1, Orientation.N);
        mowner.turnLeft();
        assertEquals( Orientation.W, mowner.getCurrentLocation().getOrientation(), "Orientation should be 'W' ");
        mowner.turnLeft();
        assertEquals( Orientation.S, mowner.getCurrentLocation().getOrientation(),"Orientation should be 'S' ");
        mowner.turnLeft();
        assertEquals( Orientation.E, mowner.getCurrentLocation().getOrientation(), "Orientation should be 'E' ");
        mowner.turnLeft();
        assertEquals( Orientation.N, mowner.getCurrentLocation().getOrientation(), "Orientation should be 'N' ");

    }

    @Test
    void mownerCanTurnRight() {

        Mowner mowner = buildMowner(1, 1, Orientation.N);
        mowner.turnRight();
        assertEquals( Orientation.E, mowner.getCurrentLocation().getOrientation(),"Orientation should be 'E' ");
        mowner.turnRight();
        assertEquals( Orientation.S, mowner.getCurrentLocation().getOrientation(),"Orientation should be 'S' ");
        mowner.turnRight();
        assertEquals( Orientation.W, mowner.getCurrentLocation().getOrientation(), "Orientation should be 'W' ");
        mowner.turnRight();
        assertEquals( Orientation.N, mowner.getCurrentLocation().getOrientation(),"Orientation should be 'N' ");

    }

    @Test
    void mownerCanMoveForward() {
        Mowner mowner = buildMowner(1, 1, Orientation.N);

        mowner.addPositionChecker(position-> position.getX() <= 5 && position.getY() <= 5);
        mowner.moveForward();

        var result = MownerLocation.with(Position.locatedAt(1,2), Orientation.N);

        assertEquals( result, mowner.getCurrentLocation(), "Y should be '2' ");
    }

    @Test
    void mownerCanNotStepOnEachOtherToes() {

        Mowner mowner1 = buildMowner(0, 0, Orientation.N);
        Mowner mowner2 = buildMowner(1, 0, Orientation.W);

        mowner1.addPositionChecker(position->mowner2.checkcollision(mowner1.getId(),position));
        mowner2.addPositionChecker(position->mowner1.checkcollision(mowner2.getId(),position));

        mowner2.moveForward();
        var mowner2Result = MownerLocation.with(Position.locatedAt(1,0), Orientation.W);
        assertEquals( mowner2Result, mowner2.getCurrentLocation(),
                "Mowner2 should not have moved : position should still be [1,1] ");


    }

    boolean detected = false;

    @Test
    void mownerRaiseNotificationwhenCollisionIsDetected() {

        Mowner mowner1 = buildMowner(0, 0, Orientation.N);
        Mowner mowner2 = buildMowner(1, 0, Orientation.W);

        mowner1.addPositionChecker(position->mowner2.checkcollision(mowner1.getId(),position));
        mowner2.addPositionChecker(position->mowner1.checkcollision(mowner2.getId(),position));

        mowner1.addCollisionListener(x->detected=true);
        mowner2.addCollisionListener(x->detected=true);

        mowner2.moveForward();

        assertTrue( detected, "Mowner2 should have detected collision ");


    }
}
