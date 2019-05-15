package com.publicissapient.tondeuse.domain.configuration.errors;

public class ConfigurationException extends Exception {

    public ConfigurationException(String message){
        super(message);
    }

    public ConfigurationException(String message, Throwable cause){
        super(message,cause);
    }

}
