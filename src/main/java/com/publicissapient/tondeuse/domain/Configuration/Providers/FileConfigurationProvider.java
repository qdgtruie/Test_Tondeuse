package com.publicissapient.tondeuse.domain.Configuration.Providers;

import com.publicissapient.tondeuse.domain.Configuration.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.util.List;
import java.util.Queue;


import com.publicissapient.tondeuse.domain.Configuration.Providers.StringConfigurationParser.ConfigurationFormatException;
import com.publicissapient.tondeuse.domain.Configuration.Providers.StringConfigurationParser.GardenConfigurationParser;
import com.publicissapient.tondeuse.domain.Configuration.Providers.StringConfigurationParser.MownerConfigurationPaser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileConfigurationProvider implements ConfigurationProvider {


    private final String filePath;
    private List<String> lines;

    public FileConfigurationProvider(final String aFilePath) {

        filePath = aFilePath;
    }

    private List<String> getLines() throws ConfigurationException {
        if (lines == null)
            try {
                lines = Files.readAllLines(java.nio.file.Path.of(this.filePath), Charset.defaultCharset());
            } catch (IOException e) {
                String message = "Error while reading configuration file";
                log.error(message, e);
                throw new ConfigurationException(message, e);
            }
        return lines;
    }

    @Override
    public GardenConfiguration getGardenConfiguration() throws ConfigurationException
    {
        try {
            return new GardenConfigurationParser().Parse(getLines().get(0));
        }catch(ConfigurationFormatException e) {
            String message = "Error while parsing garden section of configuration file";
            log.error(message, e);
            throw new ConfigurationException(message, e);
        }


    }

    /**
     *
     * @return
     * @throws ConfigurationException
     */
    @Override
    public Queue<MownerConfiguration> getMownerConfiguration() throws ConfigurationException {

        try {
            return new MownerConfigurationPaser().Parse(getLines().subList(1, getLines().size()));

        } catch (ConfigurationFormatException e) {
            String message = "Error while parsing Mowner section of configuration file";
            log.error(message, e);
            throw new ConfigurationException(message, e);
        }

    }



}
