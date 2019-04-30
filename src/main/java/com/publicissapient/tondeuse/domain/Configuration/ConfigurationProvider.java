package com.publicissapient.tondeuse.domain.Configuration;

import java.io.IOException;
import java.util.Queue;

public interface ConfigurationProvider {

    GardenConfiguration getGardenConfiguration() throws ConfigurationException;
    Queue<MownerConfiguration> getMownerConfiguration()throws ConfigurationException;
}
