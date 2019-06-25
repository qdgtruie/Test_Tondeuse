package com.publicissapient.tondeuse.domain.configuration;


import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;

/**
 * Reificate the configuration for the MownerController to load
 */
@Slf4j
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE,staticName = "populateWith")
public class Configuration {

    /***
     * Hold the garden initial size configuration
     */
    @Getter
    private GardenConfiguration garden;

    /**
     * Hold the configuration for each Mowner
     */
    @Getter
    private Queue<MowerConfiguration> mowners;


    /**
     * Helper method to intiatiate a Configuration from a ConfigurationProvider
     * @param provider implementation for lhe Configuration provider
     * @return a Configuration instance for the MownerController to execute against
     * @throws ConfigurationException when configuration could not be rehydrated
     */
    public static Configuration basedOn(ConfigurationProvider provider) throws ConfigurationException {

       return Configuration.populateWith(provider.getGardenConfiguration(), provider.getMownerConfiguration());
    }


}
