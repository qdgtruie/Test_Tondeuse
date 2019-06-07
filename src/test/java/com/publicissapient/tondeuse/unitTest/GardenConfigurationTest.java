package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Position;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import net.jqwik.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GardenConfigurationTest {

    @Property
    void InvalidMoveAttemptAreDetected(@ForAll("getGarden") GardenConfiguration garden) {
        var position = Position.locatedAt(garden.getUpperRight().getX()+1,garden.getUpperRight().getY());
        assertFalse(garden.isValideMove(UUID.randomUUID(),position),"move should be invalid");
    }

    @Property
    void validMoveAttemptPass(@ForAll("getGarden") GardenConfiguration garden) {
        var position = Position.locatedAt(garden.getUpperRight().getX()-1,garden.getUpperRight().getY());
        assertTrue(garden.isValideMove(UUID.randomUUID(),position),"move should be valid");
    }


    @Property
    void canGetConsummers(@ForAll("getGarden") GardenConfiguration garden) {

        assertEquals(0,garden.getConsummers().size(),"There should be 0 listener");
        garden.addFenceViolationListener(evt ->{});
        assertEquals(1,garden.getConsummers().size(),"There should be 1 listener");
        garden.addFenceViolationListener(evt ->{});
        assertEquals(2,garden.getConsummers().size(),"There should be 2 listener");
    }

    @Provide
    Arbitrary<GardenConfiguration> getGarden() {
        var xArbitrary = Arbitraries.integers().between(1, Integer.MAX_VALUE-1);
        var yArbitrary = Arbitraries.integers().between(1, Integer.MAX_VALUE-1);
        return Combinators.combine(xArbitrary,yArbitrary).as((x,y)->GardenConfiguration.endsAt(Position.locatedAt(x, y)));
        //Arbitraries.combine() of(GardenConfiguration.endsAt(Position.locatedAt(x, y)));
    }

    @Property
    void canGetGardenUpperRightPosition(@ForAll  int x, @ForAll  int y) {
        var garden = GardenConfiguration.endsAt(Position.locatedAt(x, y));
        var position = Position.locatedAt(x,y);
        assertEquals(position, garden.getUpperRight(),"position should be the same");
    }

    @Property
    void twoGardenInstanceAreNotEquals(@ForAll("getGarden") GardenConfiguration garden1,@ForAll("getGarden") GardenConfiguration garden2) {
        assertNotEquals(null, garden1, "Two different object should not be equals.");
        assertFalse(garden1.equals(garden2) && garden2.equals(garden1), "Two instance should not be equals.");
    }

    @Property
    void twoGardenInstanceHaveDifferentHashCode(@ForAll("getGarden") GardenConfiguration garden1,@ForAll("getGarden") GardenConfiguration garden2) {
        assertNotEquals(garden1.hashCode() , garden2.hashCode(), "Two instance should not have same hash.");
    }

    @Property
    void noMatterTheGardenStringRepresentationIsAlwaysPossible(@ForAll("getGarden") GardenConfiguration garden) {
        assertDoesNotThrow(garden::toString,
                "No matter the garden, string representation should always be pssible");
    }


}