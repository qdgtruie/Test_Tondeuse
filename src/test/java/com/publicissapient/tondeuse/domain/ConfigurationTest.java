package com.publicissapient.tondeuse.domain;


import com.publicissapient.tondeuse.domain.Configuration.ConfigurationException;
import com.publicissapient.tondeuse.domain.Configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.Configuration.Providers.FileConfigurationProvider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigurationTest {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileConfigurationProvider.class);

    private static final String FILE_CONF = "file.txt";

    @Test
    public void loadConfigurationFromFile() throws ConfigurationException {
        ClassLoader classLoader = getClass().getClassLoader();
        var file = classLoader.getResource(FILE_CONF).getFile().replace("%20"," ");

        ConfigurationProvider source = new FileConfigurationProvider(file);

        var conf =  source.getMownerConfiguration();

        logger.info(conf.toString());

        assertEquals("There should be 2 mowners.",conf.size(),2);
    }


}
