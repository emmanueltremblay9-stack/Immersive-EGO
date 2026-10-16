package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public record EgoInjuryMemory(
        int schemaVersion,
        double recentInjury,
        double lingeringPain,
        double recoveryDebt) {
    public static final int CURRENT_SCHEMA_VERSION = 1;

    private static final EgoInjuryMemory DEFAULT = new EgoInjuryMemory(
            CURRENT_SCHEMA_VERSION,
            0.0D,
            0.0D,
            0.0D);

    public EgoInjuryMemory {
        schemaVersion = CURRENT_SCHEMA_VERSION;
        recentInjury = EgoMath.clamp01(recentInjury);
        lingeringPain = EgoMath.clamp01(lingeringPain);
        recoveryDebt = EgoMath.clamp01(recoveryDebt);
    }

    public static EgoInjuryMemory defaultState() {
        return DEFAULT;
    }

    public static boolean canReadSchemaVersion(int schemaVersion) {
        return schemaVersion >= 0 && schemaVersion <= CURRENT_SCHEMA_VERSION;
    }

    public boolean clear() {
        return recentInjury == 0.0D && lingeringPain == 0.0D && recoveryDebt == 0.0D;
    }

    public double painPressure(double currentHealthDeficit) {
        return EgoMath.clamp01(Math.max(
                currentHealthDeficit,
                lingeringPain * 0.75D + recoveryDebt * 0.25D));
    }
}
