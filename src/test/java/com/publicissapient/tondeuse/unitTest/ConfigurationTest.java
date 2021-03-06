package com.publicissapient.tondeuse.unitTest;


import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ConfigurationTest {

    private static final String FILE_CONF = "file.txt";
    private static final String DUMMY_FILE = "azxazertyu";


    @Test
    void loadConfigurationFromResource() throws ConfigurationException {

        ConfigurationProvider source = FileConfigurationProvider.fromFileResource(FILE_CONF);

        var conf = source.getMowerConfiguration();

        log.info(conf.toString());

        assertEquals(2, conf.size(), "There should be 2 mowers.");
    }

    @Test
    void loadConfigurationFromWrongResource()  {

        assertThrows(ConfigurationException.class, () -> {

            ConfigurationProvider source = FileConfigurationProvider.fromFileResource(DUMMY_FILE);
            var conf = source.getMowerConfiguration();

            log.info(conf.toString());

            assertEquals(2, conf.size(), "There should be 2 mowers.");
        });
    }



}
