package com.publicissapient.tondeuse.domain;

/**
 * Capability to display a MownerPosition
 */
@FunctionalInterface
public interface ResultPublisher {

    /**
     * Publish a Position
     * @param positionProvider provided prosition to be displayed
     */
    void publishPosition(PositionProvider positionProvider);
}
