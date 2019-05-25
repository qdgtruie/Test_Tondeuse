package com.publicissapient.tondeuse.domain;

import lombok.*;

/**
 * Describe a location on a Garden grid.
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "locatedAt")
public  @Data class Position {

    /**
     * x coordinate within the Garden
     */
    @Getter
    private  int x;

    /**
     * Y coordinate within the Garden
     */
    @Getter
    private  int y;


    /**
     * Check whether the position is whithin the Garden
     * @param upperRight upperRight corner of the Garden
     * @return true when the position is within the Garden.
     */
    public boolean isWithinRectangle(Position upperRight) {
        return this.getX() <= upperRight.getX() && this.getY() <= upperRight.getY();
    }
}
