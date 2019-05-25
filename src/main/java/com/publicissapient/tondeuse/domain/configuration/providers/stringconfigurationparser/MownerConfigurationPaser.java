package com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser;

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
import java.util.regex.Pattern;
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

        if (CheckFormatMowner(mownerLine)) {
            var positionTokens = com.google.common.base.Splitter.on(SEPARATOR).splitToList(mownerLine);

            int x = Integer.valueOf(positionTokens.get(0));
            int y = Integer.valueOf(positionTokens.get(1));
            String direction = positionTokens.get(2);

            result= MownerLocation.with(Position.locatedAt(x, y), Orientation.valueOf(direction));
        }
        return result;
    }

    private boolean CheckFormatMowner(final String mownerLine) throws ConfigurationFormatException {
        var p1 = GetPatternMowner();
        if(! p1.matcher(mownerLine).matches())
            throw new ConfigurationFormatException("Invalid Configuration format for Mowner : '"+mownerLine+"'");

        return true;
    }

    private boolean CheckFormatInstruction(final String instructionsLine) throws ConfigurationFormatException {
        var p1 = GetPatternInstruction();
        if(! p1.matcher(instructionsLine).matches())
            throw new ConfigurationFormatException("Invalid Configuration format for Instruction set : '"+instructionsLine+"'");

        return true;
    }

    private Pattern patternMowner;

    private Pattern GetPatternMowner() {
        if(patternMowner==null)
            patternMowner = Pattern.compile("\\d+ \\d+ [NSEW]");

        return patternMowner;
    }

    private Pattern patternInstruction;

    private Pattern GetPatternInstruction() {
        if(patternInstruction==null)
            patternInstruction = Pattern.compile("[AGD]+");

        return patternInstruction;
    }
}
