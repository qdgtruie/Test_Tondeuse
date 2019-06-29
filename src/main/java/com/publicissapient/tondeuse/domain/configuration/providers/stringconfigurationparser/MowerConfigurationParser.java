package com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser;

import com.publicissapient.tondeuse.domain.Instruction;
import com.publicissapient.tondeuse.domain.MowerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import com.publicissapient.tondeuse.domain.configuration.MowerConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class MowerConfigurationParser {

    public Queue<MowerConfiguration> parse(List<String> lines) throws ConfigurationFormatException {

        var result = new LinkedList<MowerConfiguration>();

        for (int i = 0; i < lines.size(); i = i + 2) {

            String mowerLine = lines.get(i);
            MowerLocation location = parseMowerLocation(mowerLine);

            String instructionsLine = lines.get(i+1);
            var instructionQueue = parseInstructionSet(instructionsLine);

            var conf = MowerConfiguration.with(location, instructionQueue);
            result.add(conf);
        }

        return result;
    }

    private Queue<Instruction> parseInstructionSet(String instructionsLine) throws ConfigurationFormatException {
        Queue<Instruction> result;

        if (getPatternInstruction().matcher(instructionsLine).matches()) {
            var instructionTokens = com.google.common.base.Splitter.fixedLength(1).splitToList(instructionsLine);

            result = instructionTokens.stream()
                    .map(Instruction::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new));

        }
        else
            throw new ConfigurationFormatException("Invalid Configuration format for Instruction set : '"+instructionsLine+"'");


        return result;
    }


    private MowerLocation parseMowerLocation(String mowerLine) throws ConfigurationFormatException {

        final String SEPARATOR = " ";

        MowerLocation result;

        if (getPatternMower().matcher(mowerLine).matches()) {
            var positionTokens = com.google.common.base.Splitter.on(SEPARATOR).splitToList(mowerLine);

            int x = Integer.valueOf(positionTokens.get(0));
            int y = Integer.valueOf(positionTokens.get(1));
            String direction = positionTokens.get(2);

            result= MowerLocation.with(Position.locatedAt(x, y), Orientation.valueOf(direction));
        }
        else
            throw new ConfigurationFormatException("Invalid Configuration format for Mower : '"+mowerLine+"'");

        return result;
    }

    private Pattern patternMower;

    private Pattern getPatternMower() {
        if(patternMower ==null)
            patternMower = Pattern.compile("\\d+ \\d+ [NSEW]");

        return patternMower;
    }

    private Pattern patternInstruction;

    private Pattern getPatternInstruction() {
        if(patternInstruction==null)
            patternInstruction = Pattern.compile("[AGD]+");

        return patternInstruction;
    }
}
