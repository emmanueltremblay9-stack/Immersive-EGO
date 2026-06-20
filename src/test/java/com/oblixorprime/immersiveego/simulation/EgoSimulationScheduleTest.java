package com.oblixorprime.immersiveego.simulation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.config.EgoDynamicServerConfig;
import org.junit.jupiter.api.Test;

final class EgoSimulationScheduleTest {
    @Test
    void disabledScheduleNeverRuns() {
        EgoSimulationSchedule schedule = new EgoSimulationSchedule(false, 20);

        assertFalse(schedule.shouldRun(0L));
        assertFalse(schedule.shouldRun(20L));
    }

    @Test
    void enabledScheduleRunsOnIntervalBoundariesOnly() {
        EgoSimulationSchedule schedule = new EgoSimulationSchedule(true, 20);

        assertTrue(schedule.shouldRun(0L));
        assertFalse(schedule.shouldRun(19L));
        assertTrue(schedule.shouldRun(20L));
        assertFalse(schedule.shouldRun(21L));
    }

    @Test
    void scheduleIntervalIsClamped() {
        EgoSimulationSchedule schedule = new EgoSimulationSchedule(true, -10);

        assertTrue(schedule.shouldRun(1L));
    }

    @Test
    void scheduleCanBeCreatedFromDynamicConfig() {
        EgoDynamicServerConfig config = new EgoDynamicServerConfig(true, 40, 0.5D);
        EgoSimulationSchedule schedule = EgoSimulationSchedule.from(config);

        assertFalse(schedule.shouldRun(39L));
        assertTrue(schedule.shouldRun(40L));
    }
}
