package com.publicissapient.tondeuse.domain.configuration;

import com.publicissapient.tondeuse.domain.Instruction;
import com.publicissapient.tondeuse.domain.MowerLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Queue;

/**
 * Reificate the configuration for ONE Mower
 */
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class MowerConfiguration {

    /***
     * Hold the mower location, both in direction and position
     */
    @Getter
    private MowerLocation location;

    /**
     * Hold the sequence of instruction to be executed by the mower
     */
    @Getter
    private Queue<Instruction> instructions;
}
