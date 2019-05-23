package com.publicissapient.tondeuse.service;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MownerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.errors.IllegalMownerInstruction;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

/**
 * Central controller to program the mowners
 */
@Slf4j
public final class MownerController {


    /**
     * Configration of the garden to be mowned
     */
    private GardenConfiguration garden;

    /**
     * Queue of mowners (with their attached instruction set) to be controlled
     */
    private Queue<ExecutionBatch> executions = new LinkedList<>();

    /**
     * Alerter for out of garden bounds events
     */
    @Setter
    private OffBoundAlerter offboundAlert;


    /**
     * Publisher to display final position of mowners
     */
    @Setter
    private ResultPublisher publisher;


    /**
     * Initialize mowners based on a given configuration
     * @param config a configuratio  to be loaded
     * @return the controller
     */
    public MownerController load(@NonNull final Configuration config) {

        this.garden = config.getGarden();
        this.garden.addFenceViolationListener(this::raiseAlertNotification);

        executions.clear();
        for (MownerConfiguration item : config.getMowners()) {
            MownerLocation initialLocation = item.getLocation();

            var mowner = Mowner.initialLocation( UUID.randomUUID(),initialLocation);
            mowner.addOffBoundChecker(x -> garden.isValideMove(mowner.getID(),x) );

            var instructions = item.getInstructions().getInstructions();

            var e = new ExecutionBatch(mowner,instructions);
            executions.add(e);
        }
        return this;
    }

    /**
     * Set the out of bound Alerter on a given channel
     * @param offboundAlert an OffBoundAlerter instance to raise alerts
     * @return the controller
     */
    public MownerController withAlerter(final OffBoundAlerter offboundAlert){
        setOffboundAlert(offboundAlert);
        return this;
    }

    /**
     * Set the result publiosher on a given channel
     * @param publisher a result publisher
     * @return  the controller
     */
    public MownerController withResultPublisher(final ResultPublisher publisher) {
        setPublisher(publisher);
        return this;
    }

    /**
     * Raise an alert notification based on an event
     * @param e the invalid move event
     */
    private void raiseAlertNotification(final InvalidMoveEventArg e) {
        if(offboundAlert != null)
            offboundAlert.notifyAlert(e);
    }

    /**
     * Publish a given position on a given channel
     * @param position the given position
     */
    private void raisePositionPublication(final PositionProvider position) {
        if(publisher != null)
            publisher.publishPosition(position);
    }


    /**
     * Tuple of Mowner and associated Instruction
     */
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private @Data class ExecutionBatch {

        private final Mowner mowner;
        private final Queue<Instruction> instructions;

    }

    /**
     * Run the all the mowners
     */
    public void Run() {
        for (ExecutionBatch e : executions) {
            runMowner(e.getMowner(), e.getInstructions());
        }
    }

    /**
     * Run one specific mowner
     * @param mowner mowner to be executed
     * @param instructions set of instruction to be eecuted on the mowner
     */
    private void runMowner(@NonNull Controllable mowner, @NonNull Queue<Instruction> instructions) {

        for (Instruction step : instructions)
            executeStep(mowner, step);

        raisePositionPublication(((PositionProvider) mowner));
    }

    /**
     * Try to execute a single instruction on a single mowner
     * @param mowner mowner to execute instruction on
     * @param step instruction to be executed on the given mowner
     */
    private void executeStep(@NonNull Controllable mowner, @NonNull Instruction step) {

        switch (step) {
            case A:
                mowner.moveForward();
                break;
            case D:
                mowner.turnRight();
                break;
            case G:
                mowner.turnLeft();
                break;
            default:
                throw new IllegalMownerInstruction("Unexpected Instruction value: " + step);
        }

    }

}
