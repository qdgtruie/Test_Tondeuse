package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void positionIsProperlyInitialized() {
        var pos = Position.locatedAt(1,2);
        assertArrayEquals( new int[]{1,2},new int[]{pos.getX(), pos.getY()});

    }


}
