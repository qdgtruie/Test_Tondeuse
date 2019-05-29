package com.publicissapient.tondeuse.unitTest;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.*;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.domain.configuration.utils.InstructionQueue;
import com.publicissapient.tondeuse.service.MownerController;

import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.junit.jupiter.api.Test;
import net.jqwik.api.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private class LimittedMemoryProvider extends MemoryProvider {

        public GardenConfiguration getGardenConfiguration()  {
            return GardenConfiguration.endsAt(Position.locatedAt(getRandomInt(10), getRandomInt(10)));
        }

    }

    private class OutOfBoundMemoryProvider extends LimittedMemoryProvider {

        public Queue<MownerConfiguration> getMownerConfiguration()  {
            Queue<MownerConfiguration> result = new LinkedList<>();

            MownerLocation location = MownerLocation.with(Position.locatedAt(1,
                    1), Orientation.S);

            Queue<Instruction> queue = new LinkedList<>();
            queue.add(Instruction.A);
            queue.add(Instruction.A);
            queue.add(Instruction.A);

            var conf = MownerConfiguration.with(location, InstructionQueue.with(queue));
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

        private Random random = new Random();

        protected int getRandomInt(int bound) {
            return random.nextInt(bound);
        }

        public Queue<MownerConfiguration> getMownerConfiguration()  {
            Queue<MownerConfiguration> result = new LinkedList<>();

            for (int i = 0; i < 100; i++) {
                MownerLocation location = MownerLocation.with(Position.locatedAt(getRandomInt(100),
                        getRandomInt(100)), getRandomOrientation());

                Queue<Instruction> queue = new LinkedList<>();

                for (int j = 0; j < 100; j++)
                    queue.add(getRandomInstruction());

                MownerConfiguration conf = MownerConfiguration.with(location, InstructionQueue.with(queue));
                result.add(conf);
            }
            return result;
        }
    }


    @Property
    public void MownerCanRunAtScale() throws ConfigurationException {

        MownerController controller = new MownerController().load(Configuration.basedOn(new MemoryProvider()));
        controller.run();

    }

    @Test
    public void MownerCanNotGoOutsideOfGarden() throws ConfigurationException {

        MownerController controller = new MownerController().load(Configuration.basedOn(new LimittedMemoryProvider()));
        controller.run();
    }

    private boolean messageGotPublished = false;

    @Test
    public void MownerCanPublishResult() throws ConfigurationException {

       new MownerController()
                         .withResultPublisher(this::publishPosition)
                         .load(Configuration.basedOn(new LimittedMemoryProvider()))
                         .run();

        assertTrue(messageGotPublished,"Pusblish should have changed the value to true" );
    }

    private void publishPosition(PositionProvider positionProvider) {
        messageGotPublished = true;
    }


    @Test
    public void MownerCanRaiseOutOfBoundAlert() throws ConfigurationException {

        new MownerController()
                .withAlerter(this::notifyAlert)
                .load(Configuration.basedOn(new OutOfBoundMemoryProvider()))
                .run();

        assertTrue(alertGotRaised,"Alert should have been raised." );
    }

    private boolean alertGotRaised = false;

    private void notifyAlert(InvalidMoveEventArg invalidMoveEventArg) {
        alertGotRaised = true;
    }




}