package com.publicissapient.tondeuse.domain.Configuration;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import com.publicissapient.tondeuse.domain.Position;
import lombok.ToString;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC,staticName = "endsAt")
public class GardenConfiguration {

    private final Position upperRight;

}
