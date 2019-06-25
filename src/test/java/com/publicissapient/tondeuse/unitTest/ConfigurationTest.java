package com.publicissapient.tondeuse.unitTest;


import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ConfigurationTest {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileConfigurationProvider.class);

    private static final String FILE_CONF = "file.txt";
    private static final String DUMMY_FILE = "azxazertyu";


    @Test
    void loadConfigurationFromResource() throws ConfigurationException {

        ConfigurationProvider source = FileConfigurationProvider.fromFileResource(FILE_CONF);

        var conf = source.getMowerConfiguration();

        logger.info(conf.toString());

        assertEquals(2, conf.size(), "There should be 2 mowers.");
    }

    @Test
    void loadConfigurationFromWrongResource()  {

        assertThrows(ConfigurationException.class, () -> {

            ConfigurationProvider source = FileConfigurationProvider.fromFileResource(DUMMY_FILE);
            var conf = source.getMowerConfiguration();

            logger.info(conf.toString());

            assertEquals(2, conf.size(), "There should be 2 mowers.");
        });
    }



}
