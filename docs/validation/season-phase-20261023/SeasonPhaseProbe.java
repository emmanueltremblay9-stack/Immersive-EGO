package com.oblixorprime.immersiveego.environment;

import java.lang.management.ManagementFactory;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

/** Standalone micro-measurement of the real sampler class loaded from one JAR. */
public final class SeasonPhaseProbe {
    private static final int OPERATIONS = 2_000_000;
    private static final int BATCHES = 12;
    private static final long WARMUP_NANOS = 5_000_000_000L;
    private static final long[] INPUTS = inputs();
    private static volatile long sink;

    private static long[] inputs() {
        long[] values = new long[4096];
        Random random = new Random(0x5EA50L);
        for (int i = 0; i < values.length; i++) values[i] = random.nextLong();
        long[] edges = {Long.MIN_VALUE, Long.MIN_VALUE + 1, Long.MAX_VALUE, Long.MAX_VALUE - 1,
                -768001, -768000, -192001, -192000, -1, 0, 191999, 192000, 767999, 768000};
        System.arraycopy(edges, 0, values, 0, edges.length);
        return values;
    }

    private static long monotonic(long start, int count) {
        long checksum = 0;
        for (int i = 0; i < count; i++) {
            checksum += EgoEnvironmentSampler.seasonPhase(start + i).ordinal() + 1;
        }
        return checksum;
    }

    private static long mixed(int count) {
        long checksum = 0;
        for (int i = 0; i < count; i++) {
            checksum += EgoEnvironmentSampler.seasonPhase(INPUTS[i & (INPUTS.length - 1)]).ordinal() + 1;
        }
        return checksum;
    }

    private static long run(String mode, long start) {
        return mode.equals("monotonic_ticks") ? monotonic(start, OPERATIONS) : mixed(OPERATIONS);
    }

    public static void main(String[] args) throws Exception {
        try (PrintWriter out = new PrintWriter(Files.newBufferedWriter(Path.of(args[0])))) {
        var bean = (com.sun.management.ThreadMXBean) ManagementFactory.getThreadMXBean();
        if (!bean.isThreadAllocatedMemorySupported()) throw new IllegalStateException("Allocation counter unavailable");
        if (!bean.isThreadAllocatedMemoryEnabled()) bean.setThreadAllocatedMemoryEnabled(true);
        long thread = Thread.currentThread().threadId();
        var compilation = ManagementFactory.getCompilationMXBean();
        out.println("PROBE_SOURCE " + EgoEnvironmentSampler.class.getProtectionDomain().getCodeSource().getLocation());
        out.println("PROBE_META {\"operations\":" + OPERATIONS + ",\"batches\":" + BATCHES
                + ",\"warmup_target_seconds\":5,\"allocation_counter\":\"ThreadMXBean.getThreadAllocatedBytes\"}");
        for (String mode : new String[] {"monotonic_ticks", "mixed_extremes"}) {
            long began = System.nanoTime();
            long iterations = 0;
            do {
                sink = run(mode, 1_000_000L + iterations * OPERATIONS);
                iterations++;
            } while (System.nanoTime() - began < WARMUP_NANOS);
            out.println("PROBE_WARMUP {\"mode\":\"" + mode + "\",\"elapsed_nanos\":"
                    + (System.nanoTime() - began) + ",\"operations\":" + iterations * OPERATIONS
                    + ",\"compilation_time_ms\":" + compilation.getTotalCompilationTime() + "}");
            for (int batch = 0; batch < BATCHES; batch++) {
                long beforeBytes = bean.getThreadAllocatedBytes(thread);
                long beforeNanos = System.nanoTime();
                long checksum = run(mode, 1_000_000L + (long) batch * OPERATIONS);
                long elapsed = System.nanoTime() - beforeNanos;
                long allocated = bean.getThreadAllocatedBytes(thread) - beforeBytes;
                sink = checksum;
                out.println("PROBE_BATCH {\"mode\":\"" + mode + "\",\"batch\":" + batch
                        + ",\"operations\":" + OPERATIONS + ",\"elapsed_nanos\":" + elapsed
                        + ",\"allocated_bytes\":" + allocated + ",\"checksum\":" + checksum + "}");
            }
        }
        out.println("PROBE_COMPLETE " + sink);
        }
    }
}
