package com.publicissapient.tondeuse.domain.configuration;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;

import java.util.Queue;

public interface ConfigurationProvider {

    GardenConfiguration getGardenConfiguration() throws ConfigurationException;
    Queue<MowerConfiguration> getMownerConfiguration()throws ConfigurationException;
}
