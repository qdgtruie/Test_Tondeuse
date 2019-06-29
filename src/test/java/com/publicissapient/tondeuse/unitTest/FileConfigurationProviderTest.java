package com.publicissapient.tondeuse.unitTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

@Slf4j
class FileConfigurationProviderTest implements AutoCloseable {


    public  FileConfigurationProviderTest()  {
        var logger = (Logger) getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ALL);
    }

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

    @Property
    void canNotCreateProviderFromNullResource() {

        assertThrows(NullPointerException.class, ()->  log.info(readConfigurationFromFileResource(null).toString())
                ,"Was expecting NullPointerException");


    }

    @Property
    void ioExceptionsAreWrappedInConfigurationException() {

        final String ioErrorPath = "ioErrorFile";

        //generate IOException
        try(final RandomAccessFile accessFile = new RandomAccessFile(ioErrorPath, "rw")){
            accessFile.getChannel().lock();
        } catch (IOException e) {
        //bury exception
        }

        assertThrows(ConfigurationException.class, ()-> readConfigurationFromFileResource(ioErrorPath).getGardenConfiguration()
                ,"Was expecting ConfigurationException");


    }

    @Override
    public void close() {
        var logger = (Logger) getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.ERROR);
    }

}