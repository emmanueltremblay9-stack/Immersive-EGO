package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class EgoHydrationDemandCalculatorTest {
    @Test
    void calculatesBaselineAndStrainDrivenDemand() {
        EgoHydrationDemand demand = EgoHydrationDemandCalculator.calculate(new EgoHydrationDemandInput(
                0.5D,
                0.25D,
                0.4D,
                20,
                1.0D));

        assertEquals(0.00025D, demand.baseline(), 1.0E-12D);
        assertEquals(0.000375D, demand.thermal(), 1.0E-12D);
        assertEquals(0.000125D, demand.respiratory(), 1.0E-12D);
        assertEquals(0.0001D, demand.fatigue(), 1.0E-12D);
        assertEquals(1.0D, demand.efficiencyMultiplier(), 1.0E-12D);
        assertEquals(0.00085D, demand.total(), 1.0E-12D);
    }

    @Test
    void hydrationEfficiencyReducesDemand() {
        EgoHydrationDemand normal = EgoHydrationDemandCalculator.calculate(new EgoHydrationDemandInput(
                1.0D,
                1.0D,
                1.0D,
                20,
                1.0D));
        EgoHydrationDemand efficient = EgoHydrationDemandCalculator.calculate(new EgoHydrationDemandInput(
                1.0D,
                1.0D,
                1.0D,
                20,
                2.0D));

        assertEquals(normal.total() * 0.5D, efficient.total(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new EgoHydrationDemandInput(-0.01D, 0.0D, 0.0D, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoHydrationDemandInput(0.0D, Double.NaN, 0.0D, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoHydrationDemandInput(0.0D, 0.0D, 1.01D, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoHydrationDemandInput(0.0D, 0.0D, 0.0D, 20, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoHydrationDemand(0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -0.01D));
    }

    @Test
    void intervalIsClampedToSchedulerBounds() {
        EgoHydrationDemandContext context = new EgoHydrationDemandContext(0, 1.0D);

        assertEquals(1, context.intervalTicks());
        assertTrue(EgoHydrationDemandCalculator.calculate(new EgoHydrationDemandInput(
                0.0D,
                0.0D,
                0.0D,
                context.intervalTicks(),
                context.hydrationEfficiency())).total() > 0.0D);
    }
}
