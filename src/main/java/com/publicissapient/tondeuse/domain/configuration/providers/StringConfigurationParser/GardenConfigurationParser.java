package com.publicissapient.tondeuse.domain.configuration.providers.StringConfigurationParser;

import com.google.common.base.Splitter;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.Position;

import java.util.regex.Pattern;

/**
 * Parser to instanciate a GardenConfiguration
 */
public class GardenConfigurationParser {


    /**
     * Instanciate a GardenConfiguration based on provided string line.
     * @param headLine string used to instanciate a GardenConfiguration
     * @return a GardenConfiguration instance
     * @throws ConfigurationFormatException when string is not properly formatted
     */
    public GardenConfiguration Parse(final String headLine) throws ConfigurationFormatException {

        final String SEPARATOR = " ";

        GardenConfiguration result = GardenConfiguration.endsAt(Position.locatedAt(0,0));
        if(CheckFormat(headLine)){
            java.util.List<String> tokens = Splitter.on(SEPARATOR).splitToList(headLine);
            int x =Integer.valueOf(tokens.get(0));
            int y = Integer.valueOf(tokens.get(1));
            result = GardenConfiguration.endsAt(Position.locatedAt(x, y));
        }

        return result;
    }

    /**
     * Check the fomat on the string provided to the parser
     * @param headLine string provided
     * @return true if valid.
     * @throws ConfigurationFormatException when incorrect
     */
    private boolean CheckFormat(final String headLine) throws ConfigurationFormatException {

        Pattern p1 = GetPattern();
        if(! p1.matcher(headLine).matches())
            throw new ConfigurationFormatException("Invalid Configuration format for garden : '"+headLine+"'");

        return true;
    }

    private Pattern pattern;

    private Pattern GetPattern() {
        if(pattern==null)
            pattern = Pattern.compile("\\d+ \\d+");

        return pattern;
    }
}
