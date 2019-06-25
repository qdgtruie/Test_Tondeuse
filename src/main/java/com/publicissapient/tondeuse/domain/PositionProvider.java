package com.publicissapient.tondeuse.domain;


import java.util.UUID;

/**
 * Capability to identify a mower and its current location
 */
public interface PositionProvider {

    /**
     * Mowner ID
     * @return a globally unique iendtifier for a given mowner
     */
    UUID getId();

    /**
     * Current location of the associated mowner
     * @return a location
     */
    MowerLocation getCurrentLocation();

}
