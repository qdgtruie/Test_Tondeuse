package com.publicissapient.tondeuse.domain.configuration.providers;

import com.publicissapient.tondeuse.domain.configuration.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import java.util.List;
import java.util.Queue;


import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.GardenConfigurationParser;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.MownerConfigurationPaser;

import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileConfigurationProvider implements ConfigurationProvider {


    private final String filePath;

    private List<String> lines;

    private FileConfigurationProvider(@NonNull final String aFilePath) {

        filePath = aFilePath;
    }

    public static FileConfigurationProvider fromFile(@NonNull final String aFilePath){
        return new FileConfigurationProvider(aFilePath);
    }

    public static FileConfigurationProvider fromRessource(@NonNull final String aRessourceName)throws ConfigurationException {
        var filePath = "";

        try {
            //Option.of()
            ClassLoader classLoader = FileConfigurationProvider.class.getClassLoader();
            filePath = classLoader.getResource(aRessourceName).getFile().replace("%20", " ");
        }catch (NullPointerException e){
            String message = "Error while reading configuration file";
            log.error(message, e);
            throw new ConfigurationException(message, e);
        }

        return new FileConfigurationProvider(filePath);
    }

    private List<String> getLines() throws ConfigurationException {
        if (lines == null)
            try {
                lines = Files.readAllLines(java.nio.file.Path.of(filePath), Charset.defaultCharset());
            } catch (IOException e){
                String message = "Error while reading configuration file";
                log.error(message, e);
                throw new ConfigurationException(message, e);
            } catch(NullPointerException ex){
                String message = "Error accessing resources stream for configuration.";
                log.error(message, ex);
                throw new ConfigurationException(message, ex);
            }
        return lines;
    }

    @Override
    public GardenConfiguration getGardenConfiguration() throws ConfigurationException
    {
        try {
            return new GardenConfigurationParser().parse(getLines().get(0));
        }catch(ConfigurationFormatException e) {
            String message = "Error while parsing garden section of configuration file";
            log.error(message, e);
            throw new ConfigurationException(message, e);
        }


    }


    @Override
    public Queue<MownerConfiguration> getMownerConfiguration() throws ConfigurationException {

        try {
            return new MownerConfigurationPaser().parse(getLines().subList(1, getLines().size()));

        } catch (ConfigurationFormatException e) {
            String message = "Error while parsing Mowner section of configuration file";
            log.error(message, e);
            throw new ConfigurationException(message, e);
        }

    }



}
