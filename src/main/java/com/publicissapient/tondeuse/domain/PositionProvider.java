package com.publicissapient.tondeuse.domain;


import java.util.UUID;

/**
 * Capability to identify a mower and its current location
 */
public interface PositionProvider {

    /**
     * Mower ID
     * @return a globally unique identifier for a given mower
     */
    UUID getId();

    /**
     * Current location of the associated mower
     * @return a location
     */
    MowerLocation getCurrentLocation();

}
