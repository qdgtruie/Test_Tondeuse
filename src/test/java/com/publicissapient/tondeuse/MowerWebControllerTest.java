package com.publicissapient.tondeuse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MowerWebControllerTest {

    @Test
    void mowerWebControllerCanRunWithoutThrowing() {
        assertDoesNotThrow(()->new MowerWebController().run());
    }


}