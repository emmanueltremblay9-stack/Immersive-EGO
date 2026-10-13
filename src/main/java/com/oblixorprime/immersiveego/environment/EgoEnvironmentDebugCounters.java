package com.oblixorprime.immersiveego.environment;

public record EgoEnvironmentDebugCounters(
        long requests,
        long refreshes,
        long hits,
        long skips,
        long errors,
        long evictions) {
    public EgoEnvironmentDebugCounters {
        requireNonNegative("requests", requests);
        requireNonNegative("refreshes", refreshes);
        requireNonNegative("hits", hits);
        requireNonNegative("skips", skips);
        requireNonNegative("errors", errors);
        requireNonNegative("evictions", evictions);
    }

    public static EgoEnvironmentDebugCounters empty() {
        return new EgoEnvironmentDebugCounters(0L, 0L, 0L, 0L, 0L, 0L);
    }

    public EgoEnvironmentDebugCounters recordRequest() {
        return new EgoEnvironmentDebugCounters(requests + 1L, refreshes, hits, skips, errors, evictions);
    }

    public EgoEnvironmentDebugCounters recordRefresh() {
        return new EgoEnvironmentDebugCounters(requests, refreshes + 1L, hits, skips, errors, evictions);
    }

    public EgoEnvironmentDebugCounters recordHit() {
        return new EgoEnvironmentDebugCounters(requests, refreshes, hits + 1L, skips, errors, evictions);
    }

    public EgoEnvironmentDebugCounters recordSkip() {
        return new EgoEnvironmentDebugCounters(requests, refreshes, hits, skips + 1L, errors, evictions);
    }

    public EgoEnvironmentDebugCounters recordError() {
        return new EgoEnvironmentDebugCounters(requests, refreshes, hits, skips, errors + 1L, evictions);
    }

    public EgoEnvironmentDebugCounters recordEvictions(int count) {
        if (count <= 0) {
            return this;
        }
        return new EgoEnvironmentDebugCounters(requests, refreshes, hits, skips, errors, evictions + count);
    }

    private static void requireNonNegative(String name, long value) {
        if (value < 0L) {
            throw new IllegalArgumentException(name + " must be non-negative");
        }
    }
}
