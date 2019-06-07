package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationExceptionTest {

    @Test
    void ExceptionCanContainMessage() {

        final String MESSAGE = "MESSAGE";
        var e = new ConfigurationException(MESSAGE);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
    }

    @Test
    void ExceptionCanContainInnerException() {

        final String MESSAGE = "MESSAGE";
        final String INNER = "INNER";

        var ex = new Exception(INNER);
        var e = new ConfigurationException(MESSAGE, ex);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
        assertEquals(ex.getMessage(), e.getCause().getMessage(), "Inner Exceptions message should be equals.");
        assertEquals(ex, e.getCause(), "Exceptions should be equals.");

    }

    @Test
    void ExceptionIsThrowable() {

        final String MESSAGE = "MESSAGE";
        var e = new ConfigurationException(MESSAGE);

        assertTrue(e instanceof Throwable, "IllegalMownerInstruction should be an exception");
    }
}