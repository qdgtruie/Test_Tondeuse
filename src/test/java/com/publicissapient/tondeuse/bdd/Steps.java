package com.publicissapient.tondeuse.bdd;

import com.publicissapient.tondeuse.domain.Instruction;
import com.publicissapient.tondeuse.domain.MownerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MownerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import com.publicissapient.tondeuse.domain.configuration.utils.InstructionQueue;
import com.publicissapient.tondeuse.service.MownerController;
import org.jbehave.core.annotations.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {

    private GardenConfiguration  gardenConfig;
    private List<MownerConfiguration> mowners = new LinkedList<>();
    private MownerController controller = new MownerController();

    @BeforeScenario
    public void resetState(){
        controller = new MownerController();
        mowners.clear();
    }

    @Given("a garden of $width by $height")
    public void garden(final int width, final int height) {
        gardenConfig = GardenConfiguration.endsAt(Position.locatedAt(width, height));
    }

    @Given("a mower at position $x $y $direction with instructions $instructionsLine")
    public void mowerAtPosition(final int x, final int y, final String direction, final String instructionsLine) throws ConfigurationFormatException{
        if (Pattern.compile("[AGD]+").matcher(instructionsLine).matches()) {
            var instructionTokens = com.google.common.base.Splitter.fixedLength(1).splitToList(instructionsLine);

            var queue = instructionTokens.stream()
                    .map(Instruction::valueOf)
                    .collect(Collectors.toCollection(LinkedList::new));

            mowners.add( MownerConfiguration.with(
                    MownerLocation.with(Position.locatedAt(x, y), Orientation.valueOf(direction)),
                    InstructionQueue.with(queue)));
        }
        else
            throw new ConfigurationFormatException("Invalid Configuration format for Instruction set : '"+instructionsLine+"'");

        }


    @When("the controller run the mowers")
    public void mowersExecuteInstructions() throws ConfigurationException {

        controller.load(Configuration.basedOn( new ConfigurationProvider(){
            public GardenConfiguration getGardenConfiguration() { return gardenConfig;}
            public Queue<MownerConfiguration> getMownerConfiguration() {return (Queue<MownerConfiguration>)mowners;}
        }
        )).run();
    }

    @Then("Mower number $number should be at final position $x $y $orientationLabel")
    public void mowerShouldBeAtPosition(String number, int x, int y, String direction) {
        int idx =Integer.parseInt(number)-1;
        assertEquals(x,mowners.get(idx).getLocation().getPosition().getX(),"x position should be ["+x+"]");
        assertEquals(y,mowners.get(idx).getLocation().getPosition().getY(),"Y position should be ["+x+"]");
        assertEquals(Orientation.valueOf(direction),mowners.get(idx).getLocation().getOrientation(),
                "Orientation should be ["+direction+"]");

    }
}
