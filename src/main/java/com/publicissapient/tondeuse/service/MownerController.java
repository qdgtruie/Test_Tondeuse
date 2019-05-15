package com.publicissapient.tondeuse.service;

import com.publicissapient.tondeuse.domain.*;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.GardenConfiguration;
import com.publicissapient.tondeuse.domain.configuration.MownerConfiguration;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import lombok.*;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Slf4j
public class MownerController {


    private GardenConfiguration garden;
    private Queue<ExecutionBatch> executions = new LinkedList<>();

    @Setter
    private OffBoundAlerter offboundAlert;


    @Setter
    private ResultPublisher publisher;






    public MownerController load(@NonNull Configuration config) {

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

    public MownerController withAlerter(OffBoundAlerter offboundAlert){
        setOffboundAlert(offboundAlert);
        return this;
    }


    private void raiseAlertNotification(InvalidMoveEventArg e) {
        if(offboundAlert != null)
            offboundAlert.notifyAlert(e);
    }

    private void raisePositionPublication(PositionProvider position) {
        if(publisher != null)
            publisher.publishPosition(position);
    }

    public MownerController withResultPublisher(ResultPublisher publisher) {
        setPublisher(publisher);
        return this;
    }


    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private @Data class ExecutionBatch {

        private final Mowner mowner;
        private final Queue<Instruction> instructions;

    }

    public void Run() {
        for (ExecutionBatch e : executions) {
            runMowner(e.getMowner(), e.getInstructions());
        }
    }

    private void runMowner(@NonNull Controllable mowner, @NonNull Queue<Instruction> instructions) {

        for (Instruction step : instructions)
            executeStep(mowner, step);

        raisePositionPublication(((PositionProvider) mowner));
    }

    private void executeStep(Controllable mowner, Instruction step) {

        switch (step) {
            case A:
                mowner.moveForward();//this.garden); //by value ??
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

    private class IllegalMownerInstruction extends IllegalStateException{

        IllegalMownerInstruction(String message){
            super(message);
        }
    }
}
