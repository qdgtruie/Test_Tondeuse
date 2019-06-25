package com.publicissapient.tondeuse.service;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MownerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.errors.IllegalMownerInstruction;
import io.vavr.collection.List;
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
     * Queue of mowners (with their attached instruction set) to be controlled
     */
    private final Queue<ExecutionBatch> executions = new LinkedList<>();

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

        var garden = config.getGarden();
        garden.addFenceViolationListener(this::raiseAlertNotification);

        executions.clear();

        for (var item : config.getMowners())
            executions.add(createExecutionBatch(garden, item));

        makeMownersSeeEachOthers();

        if(log.isDebugEnabled()) {
            log.debug("Controller loaded garden configuration {}", garden.toString());
            log.debug("Controller loaded executions are {}", executions.toString());
        }

        return this;
    }

    /**
     * reprocess the executionBatches to add to each mowner position checkers from all the other mowners.
     */
    private void makeMownersSeeEachOthers() {

        for (var batch : executions)
            for (var otherBtach : executions) {

                var currentMowner = batch.getMowner();
                var otherMowner = otherBtach.getMowner();

                if (currentMowner != otherMowner) {
                    currentMowner.addPositionChecker(
                            targetPosition -> otherMowner.checkcollision(currentMowner.getId(), targetPosition)
                    );
                }
            }
    }

    /**
     * Build a ExecutionBatch object
     * @param garden to be used on the ExecutionBatch
     * @param item MownerConfig used on the ExecutionBatch
     * @return a batch
     */
    private ExecutionBatch createExecutionBatch(final GardenConfiguration garden, final MownerConfiguration item) {
        MownerLocation initialLocation = item.getLocation();

        var mowner = Mowner.initialLocation( UUID.randomUUID(),initialLocation);
        mowner.addPositionChecker(targetPosition -> garden.isValideMove(mowner.getId(),targetPosition) );
        mowner.addCollisionListener(this::raiseAlertNotification);

        var instructions = item.getInstructionQueue().getInstructions();

        return new ExecutionBatch(mowner,instructions);
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

        @Getter
        private final Mowner mowner;
        @Getter
        private final Queue<Instruction> instructions;

    }

    /**
     * Run the all the mowners
     */
    public void run() {
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

        List.ofAll(instructions).forEach(step->executeStep(mowner,step));

        raisePositionPublication(((PositionProvider) mowner));
    }

    /**
     * Try to execute a single instruction on a single mowner
     * @param mowner mowner to execute instruction on
     * @param step instruction to be executed on the given mowner
     */
    private void executeStep(@NonNull Controllable mowner, @NonNull Instruction step) {

        log.debug("Executing instruction [{}] on mowner {}", step.toString(),mowner.toString());

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
