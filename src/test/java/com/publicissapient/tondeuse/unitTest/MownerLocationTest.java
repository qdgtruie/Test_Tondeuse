package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;


import static org.junit.jupiter.api.Assertions.*;



class MownerLocationTest {

    @Property(tries=100)
    void mownerLocationEqualsIsProperlyworking(@ForAll int x, @ForAll int y) {

        var location = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);

        var result = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);

        assertEquals( result, location, "Position should be equalds");

    }

    @Property(tries=100)
    void mownerLocationHashIsSpecific(@ForAll int x, @ForAll int y){

        var location = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);
        var result = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);

        assertNotEquals( result.hashCode(), location.hashCode(), "hashCode should be equalds");


    }

}
