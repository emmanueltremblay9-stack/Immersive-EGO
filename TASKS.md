# Immersive EGO Tasks

## PR2 — persistent wetness and drying (cloud, 2026-10-16)

- [x] Reconcile merged PR1/main and preserve alpha.61 proofs.
- [x] Implement one server-owned versioned wetness attachment, pure model and per-tick observation/write path.
- [x] Preserve pause/death/dimension/logout semantics; replace only the thermal liquid branch.
- [x] Add read-only debug, FR/EN notices, hysteresis and opt-out.
- [x] Add unit and server integration/GameTest matrix; include GameTests in CI.
- [x] Finalize local runtime/validation evidence: [qualification](docs/WETNESS_VALIDATION.md).
- [ ] Create/confirm draft PR and same-commit remote CI (GitHub API 403 remains a possible external blocker).
- [ ] Balance validation / large-player-count profiling (not claimed by the bounded PR2 implementation).

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
- [x] Import complementary missing project pack prompts, branding, templates, and planning references without overwriting live alpha files.

## Phase 1 - Core State, Config, and Attributes

- [x] Register all required SERVER config files.
- [x] Add dynamic TOML loader under serverconfig/immersive_ego/.
- [x] Harden dynamic scheduler interval repair against huge integer config values.
- [x] Implement EgoState attachment and versioned codec.
- [x] Implement state migration and clamping.
- [x] Register capability attributes.
- [x] Register display-only mirror attributes.
- [x] Attach attributes to players.
- [x] Keep capability multiplier minimums aligned with the runtime 0.25 multiplier floor.
- [x] Implement mirror update service without duplicate modifiers.
- [x] Honor configured display mirror update epsilon.
- [x] Add `/ego status` and `/ego debug state`.
- [x] Expand en_us and fr_fr translations.
- [x] Verify EGO attributes appear in Apothic Attributes.
  - 2026-06-22 live LAB proof: alpha.56 opened the Apothic Attributes `Attributes` panel from the survival inventory and showed EGO rows including `EGO Acclimation Gain`, `EGO Hydration Efficiency`, and `EGO Load`; proof screenshot: `docs/proof/apothic-live-ui-alpha56-20260622-060914.png`.

## Phase 2 - Simulation Engine and Synergy Graph

- [x] Add initial pure Java normalized math helpers.
- [x] Add initial EGO Load aggregation helper.
- [x] Stabilize weighted mean aggregation for very large finite weights.
- [x] Reject non-finite weighted mean values before aggregation.
- [x] Add immutable simulation frames.
- [x] Add scheduler.
- [x] Connect scheduler frames to the first derived EGO Load and Readiness calculation.
- [x] Add configurable curves.
- [x] Add synergy edge parser and validator.
- [x] Add cycle detection.
- [x] Add `/ego synergy trace`.
- [x] Load synergy edges from dynamic TOML.
- [x] Reject non-finite external source values in synergy evaluation.
- [x] Add deterministic unit coverage for all curve and graph behavior.

## Phase 3 - Environment and Thermoregulation

- [x] Implement climate profiles.
- [x] Implement season, exposure, shelter, wetness, and thermal snapshot logic.
- [x] Add environment cache and debug counters.
- [x] Update display mirrors from environment-derived values.

## Phase 4 - Hydration and Respiration

- [x] Implement hydration demand and drink recognition.
- [x] Implement water quality and optional purification through existing items.
- [x] Implement altitude, depth, and dimension respiratory strain.

## Phase 5 - Stamina and Encumbrance

- [x] Implement activity costs and regeneration.
- [x] Implement weight resolver and inventory cache.
- [x] Add encumbrance hysteresis and stamina load synergies.

## Phase 6 - Sleep and Fatigue

- [x] Implement circadian phase, fatigue, sleep pressure, naps, and sleep quality.
- [x] Verify phantom rest qualification behavior for Minecraft 1.21.1.
- [x] Keep multiplayer time behavior safe by default.

## Phase 7 - Nutrition and Gut Health

- [x] Inspect MariesLib stable API and implement facade.
- [x] Register six stable nutrition groups through MariesLib.
- [x] Implement dietary memory, meal quality, and gut health.
- [x] Guard against bundled scanner-spec resource drift and sub-second nutrition over-application.
- [x] Treat non-finite MariesLib dietary memory levels as missing.

## Phase 8 - Psychology, Pain, and Recovery

- [x] Implement default-off stress, fear, pain-pressure, stability, focus, comfort, and recovery budget foundation.
- [x] Add persistent injury memory.
- [ ] Tune pain/recovery through live playtest evidence.
- [ ] Connect full derived gameplay systems through configured synergy edges.

## Phase 9 - Biome Origins and Acclimation

- [x] Implement default origin profiles.
- [x] Implement origin debug readback and automatic birth-biome assignment when the origins module is enabled.
- [x] Implement acclimation axes and persistence.

## Phase 10 - Apothic UX and Optional Apotheosis

- [ ] Add modifier source integration after inspecting the pinned Apothic API.
- [ ] Add a client-only Apothic filter mixin only if needed.
- [x] Document and test optional Apotheosis absence.
- [ ] Test optional Apotheosis presence.

## Phase 11 - Hardening and Release

- [x] Add command permission GameTest guard.
- [x] Add server-side source boundary guard against client-only class references.
- [x] Add repeatable dedicated-server smoke test.
- [x] Add hydration, sleep, and nutrition runtime progression GameTests.
- [x] Add attribute catalog, stamina, sleep debt, and nap runtime GameTests.
- [x] Add attachment mutation readback and respiratory dimension-mapping runtime GameTests.
- [x] Add malformed dynamic config fallback unit and runtime GameTests.
- [x] Add attachment player NBT save/load round-trip runtime GameTest.
- [x] Add death-respawn clone-event attachment policy runtime GameTest.
- [x] Add live food-consumption event runtime GameTest for MariesLib memory and nutrition state.
- [x] Add psychology runtime GameTest for live injury memory and server-authoritative state pressure.
- [x] Complete full project review and mirror it into Notion.
- [x] Refresh the standard verification workflow and reinstall the alpha.60 LAB test build.
- [ ] Add remaining runtime progression GameTests.
- [ ] Run live Desertborn hot-biome vertical-slice client proof.
  - 2026-06-22 attempt: LAB alpha.56 launch was verified, module gates and world scheduler were temporarily enabled then restored default-off, and a temporary datapack/quick-launch probe was tried and then removed. Gate remains open because the Codex desktop session could not focus/control the live Minecraft window, and no desert biome / arid origin / hot-dry environment readback was captured.
- [ ] Complete documentation and CurseForge metadata.
- [x] Complete source provenance and credits review.
- [x] Verify no forbidden assets or registrations exist.

## 2026-10-13 preservation snapshot

The local alpha.61 worktree was preserved on a separate draft-PR branch. See [snapshot provenance and validation](docs/LOCAL_ALPHA61_SNAPSHOT.md) for included files, privacy adaptations and the fresh build limitation. This entry records preservation only; it does not close runtime proof gates.
