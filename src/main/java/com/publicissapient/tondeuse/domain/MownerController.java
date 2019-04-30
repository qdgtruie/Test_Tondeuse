package com.publicissapient.tondeuse.domain;

import com.publicissapient.tondeuse.domain.Configuration.Configuration;
import com.publicissapient.tondeuse.domain.Configuration.MownerConfiguration;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

@Slf4j
public class MownerController {

    private Queue<ExecutionBatch> executions = new LinkedList<ExecutionBatch>();


    public MownerController initialize(@NotNull Configuration config) {

        executions.clear();
        for (MownerConfiguration item : config.getMowners()) {
            MownerLocation initialLocation = item.getLocation();

            var mowner = Mowner.initialLocation( UUID.randomUUID(),initialLocation);
            var instructions = item.getInstructions().getInstructions();

            var e = new ExecutionBatch(mowner,instructions);
            executions.add(e);
        }
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

    private void runMowner(@NotNull Controllable mowner, @NotNull Queue<Instruction> instructions) {
        for (Instruction step : instructions) {
            executeStep(mowner, step);
        }
        mowner.reportOnDuty();

    }

    private void executeStep(Controllable mowner, Instruction step) {

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
        }

    }
}
