package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public final class EgoWaterPurificationService {
    private EgoWaterPurificationService() {
    }

    public static Optional<EgoWaterPurificationMethod> methodForItemId(String itemId) {
        Objects.requireNonNull(itemId, "itemId");
        String normalized = itemId.toLowerCase(Locale.ROOT);
        for (EgoWaterPurificationMethod method : EgoWaterPurificationMethod.values()) {
            if (method.itemIds().contains(normalized)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

    public static EgoDrinkProfile purify(EgoDrinkProfile profile, EgoWaterPurificationMethod method) {
        Objects.requireNonNull(profile, "profile");
        Objects.requireNonNull(method, "method");
        EgoWaterQuality quality = profile.waterQuality().improve(method.qualitySteps());
        double contaminationRisk = EgoMath.clamp01(profile.contaminationRisk() - method.contaminationReduction());
        return new EgoDrinkProfile(
                profile.id() + "/" + method.name().toLowerCase(Locale.ROOT),
                quality,
                profile.hydrationGain(),
                profile.electrolyteSupport(),
                contaminationRisk);
    }
}
