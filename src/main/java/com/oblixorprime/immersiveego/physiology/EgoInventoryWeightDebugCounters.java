package com.oblixorprime.immersiveego.physiology;

public record EgoInventoryWeightDebugCounters(
        long requests,
        long refreshes,
        long hits,
        long skips,
        long errors,
        long evictions) {
    public EgoInventoryWeightDebugCounters {
        requests = requireNonNegative("requests", requests);
        refreshes = requireNonNegative("refreshes", refreshes);
        hits = requireNonNegative("hits", hits);
        skips = requireNonNegative("skips", skips);
        errors = requireNonNegative("errors", errors);
        evictions = requireNonNegative("evictions", evictions);
    }

    public static EgoInventoryWeightDebugCounters empty() {
        return new EgoInventoryWeightDebugCounters(0L, 0L, 0L, 0L, 0L, 0L);
    }

    EgoInventoryWeightDebugCounters recordRequest() {
        return new EgoInventoryWeightDebugCounters(requests + 1L, refreshes, hits, skips, errors, evictions);
    }

    EgoInventoryWeightDebugCounters recordRefresh() {
        return new EgoInventoryWeightDebugCounters(requests, refreshes + 1L, hits, skips, errors, evictions);
    }

    EgoInventoryWeightDebugCounters recordHit() {
        return new EgoInventoryWeightDebugCounters(requests, refreshes, hits + 1L, skips, errors, evictions);
    }

    EgoInventoryWeightDebugCounters recordSkip() {
        return new EgoInventoryWeightDebugCounters(requests, refreshes, hits, skips + 1L, errors, evictions);
    }

    EgoInventoryWeightDebugCounters recordError() {
        return new EgoInventoryWeightDebugCounters(requests, refreshes, hits, skips, errors + 1L, evictions);
    }

    EgoInventoryWeightDebugCounters recordEvictions(long count) {
        return new EgoInventoryWeightDebugCounters(requests, refreshes, hits, skips, errors, evictions + count);
    }

    private static long requireNonNegative(String name, long value) {
        if (value < 0L) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
        return value;
    }
}
