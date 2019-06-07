package com.publicissapient.tondeuse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MownerWebControllerTest {

    @Test
    void mMownerWebControllerCanRunWithoutThrowing() {
        assertDoesNotThrow(()->new MownerWebController().run());
    }
}