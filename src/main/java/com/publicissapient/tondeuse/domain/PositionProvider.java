package com.publicissapient.tondeuse.domain;


import java.util.UUID;

/**
 * Capability to identify a mowner and its current location
 */
public interface PositionProvider {

    /**
     * Mowner ID
     * @return a globally unique iendtifier for a given mowner
     */
    UUID getID();

    /**
     * Current location of the associated mowner
     * @return a location
     */
    MownerLocation getCurrentLocation();

}
