package com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser;

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
    public GardenConfiguration parse(final String headLine) throws ConfigurationFormatException {

        final String SEPARATOR = " ";

        GardenConfiguration result = GardenConfiguration.endsAt(Position.locatedAt(0,0));
        if( getPattern().matcher(headLine).matches()){
            java.util.List<String> tokens = Splitter.on(SEPARATOR).splitToList(headLine);
            int x =Integer.parseInt(tokens.get(0));
            int y = Integer.parseInt(tokens.get(1));
            result = GardenConfiguration.endsAt(Position.locatedAt(x, y));
        }else
            throw new ConfigurationFormatException("Invalid Configuration format for garden : '"+headLine+"'");

        return result;
    }


    private Pattern pattern;

    private Pattern getPattern() {
        if(pattern==null)
            pattern = Pattern.compile("\\d+ \\d+");

        return pattern;
    }
}
