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
- [x] Install verified production jar into Prism LAB.

## Phase 1 - Core State, Config, and Attributes

- [ ] Register all required SERVER config files.
- [ ] Add dynamic TOML loader under serverconfig/immersive_ego/.
- [ ] Implement EgoState attachment and versioned codec.
- [ ] Implement state migration and clamping.
- [ ] Register capability attributes.
- [ ] Register display-only mirror attributes.
- [ ] Attach attributes to players.
- [ ] Implement mirror update service without duplicate modifiers.
- [ ] Add `/ego status` and `/ego debug state`.
- [ ] Expand en_us and fr_fr translations.
- [ ] Verify EGO attributes appear in Apothic Attributes.

## Phase 2 - Simulation Engine and Synergy Graph

- [x] Add initial pure Java normalized math helpers.
- [x] Add initial EGO Load aggregation helper.
- [ ] Add immutable simulation frames.
- [ ] Add scheduler.
- [ ] Add configurable curves.
- [ ] Add synergy edge parser and validator.
- [ ] Add cycle detection.
- [ ] Add `/ego synergy trace`.
- [ ] Add deterministic unit coverage for all curve and graph behavior.

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
- [ ] Add hysteresis and load synergies.

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
- [ ] Connect derived states through configured synergy edges.

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
- [ ] Verify no forbidden assets or registrations exist.
