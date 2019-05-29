package com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser;

public class ConfigurationFormatException extends Exception {

    public ConfigurationFormatException(String message, Throwable cause){
        super(message,cause);
    }

    public ConfigurationFormatException(String message){
        super(message);
    }
}
