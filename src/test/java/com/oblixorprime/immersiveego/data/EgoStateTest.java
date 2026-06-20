package com.oblixorprime.immersiveego.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import org.junit.jupiter.api.Test;

class EgoStateTest {
    @Test
    void defaultStateUsesCurrentSchemaAndDisplayDefaults() {
        EgoState state = EgoState.defaultState();

        assertEquals(EgoState.CURRENT_SCHEMA_VERSION, state.schemaVersion());
        assertEquals(EgoAttributeCatalog.EGO_LOAD.defaultValue(), state.egoLoad());
        assertEquals(EgoAttributeCatalog.READINESS.defaultValue(), state.readiness());
        assertEquals(EgoAttributeCatalog.HYDRATION.defaultValue(), state.hydration());
        assertEquals(EgoAttributeCatalog.STAMINA.defaultValue(), state.stamina());
        assertEquals(EgoAttributeCatalog.FATIGUE.defaultValue(), state.fatigue());
        assertEquals(EgoAttributeCatalog.THERMAL_STRAIN.defaultValue(), state.thermalStrain());
        assertEquals(EgoAttributeCatalog.RESPIRATORY_STRAIN.defaultValue(), state.respiratoryStrain());
        assertEquals(EgoAttributeCatalog.NUTRITION_BALANCE.defaultValue(), state.nutritionBalance());
        assertEquals(EgoAttributeCatalog.STRESS.defaultValue(), state.stress());
        assertEquals(EgoAttributeCatalog.FOCUS.defaultValue(), state.focus());
        assertEquals(EgoAttributeCatalog.COMFORT.defaultValue(), state.comfort());
        assertEquals(EgoAttributeCatalog.RECOVERY_CAPACITY.defaultValue(), state.recoveryCapacity());
    }

    @Test
    void constructorMigratesVersionAndClampsValues() {
        EgoState state = new EgoState(
                0,
                -1.0D,
                2.0D,
                Double.NaN,
                Double.POSITIVE_INFINITY,
                0.25D,
                -10.0D,
                0.5D,
                2.0D,
                -0.5D,
                1.5D,
                0.4D,
                Double.NEGATIVE_INFINITY);

        assertEquals(EgoState.CURRENT_SCHEMA_VERSION, state.schemaVersion());
        assertEquals(0.0D, state.egoLoad());
        assertEquals(1.0D, state.readiness());
        assertEquals(0.0D, state.hydration());
        assertEquals(1.0D, state.stamina());
        assertEquals(0.25D, state.fatigue());
        assertEquals(0.0D, state.thermalStrain());
        assertEquals(0.5D, state.respiratoryStrain());
        assertEquals(1.0D, state.nutritionBalance());
        assertEquals(0.0D, state.stress());
        assertEquals(1.0D, state.focus());
        assertEquals(0.4D, state.comfort());
        assertEquals(0.0D, state.recoveryCapacity());
    }

    @Test
    void schemaReadGuardAllowsCurrentAndLegacyVersionsOnly() {
        assertTrue(EgoState.canReadSchemaVersion(0));
        assertTrue(EgoState.canReadSchemaVersion(EgoState.CURRENT_SCHEMA_VERSION));
        assertFalse(EgoState.canReadSchemaVersion(-1));
        assertFalse(EgoState.canReadSchemaVersion(EgoState.CURRENT_SCHEMA_VERSION + 1));
    }

    @Test
    void displayValueAccessUsesDisplayOnlyCatalogEntries() {
        EgoState state = EgoState.defaultState()
                .withDisplayValue(EgoAttributeCatalog.STRESS, 2.0D)
                .withDisplayValue(EgoAttributeCatalog.READINESS, -1.0D);

        assertEquals(1.0D, state.displayValue(EgoAttributeCatalog.STRESS));
        assertEquals(0.0D, state.displayValue(EgoAttributeCatalog.READINESS));
        assertThrows(IllegalArgumentException.class, () -> state.displayValue(EgoAttributeCatalog.STAMINA_CAPACITY));
    }
}
