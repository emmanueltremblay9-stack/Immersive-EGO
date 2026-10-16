package com.oblixorprime.immersiveego.environment;

import com.oblixorprime.immersiveego.simulation.EgoMath;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

public final class EgoEnvironmentSampler {
    private static final long MINECRAFT_DAY_TICKS = 24000L;
    private static final long SEASON_LENGTH_DAYS = 8L;
    private static final long SEASON_LENGTH_TICKS = MINECRAFT_DAY_TICKS * SEASON_LENGTH_DAYS;
    private static final long SEASON_CYCLE_TICKS = SEASON_LENGTH_TICKS * EgoSeasonPhase.values().length;

    private EgoEnvironmentSampler() {
    }

    public static EgoExposureSample sample(ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        BlockPos position = player.blockPosition();
        boolean hasSkyLight = level.dimensionType().hasSkyLight();
        boolean canSeeSky = hasSkyLight && level.canSeeSky(position);
        double skyExposure = skyExposure(hasSkyLight, canSeeSky);
        double shelter = shelter(hasSkyLight, canSeeSky);
        double wetness = wetness(player, level, position);

        return new EgoExposureSample(
                selectClimateProfile(level, position),
                seasonPhase(level.getDayTime()),
                skyExposure,
                shelter,
                wetness);
    }

    static EgoSeasonPhase seasonPhase(long dayTime) {
        long cycleTick = Math.floorMod(dayTime, SEASON_CYCLE_TICKS);
        int seasonIndex = (int) (cycleTick / SEASON_LENGTH_TICKS);
        return EgoSeasonPhase.values()[seasonIndex];
    }

    static EgoClimateProfile selectClimateProfile(float temperature, float downfall, boolean snow) {
        double normalizedTemperature = EgoMath.clamp01(temperature);
        double normalizedDownfall = EgoMath.clamp01(downfall);
        if (snow || normalizedTemperature <= 0.25D) {
            return profile(normalizedDownfall >= 0.45D ? EgoClimateProfileCatalog.COLD_WET : EgoClimateProfileCatalog.COLD_DRY);
        }
        if (normalizedTemperature >= 0.85D) {
            return profile(normalizedDownfall >= 0.55D ? EgoClimateProfileCatalog.HOT_HUMID : EgoClimateProfileCatalog.HOT_DRY);
        }
        return profile(EgoClimateProfileCatalog.TEMPERATE);
    }

    private static EgoClimateProfile selectClimateProfile(ServerLevel level, BlockPos position) {
        Biome biome = level.getBiome(position).value();
        Biome.ClimateSettings climate = biome.getModifiedClimateSettings();
        boolean snow = biome.getPrecipitationAt(position) == Biome.Precipitation.SNOW;
        return selectClimateProfile(climate.temperature(), climate.downfall(), snow);
    }

    private static double skyExposure(boolean hasSkyLight, boolean canSeeSky) {
        if (!hasSkyLight) {
            return 0.05D;
        }
        return canSeeSky ? 1.0D : 0.20D;
    }

    private static double shelter(boolean hasSkyLight, boolean canSeeSky) {
        if (!hasSkyLight) {
            return 0.60D;
        }
        return canSeeSky ? 0.0D : 0.85D;
    }

    private static double wetness(ServerPlayer player, ServerLevel level, BlockPos position) {
        if (player.isInWaterRainOrBubble()) {
            return 1.0D;
        }
        return level.isRainingAt(position) ? 0.75D : 0.0D;
    }

    private static EgoClimateProfile profile(String id) {
        return EgoClimateProfileCatalog.defaultById(id).orElseThrow(
                () -> new IllegalStateException("Missing default climate profile: " + id));
    }
}
