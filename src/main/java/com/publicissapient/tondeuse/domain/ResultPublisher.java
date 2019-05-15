package com.publicissapient.tondeuse.domain;

import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;

@FunctionalInterface
public interface ResultPublisher {

    void publishPosition(PositionProvider positionProvider);
}
