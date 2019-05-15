package com.publicissapient.tondeuse.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "locatedAt")
public  @Data class Position {

    private  int x;

    private  int y;


    public boolean isWithinRectangle(Position upperRight) {
        return this.getX() <= upperRight.getX() && this.getY() <= upperRight.getY();
    }
}
