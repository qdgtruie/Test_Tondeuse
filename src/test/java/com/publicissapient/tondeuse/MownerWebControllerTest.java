package com.publicissapient.tondeuse;

import net.jqwik.api.Property;
import net.jqwik.api.Report;
import net.jqwik.api.Reporting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MownerWebControllerTest {

    @Test
    void mMownerWebControllerCanRunWithoutThrowing() {
        assertDoesNotThrow(()->new MownerWebController().run());
    }
}