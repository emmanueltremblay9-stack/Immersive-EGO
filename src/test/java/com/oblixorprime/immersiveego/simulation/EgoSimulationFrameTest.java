package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoSimulationFrameTest {
    @Test
    void noOpFramePreservesInputState() {
        EgoState state = EgoState.defaultState();

        EgoSimulationFrame frame = EgoSimulationFrame.noOp(200L, 20, state);

        assertEquals(200L, frame.gameTime());
        assertEquals(20, frame.intervalTicks());
        assertEquals(state, frame.inputState());
        assertEquals(state, frame.outputState());
        assertFalse(frame.changed());
    }

    @Test
    void changedReportsOutputStateDifference() {
        EgoState state = EgoState.defaultState();
        EgoState changed = state.withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.25D);

        EgoSimulationFrame frame = new EgoSimulationFrame(1L, 20, state, changed);

        assertTrue(frame.changed());
    }

    @Test
    void rejectsInvalidFrameClockValues() {
        EgoState state = EgoState.defaultState();

        assertThrows(IllegalArgumentException.class, () -> EgoSimulationFrame.noOp(-1L, 20, state));
        assertThrows(IllegalArgumentException.class, () -> EgoSimulationFrame.noOp(1L, 0, state));
    }
}
