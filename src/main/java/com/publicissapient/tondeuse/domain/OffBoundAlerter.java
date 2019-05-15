package com.publicissapient.tondeuse.domain;

import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;


public interface OffBoundAlerter {

    void notifyAlert(InvalidMoveEventArg invalidMoveEventArg);

}
