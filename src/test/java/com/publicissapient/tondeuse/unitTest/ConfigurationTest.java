package com.publicissapient.tondeuse.unitTest;


import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileConfigurationProvider.class);

    private static final String FILE_CONF = "file.txt";
    private static final String DUMMY_FILE = "azxazertyu";


    @Test
    public void loadConfigurationFromRessouce() throws ConfigurationException {

        ConfigurationProvider source = FileConfigurationProvider.fromFileResource(FILE_CONF);

        var conf = source.getMownerConfiguration();

        logger.info(conf.toString());

        assertEquals(2, conf.size(), "There should be 2 mowners.");
    }

    @Test
    public void loadConfigurationFromWrongRessouce()  {

        assertThrows(ConfigurationException.class, () -> {

            ConfigurationProvider source = FileConfigurationProvider.fromFileResource(DUMMY_FILE);
            var conf = source.getMownerConfiguration();

            logger.info(conf.toString());

            assertEquals(2, conf.size(), "There should be 2 mowners.");
        });
    }



}
