package com.publicissapient.tondeuse.domain;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class PositionTest {

    @Test
    public void testAppHasAGreeting() {
        var pos = Position.locatedAt(1,2);
        assertArrayEquals( new int[]{1,2},new int[]{pos.getX(), pos.getY()});
    }
}
