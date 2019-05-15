package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void PositionIsProperlyInitialized() {
        var pos = Position.locatedAt(1,2);
        assertArrayEquals( new int[]{1,2},new int[]{pos.getX(), pos.getY()});

    }


}
