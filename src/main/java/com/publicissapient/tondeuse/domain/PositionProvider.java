package com.publicissapient.tondeuse.domain;


import java.util.UUID;
import java.util.function.Consumer;

public interface PositionProvider {

    UUID getID();
    MownerLocation getCurrentLocation();

}
