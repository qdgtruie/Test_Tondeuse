package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;



public class MownerLocationTest {

    @Property(tries=1000)
    public void MownerLocationEqualsIsProperlyworking(@ForAll int x,@ForAll int y) {

        var location = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);

        var result = MownerLocation.with(Position.locatedAt(x,y), Orientation.N);

        assertEquals( result, location, "Position should be equalds");

    }

}
