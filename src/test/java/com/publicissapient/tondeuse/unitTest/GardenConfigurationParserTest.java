package com.publicissapient.tondeuse.unitTest;

        import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
        import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.GardenConfigurationParser;
        import net.jqwik.api.ForAll;
        import net.jqwik.api.Property;
        import net.jqwik.api.constraints.IntRange;

        import static org.junit.jupiter.api.Assertions.*;

class GardenConfigurationParserTest {

    @Property(tries=30)
    void parsingRandomStringThrowsConfigurationFormatException(@ForAll final String line) {
        assertThrows(ConfigurationFormatException.class,
                ()-> new GardenConfigurationParser().parse(line));
    }

    @Property(tries=30)
    void parsingValidFormatDoesNotThrow(@ForAll @IntRange() final int x,
               @ForAll @IntRange()  final int y)  {

        String line = x + " "+y;
        assertDoesNotThrow(()->{
            var conf = new GardenConfigurationParser().parse(line);
            assertEquals(x,conf.getUpperRight().getX(), "x should be the same");
            assertEquals(y,conf.getUpperRight().getY(), "y should be the same");
        });
    }
}