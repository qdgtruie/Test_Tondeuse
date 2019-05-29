package com.publicissapient.tondeuse.errors;

/**
 * Exception raised when a mowner is provided with an invalid instruction
 */
public class IllegalMownerInstruction extends IllegalStateException{

    /**
     * Instanciate a IllegalMownerInstruction with a specifc message
     * @param message a contextual message
     */
    public IllegalMownerInstruction(String message){
        super(message);
    }

    /**
     * Instanciate a IllegalMownerInstruction with a specifc message and innerException
     * @param message a contextual message
     * @param ex an inner exception
     */
    public IllegalMownerInstruction(String message, Exception ex) {
        super(message,ex);
    }
}
