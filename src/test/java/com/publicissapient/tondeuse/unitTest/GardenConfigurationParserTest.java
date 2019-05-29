package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.GardenConfigurationParser;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GardenConfigurationParserTest {

    @Property(tries=100)
    void parse(@ForAll final String line) {
        assertThrows(ConfigurationFormatException.class,
                ()-> new GardenConfigurationParser().parse(line));
    }

    @Property(tries=100)
    void parse(@ForAll @IntRange(min = 0,  max = Integer.MAX_VALUE) final int x,
               @ForAll @IntRange(min = 0,  max = Integer.MAX_VALUE)  final int y) throws ConfigurationFormatException {
        String line = x + " "+y;

        var conf = new GardenConfigurationParser().parse(line);
        assertEquals(x,conf.getUpperRight().getX(), "x should be the same");
        assertEquals(y,conf.getUpperRight().getY(), "y should be the same");
    }
}