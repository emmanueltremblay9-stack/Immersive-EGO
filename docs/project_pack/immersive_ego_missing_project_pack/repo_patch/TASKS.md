# Immersive EGO — TASKS

Status legend: `[ ]` not done, `[x]` done, `[!]` blocked/requires evidence.

## PHASE 0 — Audit and bootstrap

- [ ] Inspect repository layout and existing implementation.
- [ ] Create/verify `AGENTS.md`.
- [ ] Create/verify `LICENSE`, `THIRD_PARTY_NOTICES.md`, docs governance files.
- [ ] Resolve exact compatible versions for Minecraft 1.21.1, NeoForge 21.1.x, Apothic Attributes, Placebo, MariesLib, and optional Apotheosis.
- [ ] Record dependency evidence in `docs/DEPENDENCY_AUDIT.md`.
- [ ] Bootstrap/repair NeoForge project structure if needed.
- [ ] Configure Java 21.
- [ ] Add required dependencies and optional Apotheosis-safe setup.
- [ ] Add GitHub Actions CI.
- [ ] Run `./gradlew build` or `gradlew.bat build` and record the result.

## PHASE 1 — Core state, config, and attributes

- [ ] Create server/client config specs and dynamic TOML loader skeleton.
- [ ] Implement `EgoState` attachment, codec, clamping, and migration.
- [ ] Register capability attributes.
- [ ] Register display-only mirror attributes.
- [ ] Attach attributes to player without duplicate modifiers.
- [ ] Implement `/ego status` and `/ego debug state`.
- [ ] Add `en_us.json` and `fr_fr.json` entries.
- [ ] Verify EGO attributes display in Apothic Attributes.

## PHASE 2 — Simulation engine and synergy graph

- [ ] Implement immutable simulation frames.
- [ ] Implement scheduler bands.
- [ ] Implement curve types and edge evaluation.
- [ ] Implement TOML edge parser and validation.
- [ ] Implement cycle detection.
- [ ] Implement `/ego synergy list|trace|explain`.
- [ ] Add deterministic unit tests.

## PHASE 3 — Environment and thermoregulation

- [ ] Implement climate profiles.
- [ ] Implement seasons and sub-seasons.
- [ ] Implement exposure/shelter/wetness with cache.
- [ ] Implement original thermoregulation model.
- [ ] Wire display mirrors and config.

## PHASE 4 — Hydration and respiration

- [ ] Implement hydration demand.
- [ ] Recognize vanilla/modded consumables via tags/config/components.
- [ ] Implement water-quality precedence.
- [ ] Implement optional purification using existing items only.
- [ ] Implement contextual respiratory strain.

## PHASE 5 — Stamina and encumbrance

- [ ] Implement activity stamina costs.
- [ ] Implement regeneration and hysteresis.
- [ ] Implement item weight resolver.
- [ ] Implement inventory fingerprint/cache.
- [ ] Wire load effects through synergy graph.

## PHASE 6 — Sleep and fatigue

- [ ] Implement fatigue, sleep debt, circadian phase, sleep pressure.
- [ ] Implement sleep-quality calculation.
- [ ] Implement naps.
- [ ] Implement reachable-threat/shelter safety approximation.
- [ ] Integrate recovery budget and phantom rest rules.

## PHASE 7 — Nutrition and gut health

- [ ] Register MariesLib nutrition values.
- [ ] Implement food classification precedence.
- [ ] Implement dietary memory and bounded repetition effects.
- [ ] Implement meal quality.
- [ ] Implement gut health and contamination synergy.

## PHASE 8 — Psychology, pain, and recovery

- [ ] Implement stress/fear/mental stability/focus/comfort.
- [ ] Implement bounded pain/injury memory.
- [ ] Implement recovery budget allocation.
- [ ] Finalize EGO Load and Readiness calculations.

## PHASE 9 — Biome origins and acclimation

- [ ] Implement TOML origin profiles.
- [ ] Implement automatic birth-biome assignment.
- [ ] Implement origin commands.
- [ ] Implement persistent acclimation axes.
- [ ] Expose modifier sources in Apothic where supported.

## PHASE 10 — Apothic UX refinement and optional Apotheosis

- [ ] Add Apothic category/filter mixin only if still necessary.
- [ ] Document exact target version and safe failure.
- [ ] Implement optional Apotheosis examples if official schema/API is confirmed.
- [ ] Test with Apotheosis absent and present.

## PHASE 11 — Hardening and release preparation

- [ ] Run performance pass.
- [ ] Run dedicated-server smoke test.
- [ ] Complete docs and credits.
- [ ] Verify no forbidden assets or licenses.
- [ ] Generate release JAR.
- [ ] Ensure no unchecked release-blocking item remains.

## First Vertical Slice Acceptance

- [ ] Desertborn player assigned and visible.
- [ ] Hot/dry biome environment snapshot works.
- [ ] Heat strain increases hydration demand.
- [ ] Heavy inventory changes load ratio and stamina cost.
- [ ] Sprinting affects stamina/fatigue.
- [ ] Safe shelter/rest/sleep improves recovery budget.
- [ ] Balanced meal improves nutrition/mental recovery inputs.
- [ ] EGO Load and Readiness update.
- [ ] Apothic mirrors visible without duplicate modifiers.
- [ ] `/ego synergy trace` explains calculations.
- [ ] Save/reload persistence verified.
