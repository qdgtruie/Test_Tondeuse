package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationExceptionTest {

    @Test
    void exceptionCanContainMessage() {

        final String MESSAGE = "MESSAGE";
        var e = new ConfigurationException(MESSAGE);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
    }

    @Test
    void exceptionCanContainInnerException() {

        final String MESSAGE = "MESSAGE";
        final String INNER = "INNER";

        var ex = new Exception(INNER);
        var e = new ConfigurationException(MESSAGE, ex);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
        assertEquals(ex.getMessage(), e.getCause().getMessage(), "Inner Exceptions message should be equals.");
        assertEquals(ex, e.getCause(), "Exceptions should be equals.");

    }

    @Test
    void exceptionIsThrowable() {

        final String MESSAGE = "MESSAGE";
        var e = new ConfigurationException(MESSAGE);

        assertTrue(Throwable.class.isAssignableFrom(e.getClass()), "ConfigurationException should be an exception");
    }
}