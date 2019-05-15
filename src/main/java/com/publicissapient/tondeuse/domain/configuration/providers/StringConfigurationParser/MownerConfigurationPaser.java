package com.publicissapient.tondeuse.domain.configuration.providers.StringConfigurationParser;

import com.publicissapient.tondeuse.domain.configuration.utils.InstructionQueue;
import com.publicissapient.tondeuse.domain.configuration.MownerConfiguration;
import com.publicissapient.tondeuse.domain.Instruction;
import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Slf4j
public class MownerConfigurationPaser {

    public Queue<MownerConfiguration> Parse(List<String> lines) throws ConfigurationFormatException {

        var result = new LinkedList<MownerConfiguration>();

        for (int i = 0; i < lines.size(); i = i + 2) {

            String mownerLine = lines.get(i);
            MownerLocation location = ParseMownerLocation(mownerLine);

            String instructionsLine = lines.get(i+1);
            InstructionQueue instructionQueue = ParseInstructionSet(instructionsLine);

            var conf = MownerConfiguration.with(location, instructionQueue);
            result.add(conf);
        }

        return result;
    }

    private InstructionQueue ParseInstructionSet(String instructionsLine) throws ConfigurationFormatException {
        InstructionQueue result = InstructionQueue.with(new LinkedList<>());

        if (CheckFormatInstruction(instructionsLine)) {
            var instructionTokens = com.google.common.base.Splitter.fixedLength(1).splitToList(instructionsLine);

            var queue = instructionTokens.stream()
                    .map(Instruction::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new));

            result = InstructionQueue.with(queue);
        }

        return result;
    }


    private MownerLocation ParseMownerLocation(String mownerLine) throws ConfigurationFormatException {

        final String SEPARATOR = " ";

        MownerLocation result = MownerLocation.with(Position.locatedAt(0,0),Orientation.N);

        if (CheckFormat(mownerLine)) {
            var positionTokens = com.google.common.base.Splitter.on(SEPARATOR).splitToList(mownerLine);

            int x = Integer.valueOf(positionTokens.get(0));
            int y = Integer.valueOf(positionTokens.get(1));
            String direction = positionTokens.get(2);

            result= MownerLocation.with(Position.locatedAt(x, y), Orientation.valueOf(direction));
        }
        return result;
    }

    private boolean CheckFormat(String headLine) throws ConfigurationFormatException {
        throw new ConfigurationFormatException("not implemented", new Exception());
    }

    private boolean CheckFormatInstruction(String instructionsLine) throws ConfigurationFormatException {
        throw new ConfigurationFormatException("not implemented", new Exception());
    }
}
