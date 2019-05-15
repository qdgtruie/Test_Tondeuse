package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Mowner;
import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class MownerTest {


    private Mowner BuildMowner(int x, int y, Orientation orientation) {
        UUID id = UUID.randomUUID();

        return Mowner.initialLocation(id, MownerLocation.with(Position.locatedAt(x, y), Orientation.N));
    }

    @Test
    public void MownerCanTurnLeft() {

        Mowner mowner = BuildMowner(1, 1, Orientation.N);
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
    public void MownerCanTurnRight() {

        Mowner mowner = BuildMowner(1, 1, Orientation.N);
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
    public void MownerCanMoveForward() {
        Mowner mowner = BuildMowner(1, 1, Orientation.N);

        mowner.addOffBoundChecker(position-> position.getX() <= 5 && position.getY() <= 5);
        mowner.moveForward();

        var result = MownerLocation.with(Position.locatedAt(1,2), Orientation.N);

        assertEquals( result, mowner.getCurrentLocation(), "Y should be '2' ");
    }


}
