package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.simulation.EgoMath;

public final class EgoInjuryMemoryService {
    private EgoInjuryMemoryService() {
    }

    public static EgoInjuryMemory update(EgoInjuryMemory memory, EgoInjuryMemoryContext context) {
        double seconds = context.seconds();
        double recoveryRate = context.recoveryCapacity() * seconds * 0.006D;
        double recentInjury = Math.max(context.healthDeficit(), Math.max(0.0D, memory.recentInjury() - recoveryRate));

        double painTarget = Math.max(context.healthDeficit(), recentInjury * 0.75D);
        double painRate = context.healthDeficit() > memory.lingeringPain()
                ? seconds * 0.25D
                : seconds * (0.010D + context.recoveryCapacity() * 0.020D);
        double lingeringPain = moveToward(memory.lingeringPain(), painTarget, EgoMath.clamp01(painRate));

        double debtFloor = recentInjury * 0.50D;
        double recoveryDebt = Math.max(debtFloor, memory.recoveryDebt()) - context.recoveryCapacity() * seconds * 0.003D;
        recoveryDebt += context.healthDeficit() * seconds * 0.002D;

        return new EgoInjuryMemory(
                memory.schemaVersion(),
                recentInjury,
                lingeringPain,
                EgoMath.clamp01(recoveryDebt));
    }

    private static double moveToward(double current, double target, double rate) {
        return current + (target - current) * rate;
    }
}
