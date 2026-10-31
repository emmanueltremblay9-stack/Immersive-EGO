package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoStaminaActivityCalculatorTest {
    @Test
    void restingRecoversStaminaAndClampsAtFull() {
        EgoStaminaActivityDelta delta = EgoStaminaActivityCalculator.calculate(new EgoStaminaActivityContext(
                0.0D,
                false,
                false,
                false,
                false,
                20,
                1.0D));
        EgoState nearlyFull = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.995D);

        EgoState recovered = EgoStaminaService.applyActivity(nearlyFull, new EgoStaminaActivityContext(
                0.0D,
                false,
                false,
                false,
                false,
                20,
                1.0D));

        assertEquals(0.012D, delta.recovery(), 1.0E-12D);
        assertEquals(0.012D, delta.netChange(), 1.0E-12D);
        assertEquals(1.0D, recovered.stamina(), 1.0E-12D);
    }

    @Test
    void sprintingConsumesStaminaAndCapacityReducesNormalizedCost() {
        EgoStaminaActivityDelta normal = EgoStaminaActivityCalculator.calculate(new EgoStaminaActivityContext(
                1.0D,
                true,
                false,
                false,
                false,
                20,
                1.0D));
        EgoStaminaActivityDelta highCapacity = EgoStaminaActivityCalculator.calculate(new EgoStaminaActivityContext(
                1.0D,
                true,
                false,
                false,
                false,
                20,
                2.0D));

        assertEquals(0.010D, normal.movementCost(), 1.0E-12D);
        assertEquals(0.030D, normal.sprintCost(), 1.0E-12D);
        assertEquals(0.040D, normal.totalCost(), 1.0E-12D);
        assertEquals(-0.040D, normal.netChange(), 1.0E-12D);
        assertEquals(0.020D, highCapacity.totalCost(), 1.0E-12D);
    }

    @Test
    void swimmingAndAirborneStateAddActivityCost() {
        EgoStaminaActivityDelta delta = EgoStaminaActivityCalculator.calculate(new EgoStaminaActivityContext(
                0.5D,
                false,
                true,
                true,
                false,
                20,
                1.0D));

        assertEquals(0.005D, delta.movementCost(), 1.0E-12D);
        assertEquals(0.018D, delta.swimmingCost(), 1.0E-12D);
        assertEquals(0.006D, delta.airborneCost(), 1.0E-12D);
        assertEquals(0.029D, delta.totalCost(), 1.0E-12D);
        assertEquals(-0.029D, delta.netChange(), 1.0E-12D);
    }

    @Test
    void lightMovementStillAllowsReducedRecovery() {
        EgoStaminaActivityDelta delta = EgoStaminaActivityCalculator.calculate(new EgoStaminaActivityContext(
                0.25D,
                false,
                false,
                false,
                true,
                20,
                1.0D));

        assertEquals(0.0025D, delta.movementCost(), 1.0E-12D);
        assertEquals(0.002D, delta.recovery(), 1.0E-12D);
        assertEquals(-0.0005D, delta.netChange(), 1.0E-12D);
    }

    @Test
    void serviceClampsAtZero() {
        EgoState exhausted = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.01D);

        EgoState afterSprint = EgoStaminaService.applyActivity(exhausted, new EgoStaminaActivityContext(
                1.0D,
                true,
                false,
                false,
                false,
                20,
                1.0D));

        assertEquals(0.0D, afterSprint.stamina(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> new EgoStaminaActivityContext(-0.01D, false, false, false, false, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoStaminaActivityContext(Double.NaN, false, false, false, false, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoStaminaActivityContext(1.01D, false, false, false, false, 20, 1.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoStaminaActivityContext(0.0D, false, false, false, false, 20, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new EgoStaminaActivityDelta(0.0D, -0.01D, 0.0D, 0.0D, 0.0D, 0.0D));
    }
}
