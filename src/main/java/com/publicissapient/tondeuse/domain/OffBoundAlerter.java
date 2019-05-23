package com.publicissapient.tondeuse.domain;

import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;


/**
 * Capability to report when an invalid move has been attempted
 */
public interface OffBoundAlerter {

    /**
     * Report invalid move
     * @param invalidMoveEventArg event containing the invalid position
     */
    void notifyAlert(InvalidMoveEventArg invalidMoveEventArg);

}
