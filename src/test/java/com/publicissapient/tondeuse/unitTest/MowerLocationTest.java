package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.MowerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;


import static org.junit.jupiter.api.Assertions.*;



class MowerLocationTest {

    @Property(tries=20)
    void mowerLocationEqualsIsProperlyWorking(@ForAll int x, @ForAll int y) {

        var location = MowerLocation.with(Position.locatedAt(x,y), Orientation.N);

        var result = MowerLocation.with(Position.locatedAt(x,y), Orientation.N);

        assertEquals( result, location, "Position should be equals");

    }

    @Property(tries=20)
    void mowerLocationHashIsSpecific(@ForAll int x, @ForAll int y){

        var location = MowerLocation.with(Position.locatedAt(x,y), Orientation.N);
        var result = MowerLocation.with(Position.locatedAt(x,y), Orientation.N);

        assertNotEquals( result.hashCode(), location.hashCode(), "hashCode should be equals");


    }

}
