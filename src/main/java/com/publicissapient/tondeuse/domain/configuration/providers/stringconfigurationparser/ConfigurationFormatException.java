package com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser;

public class ConfigurationFormatException extends Exception {

    ConfigurationFormatException(String message, Throwable cause){
        super(message,cause);
    }

    ConfigurationFormatException(String message){
        super(message);
    }
}
