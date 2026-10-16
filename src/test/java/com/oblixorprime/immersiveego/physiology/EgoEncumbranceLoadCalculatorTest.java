package com.oblixorprime.immersiveego.physiology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

final class EgoEncumbranceLoadCalculatorTest {
    @Test
    void hysteresisUsesSeparateEnterAndExitThresholds() {
        assertEquals(EgoEncumbranceTier.NORMAL, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.NORMAL,
                0.64D,
                0.0D));
        assertEquals(EgoEncumbranceTier.LOADED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.NORMAL,
                0.65D,
                0.0D));
        assertEquals(EgoEncumbranceTier.LOADED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.LOADED,
                0.56D,
                0.0D));
        assertEquals(EgoEncumbranceTier.NORMAL, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.LOADED,
                0.55D,
                0.0D));
        assertEquals(EgoEncumbranceTier.STRAINED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.LOADED,
                0.85D,
                0.0D));
        assertEquals(EgoEncumbranceTier.OVERBURDENED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.STRAINED,
                1.0D,
                0.0D));
        assertEquals(EgoEncumbranceTier.OVERBURDENED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.OVERBURDENED,
                0.96D,
                0.0D));
        assertEquals(EgoEncumbranceTier.STRAINED, EgoEncumbranceHysteresis.resolve(
                EgoEncumbranceTier.OVERBURDENED,
                0.95D,
                0.0D));
    }

    @Test
    void loadDeltaUsesTierPressureAndOverburdenPressure() {
        EgoEncumbranceLoadDelta strained = EgoEncumbranceLoadCalculator.calculate(new EgoEncumbranceContext(
                EgoEncumbranceTier.STRAINED,
                0.90D,
                0.0D,
                20));
        EgoEncumbranceLoadDelta overburdened = EgoEncumbranceLoadCalculator.calculate(new EgoEncumbranceContext(
                EgoEncumbranceTier.LOADED,
                1.0D,
                0.80D,
                20));

        assertEquals(0.0072D, strained.staminaCost(), 1.0E-12D);
        assertEquals(0.0024D, strained.fatigueGain(), 1.0E-12D);
        assertEquals(0.0096D, overburdened.staminaCost(), 1.0E-12D);
        assertEquals(0.0032D, overburdened.fatigueGain(), 1.0E-12D);
    }

    @Test
    void serviceAppliesLoadToStaminaAndFatigue() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.50D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.10D);

        EgoState updated = EgoEncumbranceService.applyLoad(state, new EgoEncumbranceContext(
                EgoEncumbranceTier.OVERBURDENED,
                1.0D,
                1.0D,
                20));

        assertEquals(0.488D, updated.stamina(), 1.0E-12D);
        assertEquals(0.104D, updated.fatigue(), 1.0E-12D);
    }

    @Test
    void serviceClampsAtNormalizedBounds() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STAMINA, 0.001D)
                .withDisplayValue(EgoAttributeCatalog.FATIGUE, 0.999D);

        EgoState updated = EgoEncumbranceService.applyLoad(state, new EgoEncumbranceContext(
                EgoEncumbranceTier.OVERBURDENED,
                1.0D,
                1.0D,
                1200));

        assertEquals(0.0D, updated.stamina(), 1.0E-12D);
        assertEquals(1.0D, updated.fatigue(), 1.0E-12D);
    }

    @Test
    void rejectsInvalidInputs() {
        assertThrows(NullPointerException.class, () -> EgoEncumbranceHysteresis.resolve(null, 0.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> EgoEncumbranceHysteresis.resolve(EgoEncumbranceTier.NORMAL, -0.01D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> EgoEncumbranceHysteresis.resolve(EgoEncumbranceTier.NORMAL, 0.0D, Double.NaN));
        assertThrows(NullPointerException.class, () -> new EgoEncumbranceContext(null, 0.0D, 0.0D, 20));
        assertThrows(IllegalArgumentException.class, () -> new EgoEncumbranceLoadDelta(-0.01D, 0.0D));
    }
}
