package com.publicissapient.tondeuse.domain;

import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class MownerLocation {

    @Getter
    private Position initialPosition;

    @Getter
    private Orientation initialOrientation;

    public void ShiftRight() {

        switch (initialOrientation){
            case N:
                initialOrientation = Orientation.E;
                break;
            case E:
                initialOrientation = Orientation.S;
                break;
            case S:
                initialOrientation = Orientation.W;
                break;
            case W:
                initialOrientation = Orientation.N;
                break;
        }

    }

    public void ShiftLeft() {

        switch (initialOrientation){
            case N:
                initialOrientation = Orientation.W;
                break;
            case E:
                initialOrientation = Orientation.N;
                break;
            case S:
                initialOrientation = Orientation.E;
                break;
            case W:
                initialOrientation = Orientation.S;
                break;
        }
    }
    /*
    *               5,5
    *   0,0
    * */
    public void ShiftForward() {

        switch (initialOrientation){
            case N:
                initialPosition.setY(initialPosition.getY()+1);
                break;
            case E:
                initialPosition.setX(initialPosition.getX()+1);
                break;
            case S:
                initialPosition.setY(initialPosition.getY()-1);
                break;
            case W:
                initialPosition.setX(initialPosition.getX()-1);
                break;
        }
    }
}
