package com.oblixorprime.immersiveego.sleep;

import com.oblixorprime.immersiveego.registry.EgoAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;

public final class EgoSleepSampler {
    private EgoSleepSampler() {
    }

    public static EgoSleepContext sample(ServerPlayer player, int intervalTicks) {
        boolean sleeping = player.isSleeping();
        int sleepTicks = sleeping ? player.getSleepTimer() : 0;
        int timeSinceRestTicks = player.getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST));
        return new EgoSleepContext(
                player.serverLevel().getDayTime(),
                sleeping,
                sleepTicks,
                timeSinceRestTicks,
                intervalTicks,
                player.getAttributeValue(EgoAttributes.SLEEP_RECOVERY));
    }
}
