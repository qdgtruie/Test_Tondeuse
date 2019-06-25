package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import static org.junit.jupiter.api.Assertions.*;

class FileConfigurationProviderTest {

    private FileConfigurationProvider readConfigurationFromFileResource(final String path) {
        return FileConfigurationProvider.fromFileResource(path);
    }

    @Property(tries = 100)
    void getGardenConfiguration(@ForAll @StringLength(min=5)final String path) {
        assertThrows(ConfigurationException.class, ()-> readConfigurationFromFileResource(path).getGardenConfiguration()
                ,"Was expecting ConfigurationException");
    }

    @Property(tries = 100)
    void getMowerConfiguration(@ForAll @StringLength(min=5) final String path) {
        assertThrows(ConfigurationException.class, ()-> readConfigurationFromFileResource(path).getMowerConfiguration()
                ,"Was expecting ConfigurationException");
    }
}