package com.oblixorprime.immersiveego.simulation;

import com.oblixorprime.immersiveego.data.EgoState;
import java.util.Objects;

public record EgoSimulationFrame(
        long gameTime,
        int intervalTicks,
        EgoState inputState,
        EgoState outputState) {
    public EgoSimulationFrame {
        if (gameTime < 0L) {
            throw new IllegalArgumentException("gameTime cannot be negative");
        }
        if (intervalTicks < 1) {
            throw new IllegalArgumentException("intervalTicks must be positive");
        }
        inputState = Objects.requireNonNull(inputState, "inputState");
        outputState = Objects.requireNonNull(outputState, "outputState");
    }

    public static EgoSimulationFrame noOp(long gameTime, int intervalTicks, EgoState state) {
        return new EgoSimulationFrame(gameTime, intervalTicks, state, state);
    }

    public boolean changed() {
        return !this.inputState.equals(this.outputState);
    }
}
