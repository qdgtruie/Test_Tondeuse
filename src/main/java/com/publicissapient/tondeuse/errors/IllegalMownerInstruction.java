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
}
