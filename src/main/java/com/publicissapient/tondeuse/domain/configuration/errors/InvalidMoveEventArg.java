package com.publicissapient.tondeuse.domain.configuration.errors;

import com.publicissapient.tondeuse.domain.Position;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "from")
public class InvalidMoveEventArg {
    @Getter
    private UUID mownerID;
    @Getter
    private Position targetPosition;
}
