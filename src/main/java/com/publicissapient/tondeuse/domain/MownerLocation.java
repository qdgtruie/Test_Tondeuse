package com.publicissapient.tondeuse.domain;

import lombok.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

@ToString(exclude = "positionListener")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC, staticName = "with")
public class MownerLocation {

    @Getter(AccessLevel.PACKAGE)
    @NonNull
    private Position position;

    @Getter
    @NonNull
    private Orientation orientation;


    private Queue<Predicate<Position>> positionListener = new LinkedList<>();

    void ShiftRight() {

        switch (orientation) {
            case N:
                orientation = Orientation.E;
                break;
            case E:
                orientation = Orientation.S;
                break;
            case S:
                orientation = Orientation.W;
                break;
            case W:
                orientation = Orientation.N;
                break;
        }

    }

    void ShiftLeft() {

        switch (orientation) {
            case N:
                orientation = Orientation.W;
                break;
            case E:
                orientation = Orientation.N;
                break;
            case S:
                orientation = Orientation.E;
                break;
            case W:
                orientation = Orientation.S;
                break;
        }
    }

    /*
     *
     *
     * */
     void ShiftForward() {

        int nextX = 0;
        int nextY = 0;

        switch (orientation) {
            case N:
                nextY = position.getY() + 1;
                break;
            case E:
                nextX = position.getX() + 1;
                break;
            case S:
                nextY = position.getY() - 1;
                break;
            case W:
                nextX = position.getX() - 1;
                break;
            default:
                break;
        }

        if (onMovingTo(Position.locatedAt(nextX, nextY))) {
            position.setY(nextY);
            position.setX(nextX);
        }

    }

    private boolean onMovingTo(Position movingTo) {

        return positionListener.stream().allMatch(check -> check.test(movingTo));

    }

     void addPositionListener(Predicate<Position> check){
        positionListener.add(check);
    }


}
