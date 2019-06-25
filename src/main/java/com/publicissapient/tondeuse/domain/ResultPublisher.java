package com.publicissapient.tondeuse.domain;

/**
 * Capability to display a MowerPosition
 */
@FunctionalInterface
public interface ResultPublisher {

    /**
     * Publish a Position
     * @param positionProvider provided position to be displayed
     */
    void publishPosition(PositionProvider positionProvider);
}
