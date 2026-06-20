# Immersive EGO Tasks

## Phase 0 - Audit and Bootstrap

- [x] Inspect repository state.
- [x] Create root AGENTS.md.
- [x] Initialize NeoForge 1.21.1 / Java 21 project.
- [x] Configure Gradle wrapper, build.gradle, settings.gradle, and gradle.properties.
- [x] Add MIT license and third-party notices.
- [x] Add project logo resource.
- [x] Resolve and pin initial NeoForge, Apothic Attributes, Placebo, MariesLib, and Apotheosis versions.
- [x] Document dependency and license audit.
- [x] Add GitHub Actions Java 21 build.
- [x] Make `.\gradlew.bat clean build` pass.
- [x] Add repeatable Windows Prism LAB install script.
- [x] Add repeatable Prism LAB runtime dependency installer.
- [x] Harden Prism LAB installers against mistyped missing target trees.
- [x] Install verified production jar into Prism LAB.

## Phase 1 - Core State, Config, and Attributes

- [x] Register all required SERVER config files.
- [x] Add dynamic TOML loader under serverconfig/immersive_ego/.
- [x] Implement EgoState attachment and versioned codec.
- [x] Implement state migration and clamping.
- [x] Register capability attributes.
- [x] Register display-only mirror attributes.
- [x] Attach attributes to players.
- [x] Implement mirror update service without duplicate modifiers.
- [x] Add `/ego status` and `/ego debug state`.
- [x] Expand en_us and fr_fr translations.
- [ ] Verify EGO attributes appear in Apothic Attributes.

## Phase 2 - Simulation Engine and Synergy Graph

- [x] Add initial pure Java normalized math helpers.
- [x] Add initial EGO Load aggregation helper.
- [x] Stabilize weighted mean aggregation for very large finite weights.
- [x] Add immutable simulation frames.
- [x] Add scheduler.
- [x] Connect scheduler frames to the first derived EGO Load and Readiness calculation.
- [x] Add configurable curves.
- [x] Add synergy edge parser and validator.
- [x] Add cycle detection.
- [x] Add `/ego synergy trace`.
- [x] Load synergy edges from dynamic TOML.
- [x] Add deterministic unit coverage for all curve and graph behavior.

## Phase 3 - Environment and Thermoregulation

- [ ] Implement climate profiles.
- [ ] Implement season, exposure, shelter, wetness, and thermal snapshot logic.
- [ ] Add environment cache and debug counters.
- [ ] Update display mirrors from environment-derived values.

## Phase 4 - Hydration and Respiration

- [ ] Implement hydration demand and drink recognition.
- [ ] Implement water quality and optional purification through existing items.
- [ ] Implement altitude, depth, and dimension respiratory strain.

## Phase 5 - Stamina and Encumbrance

- [ ] Implement activity costs and regeneration.
- [ ] Implement weight resolver and inventory cache.
- [ ] Add encumbrance hysteresis and stamina load synergies.

## Phase 6 - Sleep and Fatigue

- [ ] Implement circadian phase, fatigue, sleep pressure, naps, and sleep quality.
- [ ] Verify phantom rest qualification behavior for Minecraft 1.21.1.
- [ ] Keep multiplayer time behavior safe by default.

## Phase 7 - Nutrition and Gut Health

- [ ] Inspect MariesLib stable API and implement facade.
- [ ] Register six stable nutrition groups through MariesLib.
- [ ] Implement dietary memory, meal quality, and gut health.

## Phase 8 - Psychology, Pain, and Recovery

- [ ] Implement stress, fear, stability, focus, comfort, pain, injury memory, and recovery budget.
- [ ] Connect full derived gameplay systems through configured synergy edges.

## Phase 9 - Biome Origins and Acclimation

- [ ] Implement default origin profiles.
- [ ] Implement origin commands and automatic birth-biome assignment.
- [ ] Implement acclimation axes and persistence.

## Phase 10 - Apothic UX and Optional Apotheosis

- [ ] Add modifier source integration after inspecting the pinned Apothic API.
- [ ] Add a client-only Apothic filter mixin only if needed.
- [ ] Document and test optional Apotheosis absence/presence.

## Phase 11 - Hardening and Release

- [ ] Add GameTests and dedicated-server smoke tests.
- [ ] Complete documentation and CurseForge metadata.
- [ ] Complete source provenance and credits review.
- [x] Verify no forbidden assets or registrations exist.
