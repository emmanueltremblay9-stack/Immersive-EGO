# Season lookup experiment — 2026-10-23

**Prepared and micro-measured for draft review; not merged or installed.** The owner approved this single experiment. It only avoids the repeated `EgoSeasonPhase.values()` array copy. It does not establish a Minecraft performance gain or justify an MNN.

## Change and provenance

Review branch: `experiment/mnl-season-phase-table`, based on current main `9e38b30f3d20e6ff12013106200dae091a421896`. Its tree is identical to the original experiment base, documentation commit `db1f65dfc1a1639974f247c09a463201c0888789`; historical baseline evidence is preserved. The baseline client-tested source was `0b8b49c0d0a43424a73997e294d8fc3a2a9f4dc0`. The draft PR records its exact commit and subsequent CI result.

`EgoEnvironmentSampler` now retains a private, unexposed array of the four enum constants. The cycle length and season lookup use this array. The array is never mutated. `floorMod`, season duration, enum order and the current `level.getDayTime()` read remain unchanged. No biome, weather, sky, shelter, W or effective tolerance read is cached by this change.

| Artifact | Bytes | SHA256 |
|---|---:|---|
| Baseline alpha.62 JAR, preserved | 511707 | `bbe4be56ea2e9169cd35dbad10ea91d82baccb7111e77d80da4a04054cf6a163` |
| Experimental alpha.62 JAR, not installed | 511738 | `66781901cbc761f734a6fb597b3874d585cc5583d419e52277aa2b49a74bab1e` |

Comparing uncompressed entries finds **exactly one changed class**, `com/oblixorprime/immersiveego/environment/EgoEnvironmentSampler.class`; no added or removed entries. The version label is unchanged, so the hashes distinguish the binaries. The previous native baseline JFR remains private and unchanged: SHA256 `fe38c0f84ab169daef2b5762d1c17ecadc6d9ea8a5c34c850533e87ea7cdb622`.

## Correctness checks actually run

Fresh offline clean build: **193 JUnit tests, zero failures/errors/skips**, including six sampler tests. Four tests were added:

- Positive and negative season boundaries, including the adjacent ticks.
- Reordered times, rewinds, `Long.MIN_VALUE` and `Long.MAX_VALUE` and their neighbors.
- 10,000 deterministic full-range `long` inputs, checked against an independent BigInteger modulo oracle and explicit enum mapping.
- Mutation of a caller's `values()` copy cannot affect the private lookup.

Linux Gradle task invocation through the existing Java 21 wrapper (private helper path redacted):

```text
./gradlew --no-daemon --max-workers=3 --offline --no-build-cache -I <private-classpath-exporter> clean build writeSeasonProbeClasspath
```

The first attempt stopped before tests because the external classpath-export helper referenced the task's nonexistent `configurations` property. The helper was corrected to capture the project classpath during configuration. The second invocation completed successfully in 13 seconds, with nine tasks executed. Both logs are preserved. Existing deprecation warnings were not new failures. This is the Linux equivalent of the repository's required `gradlew.bat clean build`; no dependency was installed or downloaded.

The per-tick wetness ordering, one-tick water counterexample, pause/death/dimension semantics and dynamic thermal-attribute counterexamples remain unchanged in source and JAR entries. Existing GameTests were **not rerun** in this experiment; their historical results are not counted as new proof. No Minecraft client or server was launched.

## Controlled micro-measurement

The same compiled probe calls the real package-level `seasonPhase` method from the baseline or experimental JAR, in separate JVMs. Each loaded JAR's source and SHA256 are verified. There is no copied substitute implementation in the measurement.

Environment: existing Temurin 21.0.8+9, 4-CPU/16-GiB cloud quota; probe heap fixed at 512 MiB, `ActiveProcessorCount=4`. Eight sequential JVMs form four pairs in **AB, BA, AB, BA** order. Each case has **five seconds of warmup**, then **12 batches of 2,000,000 operations**. Cases use increasing ticks and mixed full-range inputs with boundary/extreme values. Checksums are consumed and match between variants. Normal tiered JIT, inlining and escape analysis remain enabled; there is no forced escape or compiler exclusion. Compilation logs confirm tier-4 compilation.

Allocations use `ThreadMXBean.getThreadAllocatedBytes` around each measured loop. Timing uses `System.nanoTime` outside the loop. Reported time includes loop, input selection and checksum overhead. The probe and results are retained in [validation evidence](validation/season-phase-20261023/README.md).

| Case | Baseline bytes/call | Variant bytes/call | Baseline ns/call | Variant ns/call |
|---|---:|---:|---:|---:|
| Increasing ticks | 32.000 | 0.000 | 3.264 | 2.300 |
| Mixed/extreme times | 32.000 | 0.000 | 3.608 | 2.423 |

Times are medians of four fork medians, not per-tick Minecraft statistics. Every measured batch reports 32 bytes/call for the baseline and zero for the variant. Candidate timing is lower in all four pairs for both cases. Paired differences span 0.888–1.860 ns/call for increasing ticks and 1.018–1.330 ns/call for mixed inputs.

**The JIT did not eliminate the baseline allocation in this probe.** Avoiding it is observed under these conditions, rather than inferred solely from the earlier sampled `Object.clone` stack. This custom JDK probe is not JMH; shared CPU noise, GC and only four pairs limit timing conclusions. No allocation rate, CPU saving, FPS, TPS or p95 improvement in actual gameplay is established. Different inlining/caller contexts can change JIT behavior.

## Disposition

The narrow variant passes its equivalence tests and reduces allocation in this micro-measurement. It remains an experimental local patch and separately identified JAR. Baseline runtime, its 191 files and prior evidence are preserved; no installed mod or world changed. This local measurement did not run a live A/B, new JFR or GameTests. Subsequent CI is reported separately in the draft PR. No merge, release, installation or MNN was performed. Integration and any in-game performance claim require a separate decision and relevant evidence.
