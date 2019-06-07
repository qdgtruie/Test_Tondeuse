package com.publicissapient.tondeuse.domain.configuration;

import com.publicissapient.tondeuse.domain.configuration.utils.InstructionQueue;
import com.publicissapient.tondeuse.domain.MownerLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Reificate the configuration for ONE Mowner
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class MownerConfiguration {

    /***
     * Hold the mowner location, both in direction and position
     */
    @Getter
    private MownerLocation location;

    /**
     * Hold the sequence of instruction to be executed by the mowner
     */
    @Getter
    private InstructionQueue instructionQueue;
}
