package com.oblixorprime.immersiveego.physiology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributeCatalog;
import java.util.Objects;

public final class EgoRespirationService {
    private EgoRespirationService() {
    }

    public static EgoState applySample(EgoState state, EgoRespiratorySample sample) {
        Objects.requireNonNull(state, "state");
        return state.withDisplayValue(
                EgoAttributeCatalog.RESPIRATORY_STRAIN,
                EgoRespiratoryStrainCalculator.calculate(sample));
    }
}
