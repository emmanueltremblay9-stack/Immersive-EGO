package com.oblixorprime.immersiveego.origins;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Locale;

public record EgoOriginState(
        int schemaVersion,
        String originId,
        double heatAcclimation,
        double coldAcclimation,
        double altitudeAcclimation,
        double humidityAcclimation) {
    public static final int CURRENT_SCHEMA_VERSION = 1;
    public static final String UNASSIGNED = "unassigned";

    private static final EgoOriginState DEFAULT = new EgoOriginState(
            CURRENT_SCHEMA_VERSION,
            UNASSIGNED,
            0.0D,
            0.0D,
            0.0D,
            0.0D);

    public EgoOriginState {
        schemaVersion = CURRENT_SCHEMA_VERSION;
        originId = normalizeOriginId(originId);
        heatAcclimation = EgoMath.clamp01(heatAcclimation);
        coldAcclimation = EgoMath.clamp01(coldAcclimation);
        altitudeAcclimation = EgoMath.clamp01(altitudeAcclimation);
        humidityAcclimation = EgoMath.clamp01(humidityAcclimation);
    }

    public static EgoOriginState defaultState() {
        return DEFAULT;
    }

    public static boolean canReadSchemaVersion(int schemaVersion) {
        return schemaVersion >= 0 && schemaVersion <= CURRENT_SCHEMA_VERSION;
    }

    public boolean assigned() {
        return !UNASSIGNED.equals(originId);
    }

    public EgoOriginState withOrigin(EgoOriginProfile profile) {
        return new EgoOriginState(
                schemaVersion,
                profile.id(),
                Math.max(heatAcclimation, profile.heatAffinity() * 0.25D),
                Math.max(coldAcclimation, profile.coldAffinity() * 0.25D),
                Math.max(altitudeAcclimation, profile.altitudeAffinity() * 0.25D),
                Math.max(humidityAcclimation, profile.humidityAffinity() * 0.25D));
    }

    public EgoOriginState withAcclimation(
            double heatAcclimation,
            double coldAcclimation,
            double altitudeAcclimation,
            double humidityAcclimation) {
        return new EgoOriginState(
                schemaVersion,
                originId,
                heatAcclimation,
                coldAcclimation,
                altitudeAcclimation,
                humidityAcclimation);
    }

    private static String normalizeOriginId(String originId) {
        if (originId == null || originId.isBlank()) {
            return UNASSIGNED;
        }
        return originId.toLowerCase(Locale.ROOT);
    }
}
