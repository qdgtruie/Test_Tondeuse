package com.publicissapient.tondeuse.domain.configuration.errors;

import com.publicissapient.tondeuse.domain.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "from")
public class InvalidMoveEventArg {
    private UUID mownerID;
    private Position targetPosition;
}
