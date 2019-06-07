package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationFormatExceptionTest {

    @Property(tries=100)
    void ExceptionCanContainMessage(@ForAll final String MESSAGE) {

        var e = new ConfigurationFormatException(MESSAGE);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
    }

    @Property(tries=100)
    void ExceptionCanContainInnerException(@ForAll final String MESSAGE, @ForAll final String INNER) {

        var ex = new Exception(INNER);
        var e = new ConfigurationFormatException(MESSAGE, ex);

        assertEquals(MESSAGE, e.getMessage(), "Exception message should be equals.");
        assertEquals(ex.getMessage(), e.getCause().getMessage(), "Inner Exceptions message should be equals.");
        assertEquals(ex, e.getCause(), "Exceptions should be equals.");

    }

    @Property(tries=100)
    void ExceptionIsThrowable(@ForAll final String MESSAGE) {

        var e = new ConfigurationFormatException(MESSAGE);

        assertTrue(e instanceof Throwable, "IllegalMownerInstruction should be an exception");
    }

}