# Season lookup experiment evidence

See [the report](../../SEASON_PHASE_EXPERIMENT.md) for scope, identities and limits. No game runtime was started or altered.

- `SeasonPhaseProbe.java`: the executed standalone probe. It directly calls `EgoEnvironmentSampler.seasonPhase` from the chosen actual JAR, consumes checksums and measures current-thread allocations outside each loop.
- `probe-summary.json`: four paired JVM comparisons, two input cases, warmup and timing/allocation summaries.
- `probe-observations.json`: every measured batch, warmup counts, loaded-JAR confirmation, input-result checksums and hashes of private raw logs.
- `jit-compilation-evidence.json`: tier-4 compilation observations before the corresponding measurements; normal JIT and escape analysis remain enabled.
- `test-summary.json` and `build-receipt.json`: fresh 193-JUnit result and both build attempts, including the corrected external classpath helper error.
- `candidate-identity.json`: both JAR hashes and the single changed archive entry.
- `baseline-identity.json` and `baseline-preservation.json`: original source/JAR/JFR identity and unchanged 191-file game inventory, source world and prior report.

The external runner, classpath-export helper and raw logs remain private. No new dependency such as JMH was installed. The raw XML/build/compilation logs remain private; their hashes and cleaned results are supplied here. The baseline and candidate are loaded in separate sequential JVMs, with `-Xms512m -Xmx512m -XX:ActiveProcessorCount=4 -XX:+PrintCompilation`, five seconds of warmup per case and 12 batches of 2,000,000 calls per case. The probe source is identical across variants. Each pair runs in alternating AB/BA order.

These files establish micro-measurement observations, not FPS, TPS, per-tick quantiles or an in-game speedup. Local GameTests and gameplay A/B remain NOT_RUN for this experiment; subsequent CI is recorded separately in the draft PR.

To reproduce a probe fork with the same dependency classpath resolved by this project (no new libraries):

```sh
javac -cp "$BASELINE_JAR:$RUNTIME_CLASSPATH" -d probe-classes SeasonPhaseProbe.java
java -Xms512m -Xmx512m -XX:ActiveProcessorCount=4 -XX:+PrintCompilation -cp "probe-classes:$CHOSEN_JAR:$RUNTIME_CLASSPATH" com.oblixorprime.immersiveego.environment.SeasonPhaseProbe results.jsonl > compilation.log
```

Choose the baseline or candidate JAR for each separate fork and retain the recorded AB/BA order. Verify the emitted `PROBE_SOURCE` against that JAR before aggregating measurements. Keep output files private until reviewed.
