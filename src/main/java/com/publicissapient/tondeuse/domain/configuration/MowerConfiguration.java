package com.publicissapient.tondeuse.domain.configuration;

import com.publicissapient.tondeuse.domain.configuration.utils.InstructionQueue;
import com.publicissapient.tondeuse.domain.MowerLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Reificate the configuration for ONE Mowner
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class MowerConfiguration {

    /***
     * Hold the mowner location, both in direction and position
     */
    @Getter
    private MowerLocation location;

    /**
     * Hold the sequence of instruction to be executed by the mowner
     */
    @Getter
    private InstructionQueue instructionQueue;
}
