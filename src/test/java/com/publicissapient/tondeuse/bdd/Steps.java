package com.publicissapient.tondeuse.bdd;

import com.publicissapient.tondeuse.domain.Instruction;
import com.publicissapient.tondeuse.domain.MowerLocation;
import com.publicissapient.tondeuse.domain.Orientation;
import com.publicissapient.tondeuse.domain.Position;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MowerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.providers.stringconfigurationparser.ConfigurationFormatException;
import com.publicissapient.tondeuse.service.MowerController;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Steps {

    private GardenConfiguration  gardenConfig;
    private final List<MowerConfiguration> mowers = new LinkedList<>();
    private MowerController controller = new MowerController();

    @BeforeScenario
    public void resetState(){
        controller = new MowerController();
        mowers.clear();
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

            mowers.add( MowerConfiguration.with(
                    MowerLocation.with(Position.locatedAt(x, y), Orientation.valueOf(direction)), queue));
        }
        else
            throw new ConfigurationFormatException("Invalid Configuration format for Instruction set : '"+instructionsLine+"'");

        }


    @When("the controller run the mowers")
    public void mowersExecuteInstructions() throws ConfigurationException {

        controller.load(Configuration.basedOn( new ConfigurationProvider(){
            public GardenConfiguration getGardenConfiguration() { return gardenConfig;}
            public Queue<MowerConfiguration> getMowerConfiguration() { return  (Queue<MowerConfiguration>) mowers;}
        }
        )).run();
    }

    @Then("Mower number $number should be at final position $x $y $orientationLabel")
    public void mowerShouldBeAtPosition(String number, int x, int y, String direction) {
        int idx =Integer.parseInt(number)-1;
        assertEquals(x, mowers.get(idx).getLocation().getPosition().getX(),"x position should be ["+x+"]");
        assertEquals(y, mowers.get(idx).getLocation().getPosition().getY(),"Y position should be ["+x+"]");
        assertEquals(Orientation.valueOf(direction), mowers.get(idx).getLocation().getOrientation(),
                "Orientation should be ["+direction+"]");

    }
}
