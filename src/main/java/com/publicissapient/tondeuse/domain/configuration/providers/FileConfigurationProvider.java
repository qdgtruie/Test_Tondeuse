package com.publicissapient.tondeuse.domain.configuration.providers;

import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MowerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.GardenConfigurationParser;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.MowerConfigurationParser;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Queue;

@Slf4j
public class FileConfigurationProvider implements ConfigurationProvider {


    private final String resourcePath;

    private List<String> lines;

    private FileConfigurationProvider(@NonNull final String aResourcePath) {

        resourcePath = aResourcePath;
    }

    public static FileConfigurationProvider fromFileResource(@NonNull final String aResourcePath){
        return new FileConfigurationProvider(aResourcePath);
    }

    /**
     * Perform actual parsing and wrap exception into business exception
     * @return  a list of lines
     * @throws ConfigurationException with proper message
     */
    private List<String> getLines() throws ConfigurationException {
        if (lines == null)
            try (var fileStream = getFileStream(resourcePath)){

                //IOUtils do not close the input stream, need to be done at a higher level (hence the try with resource)
                String all = IOUtils.toString(fileStream,Charset.defaultCharset());
                lines = List.of(all.split(IOUtils.LINE_SEPARATOR));

            } catch (IllegalArgumentException e){
                String message = "Provided resourcePath is not a file name";
                log.error(message, e);
                throw new ConfigurationException(message, e);
            }  catch (IOException e){
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


    private static InputStream getFileStream(@NonNull final String aResourceName) throws FileNotFoundException {
        File file = new File(aResourceName);
        if(file.exists())
            return new FileInputStream(file);
        else
            return FileConfigurationProvider.class.getClassLoader().getResourceAsStream(aResourceName);

    }



    @Override
    public GardenConfiguration getGardenConfiguration() throws ConfigurationException
    {
        try {
            return new GardenConfigurationParser().parse(getLines().get(0));

        }catch(ConfigurationFormatException e) {
            String message = "Error while parsing garden section of configuration file";
            if(log.isErrorEnabled())
                log.error(message, e);
            throw new ConfigurationException(message, e);
        }


    }


    @Override
    public Queue<MowerConfiguration> getMowerConfiguration() throws ConfigurationException {

        try {
            return new MowerConfigurationParser().parse(getLines().subList(1, getLines().size()));

        } catch (ConfigurationFormatException e) {
            String message = "Error while parsing Mower section of configuration file";
            if(log.isErrorEnabled())
                log.error(message, e);
            throw new ConfigurationException(message, e);
        }

    }



}
