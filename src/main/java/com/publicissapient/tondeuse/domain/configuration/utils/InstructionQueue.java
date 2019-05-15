package com.publicissapient.tondeuse.domain.configuration.utils;

import com.publicissapient.tondeuse.domain.Instruction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Queue;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class InstructionQueue {

    @Getter
    private Queue<Instruction> instructions;
}

