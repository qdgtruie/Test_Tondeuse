package com.publicissapient.tondeuse.domain.Configuration;

import com.publicissapient.tondeuse.domain.Configuration.Providers.StringConfigurationParser.GardenConfigurationParser;
import com.publicissapient.tondeuse.domain.Configuration.Providers.StringConfigurationParser.MownerConfigurationPaser;
import com.publicissapient.tondeuse.service.ConfigurationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Queue;

@Slf4j
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE,staticName = "populateWith")
public class Configuration {

    @Getter
    private GardenConfiguration garden;

    @Getter
    private Queue<MownerConfiguration> mowners;


    public static Configuration with(ConfigurationProvider provider) throws ConfigurationException {

       return Configuration.populateWith(provider.getGardenConfiguration(), provider.getMownerConfiguration());
    }


}
