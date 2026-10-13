package com.oblixorprime.immersiveego.psychology;

import com.oblixorprime.immersiveego.data.EgoState;
import com.oblixorprime.immersiveego.registry.EgoAttributes;
import com.oblixorprime.immersiveego.registry.EgoAttachments;
import com.oblixorprime.immersiveego.simulation.EgoMath;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;

public final class EgoPsychologyRuntime {
    private EgoPsychologyRuntime() {
    }

    public static Optional<EgoPsychologyContext> context(ServerPlayer player, EgoState state, int intervalTicks) {
        if (!EgoPsychologyModule.enabled()) {
            return Optional.empty();
        }

        double maxHealth = Math.max(1.0D, player.getMaxHealth());
        double healthDeficit = EgoMath.clamp01(1.0D - (player.getHealth() / maxHealth));
        EgoInjuryMemory memory = player.getData(EgoAttachments.EGO_INJURY_MEMORY.get());
        EgoInjuryMemory updatedMemory = EgoInjuryMemoryService.update(
                memory,
                new EgoInjuryMemoryContext(healthDeficit, intervalTicks, state.recoveryCapacity()));
        if (!updatedMemory.equals(memory)) {
            player.setData(EgoAttachments.EGO_INJURY_MEMORY.get(), updatedMemory);
        }

        double fearPressure = Math.max(healthDeficit * 0.80D, Math.max(state.egoLoad(), state.fatigue()) * 0.35D);
        double painPressure = updatedMemory.painPressure(healthDeficit);
        double stabilitySupport = 1.0D - Math.max(state.stress(), healthDeficit);
        double comfortSupport = 1.0D - Math.max(state.fatigue(), healthDeficit);

        return Optional.of(new EgoPsychologyContext(
                EgoMath.clamp01(fearPressure),
                painPressure,
                EgoMath.clamp01(stabilitySupport),
                EgoMath.clamp01(comfortSupport),
                intervalTicks,
                player.getAttributeValue(EgoAttributes.STRESS_RESILIENCE)));
    }
}
