package com.publicissapient.tondeuse.domain.configuration.providers.StringConfigurationParser;

import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.Position;

public class GardenConfigurationParser {

    public GardenConfiguration Parse(String headLine) throws ConfigurationFormatException {

        String SEPARATOR = " ";
        GardenConfiguration result = GardenConfiguration.endsAt(Position.locatedAt(0,0));
        if(CheckFormat(headLine)){
            java.util.List<String> tokens = com.google.common.base.Splitter.on(SEPARATOR).splitToList(headLine);
            int x =Integer.valueOf(tokens.get(0));
            int y = Integer.valueOf(tokens.get(1));
            result = GardenConfiguration.endsAt(Position.locatedAt(x, y));
        }

        return result;
    }

    private boolean CheckFormat(String headLine) throws ConfigurationFormatException {
        throw new ConfigurationFormatException("not implemented", new Exception());
    }
}
