package com.publicissapient.tondeuse.service;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MowerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.errors.IllegalMowerInstruction;
import io.vavr.collection.List;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

/**
 * Central controller to program the mowers
 */
@Slf4j
public final class MowerController {


    /**
     * Queue of mowers (with their attached instruction set) to be controlled
     */
    private final Queue<ExecutionBatch> executions = new LinkedList<>();

    /**
     * Alerter for out of garden bounds events
     */
    @Setter
    private OffBoundAlerter offBoundAlert;


    /**
     * Publisher to display final position of mowers
     */
    @Setter
    private ResultPublisher publisher;


    /**
     * Initialize mowers based on a given configuration
     * @param config a configuration  to be loaded
     * @return the controller
     */
    public MowerController load(@NonNull final Configuration config) {

        var garden = config.getGarden();
        garden.addFenceViolationListener(this::raiseAlertNotification);

        executions.clear();

        for (var item : config.getMowers())
            executions.add(createExecutionBatch(garden, item));

        makeMowersSeeEachOthers();

        if(log.isDebugEnabled()) {
            log.debug("Controller loaded garden configuration {}", garden.toString());
            log.debug("Controller loaded executions are {}", executions.toString());
        }

        return this;
    }

    /**
     * reprocess the executionBatches to add to each mower position checkers from all the other mowers.
     */
    private void makeMowersSeeEachOthers() {

        for (var batch : executions)
            for (var otherBatch : executions) {

                var currentMower = batch.getMower();
                var otherMower = otherBatch.getMower();

                if (currentMower != otherMower) {
                    currentMower.addPositionChecker(
                            targetPosition -> otherMower.checkCollision(currentMower.getId(), targetPosition)
                    );
                }
            }
    }

    /**
     * Build a ExecutionBatch object
     * @param garden to be used on the ExecutionBatch
     * @param item MowerConfig used on the ExecutionBatch
     * @return a batch
     */
    private ExecutionBatch createExecutionBatch(final GardenConfiguration garden, final MowerConfiguration item) {
        MowerLocation initialLocation = item.getLocation();

        var mower = Mower.initialLocation( UUID.randomUUID(),initialLocation);
        mower.addPositionChecker(targetPosition -> garden.isValidMove(mower.getId(),targetPosition) );
        mower.addCollisionListener(this::raiseAlertNotification);

        var instructions = item.getInstructions();

        return new ExecutionBatch(mower,instructions);
    }

    /**
     * Set the out of bound Alerter on a given channel
     * @param offBoundAlert an OffBoundAlerter instance to raise alerts
     * @return the controller
     */
    public MowerController withAlerter(final OffBoundAlerter offBoundAlert){
        setOffBoundAlert(offBoundAlert);
        return this;
    }

    /**
     * Set the result publisher on a given channel
     * @param publisher a result publisher
     * @return  the controller
     */
    public MowerController withResultPublisher(final ResultPublisher publisher) {
        setPublisher(publisher);
        return this;
    }

    /**
     * Raise an alert notification based on an event
     * @param e the invalid move event
     */
    private void raiseAlertNotification(final InvalidMoveEventArg e) {
        if(offBoundAlert != null)
            offBoundAlert.notifyAlert(e);
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
     * Tuple of Mower and associated Instruction
     */
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private @Data class ExecutionBatch {

        @Getter
        private final Mower mower;
        @Getter
        private final Queue<Instruction> instructions;

    }

    /**
     * Run the all the mowers
     */
    public void run() {
        for (ExecutionBatch e : executions) {
            runMower(e.getMower(), e.getInstructions());
        }
    }

    /**
     * Run one specific mower
     * @param mower mower to be executed
     * @param instructions set of instruction to be executed on the mower
     */
    private void runMower(@NonNull Controllable mower, @NonNull Queue<Instruction> instructions) {

        List.ofAll(instructions).forEach(step->executeStep(mower,step));

        raisePositionPublication(((PositionProvider) mower));
    }

    /**
     * Try to execute a single instruction on a single mower
     * @param mower mower to execute instruction on
     * @param step instruction to be executed on the given mower
     */
    private void executeStep(@NonNull Controllable mower, @NonNull Instruction step) {

        log.debug("Executing instruction [{}] on mower {}", step.toString(),mower.toString());

        switch (step) {
            case A:
                mower.moveForward();
                break;
            case D:
                mower.turnRight();
                break;
            case G:
                mower.turnLeft();
                break;
            default:
                throw new IllegalMowerInstruction("Unexpected Instruction value: " + step);
        }

    }

}
