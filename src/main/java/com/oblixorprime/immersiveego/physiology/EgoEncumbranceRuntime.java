package com.oblixorprime.immersiveego.physiology;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public final class EgoEncumbranceRuntime {
    private static final Map<UUID, EgoEncumbranceTier> TIERS = new HashMap<>();

    private EgoEncumbranceRuntime() {
    }

    public static synchronized Optional<EgoEncumbranceContext> context(
            UUID playerId,
            Optional<EgoInventoryWeightSnapshot> inventoryWeightSnapshot,
            int intervalTicks) {
        if (inventoryWeightSnapshot.isEmpty()) {
            return Optional.empty();
        }
        EgoInventoryWeightSnapshot snapshot = inventoryWeightSnapshot.orElseThrow();
        EgoEncumbranceTier previousTier = TIERS.getOrDefault(playerId, EgoEncumbranceTier.NORMAL);
        EgoEncumbranceTier tier = EgoEncumbranceHysteresis.resolve(
                previousTier,
                snapshot.loadRatio(),
                snapshot.overburdenPressure());
        TIERS.put(playerId, tier);
        return Optional.of(new EgoEncumbranceContext(
                tier,
                snapshot.loadRatio(),
                snapshot.overburdenPressure(),
                intervalTicks));
    }

    public static synchronized void remove(UUID playerId) {
        TIERS.remove(playerId);
    }
}
