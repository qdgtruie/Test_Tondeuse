package com.publicissapient.tondeuse.unitTest;


import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ConfigurationTest {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileConfigurationProvider.class);

    private static final String FILE_CONF = "file.txt";
    private static final String DUMMY_FILE = "azxazertyu";

    @Test
    public void loadConfigurationFromFile() throws ConfigurationException {


        ClassLoader classLoader = getClass().getClassLoader();
        var file = classLoader.getResource(FILE_CONF).getFile().replace("%20", " ");

        ConfigurationProvider source = FileConfigurationProvider.fromFile(file);

        var conf = source.getMownerConfiguration();

        logger.info(conf.toString());

        assertEquals(2, conf.size(), "There should be 2 mowners.");
    }

    @Test
    public void loadConfigurationFromRessouce() throws ConfigurationException {

        ConfigurationProvider source = FileConfigurationProvider.fromRessource(FILE_CONF);

        var conf = source.getMownerConfiguration();

        logger.info(conf.toString());

        assertEquals(2, conf.size(), "There should be 2 mowners.");
    }

    @Test
    public void loadConfigurationFromWrongRessouce()  {

        assertThrows(ConfigurationException.class, () -> {

            ConfigurationProvider source = FileConfigurationProvider.fromRessource(DUMMY_FILE);
            var conf = source.getMownerConfiguration();

            logger.info(conf.toString());

            assertEquals(2, conf.size(), "There should be 2 mowners.");
        });
    }

    @Test
    public void loadConfigurationFromWrongFile()  {

        assertThrows(ConfigurationException.class, () -> {

            ConfigurationProvider source = FileConfigurationProvider.fromFile(DUMMY_FILE);
            var conf = source.getMownerConfiguration();


            logger.info(conf.toString());

            assertEquals(2, conf.size(), "There should be 2 mowners.");
        });
    }

}
