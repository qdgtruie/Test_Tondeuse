package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.errors.IllegalMownerInstruction;
import com.publicissapient.tondeuse.service.MownerController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalMownerInstructionTest {

    @Test
    public void ExceptionCanContainMessage() {

        final String MESSAGE = "MESSAGE";
        var e = new IllegalMownerInstruction(MESSAGE);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
    }

    @Test
    public void ExceptionCanContainInnerException() {

        final String MESSAGE = "MESSAGE";
        final String INNER = "INNER";

        var ex = new Exception(INNER);
        var e = new IllegalMownerInstruction(MESSAGE, ex);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
        assertEquals(ex.getMessage(), e.getCause().getMessage(), "Inner Exceptions message should be equals.");
        assertEquals(ex, e.getCause(), "Exceptions should be equals.");

    }

    @Test
    public void ExceptionIsThrowable() {

        final String MESSAGE = "MESSAGE";
        var e = new IllegalMownerInstruction(MESSAGE);

        assertTrue(e instanceof Throwable, "IllegalMownerInstruction should be an exception");
    }
}