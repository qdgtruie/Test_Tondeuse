package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.ConfigurationProvider;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MowerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.service.MowerController;
import lombok.extern.slf4j.Slf4j;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class ControllerTest {

    private class LimitedMemoryProvider extends MemoryProvider {

        public GardenConfiguration getGardenConfiguration()  {
            return GardenConfiguration.endsAt(Position.locatedAt(getRandomInt(10), getRandomInt(10)));
        }

    }

    private class OutOfBoundMemoryProvider extends LimitedMemoryProvider {

        public Queue<MowerConfiguration> getMowerConfiguration()  {
            Queue<MowerConfiguration> result = new LinkedList<>();

            MowerLocation location = MowerLocation.with(Position.locatedAt(1,
                    1), Orientation.S);

            Queue<Instruction> queue = new LinkedList<>();
            queue.add(Instruction.A);
            queue.add(Instruction.A);
            queue.add(Instruction.A);

            var conf = MowerConfiguration.with(location, queue);
            result.add(conf);

            return result;
        }

    }

    private class MemoryProvider implements ConfigurationProvider {


        public GardenConfiguration getGardenConfiguration() {
            return GardenConfiguration.endsAt(Position.locatedAt(getRandomInt(100), getRandomInt(100)));
        }

        private Instruction getRandomInstruction() {
            LinkedList<Instruction> list = new LinkedList<>();
            list.add(Instruction.A);
            list.add(Instruction.D);
            list.add(Instruction.G);
            return list.get(getRandomInt(3));
        }

        private Orientation getRandomOrientation() {
            LinkedList<Orientation> list = new LinkedList<>();

            list.add(Orientation.E);
            list.add(Orientation.N);
            list.add(Orientation.S);
            list.add(Orientation.W);

            return list.get(getRandomInt(4));
        }

        private final Random random = new Random();

        int getRandomInt(int bound) {
            return random.nextInt(bound);
        }

        public Queue<MowerConfiguration> getMowerConfiguration()  {
            Queue<MowerConfiguration> result = new LinkedList<>();

            for (int i = 0; i < 100; i++) {
                MowerLocation location = MowerLocation.with(Position.locatedAt(getRandomInt(100),
                        getRandomInt(100)), getRandomOrientation());

                Queue<Instruction> queue = new LinkedList<>();

                for (int j = 0; j < 100; j++)
                    queue.add(getRandomInstruction());

                MowerConfiguration conf = MowerConfiguration.with(location, queue);
                result.add(conf);
            }
            return result;
        }
    }


    @Property
    void mowerCanRunAtScale() throws ConfigurationException {

        MowerController controller = new MowerController().load(Configuration.basedOn(new MemoryProvider()));
        controller.run();

    }

    @Test
    void mowerCanNotGoOutsideOfGarden() throws ConfigurationException {

        MowerController controller = new MowerController().load(Configuration.basedOn(new LimitedMemoryProvider()));
        controller.run();
    }

    private boolean messageGotPublished = false;

    @Test
    void mowerCanPublishResult() throws ConfigurationException {

       new MowerController()
                         .withResultPublisher(this::publishPosition)
                         .load(Configuration.basedOn(new LimitedMemoryProvider()))
                         .run();

        assertTrue(messageGotPublished,"Publish should have changed the value to true" );
    }

    private void publishPosition(PositionProvider positionProvider) {
        log.info(positionProvider.toString());
        messageGotPublished = true;
    }


    @Test
    void mowerCanRaiseOutOfBoundAlert() throws ConfigurationException {

        new MowerController()
                .withAlerter(this::notifyAlert)
                .load(Configuration.basedOn(new OutOfBoundMemoryProvider()))
                .run();

        assertTrue(alertGotRaised,"Alert should have been raised." );
    }

    private boolean alertGotRaised = false;

    private void notifyAlert(InvalidMoveEventArg invalidMoveEventArg) {
        log.info(invalidMoveEventArg.toString());
        alertGotRaised = true;
    }




}