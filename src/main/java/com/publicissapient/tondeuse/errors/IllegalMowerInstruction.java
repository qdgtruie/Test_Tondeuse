package com.publicissapient.tondeuse.errors;

/**
 * Exception raised when a mower is provided with an invalid instruction
 */
public class IllegalMowerInstruction extends IllegalStateException{

    /**
     * Instanciate a IllegalMowerInstruction with a specific message
     * @param message a contextual message
     */
    public IllegalMowerInstruction(String message){
        super(message);
    }

    /**
     * Instanciate a IllegalMowerInstruction with a specific message and innerException
     * @param message a contextual message
     * @param ex an inner exception
     */
    public IllegalMowerInstruction(String message, Exception ex) {
        super(message,ex);
    }
}
