# Thermal tolerance — approved option B

## Decision and scope

On 2026-10-17 at 10:44 UTC, the owner selected **B** in direct response to the A/B/C choice (`Sentinel_6f6aeebbfa408191a30f3b26633ecbed`). The delegated mandate explicitly authorizes dividing the snapshot's hot and cold pressures by thermal tolerance, including hydration and acclimation consumers. This resolves the previously undefined qualitative capability; it does not reinterpret the original failed qualification as a pass or attribute the gap to PR2.

The implementation is based on merged main `3f7734cc4bbd6802c12c09b5990544b86faac47a`, on `fix/thermal-tolerance-snapshot`. Dependencies, configuration defaults, attribute range/default, version (`0.1.0-alpha.62`), origin coefficients, scheduler ordering and other capability parameters remain unchanged. A new JAR is identified by its exact commit and SHA-256, not by the shared version string. Shelters and equipment remain subsequent work.

## Formula and ownership

All pressures are dimensionless, normalized to `[0,1]`. `T` is the server player's **effective** `immersive_ego:thermal_tolerance`, including attribute modifiers. The registered range remains `[0.25,4]`, default `1`. The pure calculation rejects nonfinite/nonpositive tolerance and bounds other positive inputs to that range, matching the existing multiplier convention.

1. Compute `rawHeatPressure` and `rawColdPressure` with the preexisting climate, season, exposure, shelter and wetness formula, including its original `[0,1]` clamp.
2. Compute `heatPressure = clamp01(rawHeatPressure / T)` and `coldPressure = clamp01(rawColdPressure / T)`.
3. Compute `thermalStrain = max(heatPressure, coldPressure)` and the existing mode/deadband from those **adjusted** pressures.

`T=1` preserves the previous result. Values below one increase pressure up to one; values above one reduce it. The original clamp occurs **before division**, and another clamp bounds amplified results. If both adjusted pressures saturate equally, the mode can be neutral despite a high strain; mode denotes the existing heat/cold difference, not an absence of pressure.

Example: open `hot_dry` in spring, W=0 has raw heat `0.870` and raw cold `0.195`. At T=2, adjusted heat/cold are `0.435`/`0.0975`; at T=0.25 they are `1`/`0.78`. These examples precede origin relief.

The raw exposure cache is not a cache of the player's tolerance. Each service projection (`refreshPlayer`, `latest`, `debugSnapshot`) reads current W and current effective T, so an attribute change is visible even when the climate/exposure sample is cached. No new persistent state or client-authoritative write is introduced. Pure legacy overloads and the six-argument snapshot constructor retain neutral tolerance for callers without a player.

## Consumers and ordering

- Environment writes adjusted snapshot strain into the frame.
- Hydration consumes that strain **before origin relief**, at the existing thermal coefficient `0.00075` per simulation second. Baseline, respiration, fatigue and hydration efficiency are unchanged. This affects heat and cold because the existing demand uses maximum thermal strain.
- Origins use adjusted heat/cold pressures for their respective acclimation targets. The existing `0.70 × exposure + 0.30 × affinity` target and gain/interval step remain unchanged. Higher tolerance therefore reduces thermal exposure targets; it does not increase acclimation gain. Humidity and altitude axes keep their existing inputs.
- Sleep and derived load/readiness see the thermal state through their existing frame positions. The original origin relief `0.08 × max(heatAcclimation,coldAcclimation)` is applied later, once, with no change of order or coefficient.

## PR2 invariants

The only wetness-to-thermal branch remains `max(active W, climateWetnessPressure × unshelteredExposure)`. T divides the resulting **thermal pressures**, never W, its acquisition/drying rates, the climatic wetness floor or exposure itself. There is no additional wetness penalty or sum. Equipment has no new direct input.

W remains a separate server attachment `[0,1]`, distinct from hydration. Survival/Adventure, Creative/Spectator pauses, all disable gates, no offline catch-up, death reset, dimensions/restart persistence and read-only diagnostics retain PR2's behavior. This change does not extend PR2's W-specific mode gates into a new global gate for every EGO system.

## Diagnostics

`/ego debug environment` now prints a separate **raw heat / raw cold / effective tolerance** line, followed by explicitly **adjusted** heat/cold/mode/strain. Wetness and exposure fields are unscaled. `/ego debug wetness` uses the same effective tolerance for its fresh preview, labels its pressure as wetness pressure and its strain as tolerance-adjusted. Both commands identify the preview as preceding origin relief; the persistent state's strain may therefore differ. These reads never advance W, hydration or acclimation.

FR/EN attribute descriptions describe the now-active behavior. No new end-user gauge, gameplay effect, configuration key or dependency is added.

## Qualification

The targeted suite covers hot/cold T=0.25/0.5/1/2/4, golden pressures, both clamps, invalid input, legacy neutrality, wetness's single maximum branch, hydration and all acclimation axes. Server GameTests cover actual attribute reads across cached exposure, player isolation, diagnostic values/serialization/nonmutation and the real scheduler event through hydration/acclimation while W remains equal.

Fresh build, complete JUnit/GameTest results, independent review, packaged client identity and scenario results are recorded in `THERMAL_TOLERANCE_VALIDATION.md` when observed. Historical qualification files are preserved as historical evidence.
