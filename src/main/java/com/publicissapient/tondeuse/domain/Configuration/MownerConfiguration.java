package com.publicissapient.tondeuse.domain.Configuration;

import com.publicissapient.tondeuse.domain.MownerLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "with")
public class MownerConfiguration {

    @Getter
    private MownerLocation location;

    @Getter
    private InstructionQueue instructions;
}
