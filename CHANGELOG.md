# Changelog

## 0.1.0-alpha.56

- Added runtime GameTest coverage for the psychology module path that records live health-deficit injury memory and applies pain/fear pressure to server-authoritative EGO state.
- Imported complementary missing project pack material: branding placeholders, Codex prompts, config templates, project templates, missing planning docs, and pack provenance notes without overwriting live alpha files.
- The verified Prism LAB jar is `immersive_ego-0.1.0-alpha.56.jar`.

## 0.1.0-alpha.55

- Added runtime GameTest coverage for the live food-consumption event path with the nutrition module enabled.
- The new GameTest posts `LivingEntityUseItemEvent.Finish` for vanilla bread and verifies MariesLib dietary memory plus persisted `nutrition_balance` update under the Minecraft/NeoForge classpath.

## 0.1.0-alpha.54

- Added runtime GameTest coverage for EGO attachment copy behavior on NeoForge player clone events fired for death respawn.
- The new GameTest posts `PlayerEvent.Clone` with `wasDeath=true` and verifies `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` survive the clone through NeoForge's `copyOnDeath` attachment path.

## 0.1.0-alpha.53

- Added runtime GameTest coverage for player NBT save/load round-tripping of `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` attachments.
- The new GameTest asserts the serialized NeoForge attachment keys and reloads a fresh mock player from saved NBT under the Minecraft/NeoForge classpath.

## 0.1.0-alpha.52

- Hardened dynamic `simulation.toml` and `synergies.toml` loading so malformed TOML parser failures reset only the affected Immersive EGO dynamic file to safe defaults instead of crashing startup.
- Added unit and runtime GameTest coverage for malformed dynamic config repair under the Minecraft/NeoForge classpath.

## 0.1.0-alpha.51

- Added runtime GameTests for EGO state/origin/injury attachment mutation readback and respiratory dimension mapping into server-authoritative `respiratory_strain`.
- Exposed the respiratory dimension classifier for runtime verification and strengthened the dedicated-server smoke failure scan for client-only class-load signatures.

## 0.1.0-alpha.50

- Added runtime GameTests for EGO attribute catalog uniqueness/bounds/sentiment, stamina consume/recover progression, awake-night sleep debt progression, and day-nap recovery behavior.

## 0.1.0-alpha.49

- Added runtime GameTests for server-authoritative hydration demand progression, sleep recovery progression, and vanilla-food nutrition balance progression.
- The new GameTests exercise persisted `EGO_STATE` updates under the Minecraft runtime classpath with mock server players.

## 0.1.0-alpha.48

- Added `scripts/smoke-dedicated-server.ps1` for a repeatable bounded dedicated-server startup smoke.
- The smoke writes `build/dedicated-server-smoke-report.json` plus stdout/stderr logs and requires explicit `-AcceptEula` when the local run EULA file is missing.
- The smoke verifies Immersive EGO bootstrap, MariesLib scanner config loading, dynamic simulation config loading, dynamic synergy config loading, and vanilla dedicated-server startup completion.

## 0.1.0-alpha.47

- Added runtime GameTest coverage that proves `/ego debug` and `/ego synergy` require permission level 2.
- Added project boundary guards against accidental client-only class references in main server-loaded sources.
- Added project boundary guards that keep Apotheosis optional in mod metadata and prevent hard source references to optional Apotheosis APIs.

## 0.1.0-alpha.46

- Added persistent `EgoInjuryMemory` attachment for recent injury, lingering pain, and recovery debt.
- Wired injury memory into the default-off psychology runtime so pain pressure can persist after immediate health recovery.
- Added pure unit coverage for injury memory recording, lingering pain, recovery decay, and context validation.
- Added runtime GameTest coverage for the injury-memory attachment default state.

## 0.1.0-alpha.45

- Added default-off psychology and recovery-budget foundation for fear pressure, pain pressure, stability support, focus, comfort, stress, and recovery capacity.
- Added persistent `EgoOriginState` attachment with default origin profiles and heat, cold, altitude, and humidity acclimation axes.
- Wired optional psychology and origins contexts into scheduler frame calculation before derived EGO Load/Readiness.
- Added `/ego debug origin` readback for persisted origin and acclimation state.
- Added JUnit coverage for psychology/recovery and origin/acclimation behavior.
- Added runtime GameTest coverage for the origin attachment default and updated command registration coverage.

## 0.1.0-alpha.44

- Fixed display mirror updates so configured mirror update epsilon values are honored instead of using a hardcoded near-zero threshold.
- Added regression coverage for mirror-update threshold decisions.

## 0.1.0-alpha.43

- Fixed dynamic server config repair so huge integer scheduler intervals clamp before narrowing to `int`.
- Added regression coverage for `Long.MAX_VALUE`-sized `scheduler.intervalTicks` values in `serverconfig/immersive_ego/simulation.toml`.

## 0.1.0-alpha.42

- Removed the bundled MariesLib scanner-spec resource so the runtime path stays source-backed by `config/immersive_ego/scanner_spec.json`.
- Added a project boundary guard against reintroducing the bundled scanner-spec path.
- Fixed nutrition balance rate limiting so sub-second scheduler intervals scale by actual elapsed ticks instead of applying a full one-second change.
- Added regression coverage for one-tick nutrition balance updates.

## 0.1.0-alpha.41

- Hardened the MariesLib scanner-spec startup path by creating the expected `config/immersive_ego/scanner_spec.json` file before context registration when it is missing.
- Rebuilt the nutrition integration after the alpha.40 Prism install pass so the final LAB artifact has a fresh version.

## 0.1.0-alpha.40

- Added a stable MariesLib facade for nutrition values, source classifications, food-consumption triggers, and read-only dietary memory.
- Registered six EGO nutrition groups through MariesLib: grains, protein, vegetables, fruit, fats, and minerals.
- Added a vanilla food nutrition catalog without registering custom items or copying third-party assets.
- Added meal quality and gut health calculations that feed the existing server-authoritative `nutrition_balance` display state behind the default-off nutrition module.
- Wired nutrition contexts into scheduler frame calculation after sleep and before EGO Load/Readiness derivation.
- Created MariesLib's expected `config/immersive_ego/scanner_spec.json` on first startup so the scanner spec reload path does not fall back to missing bundled defaults.
- Added JUnit and GameTest coverage for nutrition group keys, vanilla food classification, meal quality, nutrition balance movement, frame ordering, and runtime catalog assumptions.

## 0.1.0-alpha.38

- Added vanilla phantom-rest modeling from `Stats.TIME_SINCE_REST` with the 72000-tick restless spawn threshold.
- Updated the sleep runtime sampler to carry player sleep ticks and the vanilla time-since-rest stat separately.
- Added a runtime GameTest guard that verifies Minecraft 1.21.1 still exposes the expected 100-tick sleep duration, `time_since_rest` stat, and phantom restless threshold constants used by Immersive EGO.

## 0.1.0-alpha.37

- Added circadian phase modeling for dawn, day, dusk, and night using Minecraft day time.
- Added sleep-pressure and sleep-recovery calculations for awake fatigue gain, night sleep, day naps, sleep quality, and phantom-rest threshold modeling.
- Added a default-off sleep module gate and server-side sleep sampler using existing Minecraft sleep state and the `sleep_recovery` capability attribute.
- Wired optional sleep contexts into scheduler frame calculation after encumbrance load and before EGO Load/Readiness derivation.
- Added JUnit coverage for phase boundaries, fatigue pressure, sleep quality, nap behavior, service clamping, phantom-rest threshold modeling, and frame ordering.

## 0.1.0-alpha.36

- Added encumbrance tiers and hysteresis for normal, loaded, strained, and overburdened inventory load states.
- Added load-synergy calculation that drains stamina and raises fatigue from effective encumbrance pressure.
- Wired optional encumbrance contexts into scheduler frame calculation before EGO Load/Readiness derivation.
- Added runtime tier memory so cached inventory load snapshots can produce stable encumbrance transitions.
- Added JUnit coverage for hysteresis thresholds, load deltas, stamina/fatigue service application, clamping, validation, and frame ordering.
- Hardened Prism LAB install verification to match declared `[[mods]]` entries instead of dependency `modId` entries.

## 0.1.0-alpha.35

- Added a pure inventory weight resolver for item stacks using item id, stack size, max stack size, and damageable state.
- Added inventory load snapshots with total weight, load-tolerance-scaled capacity, normalized load ratio, and overburden pressure.
- Added a player-scoped inventory weight cache with refresh, hit, skip, error, and eviction counters.
- Added a gated server-side inventory sampler behind the default-off encumbrance module.
- Added JUnit coverage for item weight heuristics, load ratio/capacity scaling, overburden clamping, cache behavior, fallback on sampler failure, and validation.

## 0.1.0-alpha.34

- Added server-side stamina activity context sampling for movement intensity, sprinting, swimming, airborne state, item use, scheduler interval, and stamina capacity.
- Added pure stamina activity cost and regeneration calculation with capacity scaling.
- Added server-authoritative stamina application before EGO Load/Readiness derivation.
- Wired optional stamina activity contexts into scheduler frame calculation behind the existing default-off physiology module gate.
- Added JUnit coverage for rest recovery, sprint/swim/airborne costs, item-use recovery reduction, capacity scaling, clamping, validation, and frame ordering.

## 0.1.0-alpha.33

- Added server-side respiratory pressure sampling for altitude, depth, submersion, and dimension context.
- Added pure respiratory strain calculation with respiration-efficiency scaling and clamping.
- Wired optional respiratory samples into scheduler frame calculation before hydration demand and EGO Load/Readiness derivation.
- Centralized the physiology module gate shared by hydration and respiration.
- Added JUnit coverage for respiratory pressure mapping, strain weighting, efficiency scaling, service application, validation, and frame ordering.

## 0.1.0-alpha.32

- Added water quality tiers for contaminated, questionable, potable, and purified drinks.
- Added quality-aware hydration recovery and stress penalties for risky water sources.
- Added existing-item purification methods for charcoal/coal filtering and campfire/furnace/smoker boiling.
- Added vanilla item-stack recognition for purification items without registering new items, recipes, fluids, or assets.
- Added JUnit coverage for quality improvement, purification item ids, contamination reduction, and quality-adjusted hydration/stress behavior.
- Extended runtime GameTest coverage to verify purification item recognition under the Minecraft classpath.

## 0.1.0-alpha.31

- Added the first hydration demand calculator driven by thermal strain, respiratory strain, fatigue, scheduler interval, and hydration efficiency.
- Added vanilla drink profiles for water potions, milk buckets, and honey bottles.
- Added Minecraft item-stack drink recognition for direct water potions, milk buckets, and honey bottles while excluding non-drink potion forms.
- Wired server-side drink recovery through `LivingEntityUseItemEvent.Finish`, gated behind core simulation plus the default-off physiology module.
- Wired optional scheduler hydration demand into pure frame calculation so enabled physiology passes can reduce hydration before EGO Load and Readiness derivation.
- Added JUnit coverage for hydration demand, drink profiles, drink recovery, demand clamping, and frame-level hydration demand.
- Added runtime GameTest coverage for vanilla drink item recognition under the Minecraft classpath.

## 0.1.0-alpha.30

- Added a player-scoped server environment cache with deterministic refresh, hit, skip, error, and eviction counters.
- Added live server-side biome/weather sampling for climate profile, season, sky exposure, shelter, and wetness inputs.
- Added `/ego debug environment` for operator-only cache counters, exposure sample details, and thermal snapshot output.
- Wired enabled environment snapshots into scheduler frames so `thermal_strain`, EGO Load, and Readiness can update from environment-derived values.
- Added unit coverage for environment cache behavior, season/climate selection, and scheduler frame application of thermal snapshots.

## 0.1.0-alpha.29

- Added immutable exposure samples, season phases, thermal modes, and thermal snapshots.
- Added a deterministic thermal snapshot calculator for season, sky exposure, shelter, wetness, and climate profile inputs.
- Kept the thermal snapshot layer pure and server-side with no gameplay mutation yet.
- Added unit coverage for invalid exposure inputs, shelter relief, wet cold winter pressure, and sheltered temperate neutral behavior.

## 0.1.0-alpha.28

- Added immutable climate profile primitives for the first environment simulation slice.
- Added a default climate profile catalog for temperate, hot dry, hot humid, cold dry, and cold wet profiles.
- Added deterministic pressure helpers for dominant thermal load and wetness pressure.
- Added unit coverage for profile validation, derived pressures, default catalog uniqueness, immutable defaults, and lookup by id.

## 0.1.0-alpha.27

- Added `EgoProjectBoundaryTest` as a release-hardening guard.
- Verified the project has no forbidden custom block, item, entity, fluid, block entity, or menu registration snippets in main sources.
- Verified forbidden custom block/item/entity model, texture, recipe, and loot-table resource directories are absent.
- Verified main sources and resources do not reference Cold Sweat or Tough As Nails integrations.

## 0.1.0-alpha.26

- Added `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs` to register every required roadmap SERVER config file.
- Registered default-off module configs for environment, physiology, sleep, nutrition, psychology, origins, encumbrance, synergies, Apothic, and performance.
- Added `enabled` and `debugLogging` settings to each module config without enabling unfinished gameplay.
- Added English and French translation keys for the new module config entries.
- Added unit coverage that guards the required config filename list and default-off module metadata.

## 0.1.0-alpha.25

- Added dynamic per-world synergy graph loading from `serverconfig/immersive_ego/synergies.toml`.
- Added `synergies.edges` support for declarative `source -> target : weight` entries.
- Added safe repair for missing, malformed, invalid, or cyclic synergy lists by resetting them to an empty graph.
- Updated `/ego synergy trace` to use configured edges when present, falling back to the built-in sample graph when no edges are configured.
- Added unit coverage for default synergy file creation, valid configured edge loading, invalid edge repair, and cyclic edge repair.

## 0.1.0-alpha.24

- Added `/ego synergy trace` as a permission-level-2 read-only command.
- Added a safe built-in sample synergy graph for trace output until TOML-backed graph loading exists.
- Added English and French translations for the synergy trace header, step lines, and result line.
- Extended runtime GameTest command registration coverage to include `/ego synergy trace`.

## 0.1.0-alpha.23

- Added deterministic synergy edge evaluation over validated acyclic graphs.
- Added topological evaluation so dependent edges run after all upstream contributions, independent of config line order.
- Added evaluation trace records with source value, weight, delta, target before value, and target after value.
- Added support for normalized external source values for non-state sources such as capability attributes.
- Added unit coverage for topological ordering, target clamping, input immutability, trace records, external source values, and missing external source rejection.

## 0.1.0-alpha.22

- Added deterministic synergy graph cycle detection.
- Wired cycle detection into catalog edge validation so cyclic graphs are rejected before evaluation.
- Added unit coverage for acyclic graphs, first-cycle path reporting, direct validation rejection, and catalog validator cycle rejection.

## 0.1.0-alpha.21

- Added the first synergy edge model with finite non-zero bounded weights.
- Added parser support for the declarative `source -> target : weight` edge format.
- Added catalog validation for known endpoints, display-only targets, and duplicate source-target pairs.
- Added deterministic unit coverage for valid parsing, invalid formats, unsafe weights, unknown endpoints, capability targets, duplicate edges, and defensive list copying.

## 0.1.0-alpha.20

- Added immutable normalized curve primitives with strict validation for full 0..1 coverage.
- Added deterministic linear interpolation, clamped runtime inputs, and immutable point storage.
- Added identity response curve hooks to the derived EGO Load and Readiness bridge.
- Added unit coverage for interpolation, clamping, identity behavior, invalid curve shapes, invalid point values, and defensive copying.

## 0.1.0-alpha.19

- Added the first scheduler-driven derived-state bridge for EGO Load and Readiness.
- Added `EgoDerivedStateCalculator` to derive EGO Load from fatigue, thermal strain, respiratory strain, stress, and prior load persistence.
- Added Readiness derivation from hydration, stamina, nutrition balance, focus, comfort, recovery capacity, and inverse EGO Load.
- Updated `EgoSimulationScheduler` frames so enabled scheduler passes can persist changed derived values and refresh display mirrors.
- Kept active simulation disabled by default unless both `enableSimulation` and the dynamic scheduler flag are explicitly enabled.
- Added deterministic unit coverage for neutral and strained derived-state calculations.

## 0.1.0-alpha.18

- Added immutable `EgoSimulationFrame` records for input/output state snapshots.
- Added `EgoSimulationSchedule` with deterministic interval-boundary checks.
- Added a double-gated `EgoSimulationScheduler` hook on server player ticks.
- Kept active simulation disabled by default unless both `enableSimulation` and the dynamic scheduler flag are explicitly enabled.
- Added unit coverage for frame immutability, no-op frames, schedule boundaries, and interval clamping.

## 0.1.0-alpha.17

- Added the dynamic serverconfig loader foundation for `serverconfig/immersive_ego/simulation.toml`.
- Added default creation and range repair for future scheduler and display mirror settings.
- Wired dynamic config loading into server startup.
- Added unit coverage for default config creation and clamped repair of out-of-range values.
- Added explicit NightConfig test runtime dependencies pinned to the NeoForge runtime version.

## 0.1.0-alpha.16

- Added runtime GameTests proving all EGO attributes attach to player suppliers.
- Added the server-authoritative display mirror service with stable modifier IDs and duplicate-modifier protection.
- Added `/ego status` and `/ego debug state` server commands.
- Added GameTest coverage for mirror updates and command registration.
- Added the local GameTest empty structure fixture.
- Bumped from `0.1.0-alpha.15` after the first GameTest pass found a prefixed template path issue.

## 0.1.0-alpha.14

- Added the first persisted server-authoritative `EgoState` attachment with a versioned codec, normalized display mirror values, migration/read guards, and clamping.
- Registered `immersive_ego:ego_state` as a NeoForge data attachment copied on player death.
- Hardened `scripts/install-mod.ps1` so unrelated names like `immersive_ego_civitas-*.jar` no longer match by filename.

## 0.1.0-alpha.7

- Fixed `EgoMath.weightedMean` so very large finite weights cannot overflow the accumulator and collapse a valid result to `0.0`.
- Added regression coverage for large finite weighted-mean inputs.

## 0.1.0-alpha.6

- Hardened `scripts/install-mod.ps1` and `scripts/install-runtime-deps.ps1` so mistyped Prism paths cannot silently create a full fake target tree.
- Kept the normal LAB install path supported while allowing only a missing final `mods` folder under an existing parent to be created.

## 0.1.0-alpha.5

- Updated GitHub Actions to Node 24 based action versions: `actions/checkout@v7`, `actions/setup-java@v5`, and `gradle/actions/setup-gradle@v6`.
- Removed the CI Node 20 deprecation warning path while keeping the same Java 21 Gradle build.

## 0.1.0-alpha.4

- Added `scripts/install-runtime-deps.ps1` to reproduce Prism LAB runtime dependency installation with pinned URLs and SHA-256 checks.
- Updated `scripts/install-mod.ps1` to install and verify runtime dependencies by default before installing Immersive EGO.
- Added `build/runtime-deps-report.json` generation for dependency hash/count proof.

## 0.1.0-alpha.3

- Fixed GitHub Actions bootstrap risk by tracking `gradlew` as executable, preserving LF line endings for shell scripts, and adding a workflow `chmod` guard.
- Fixed `EgoMath.weightedMean` so NaN and infinite weights fail fast instead of collapsing calculations to `0.0`.
- Added unit coverage for non-finite weight rejection.

## 0.1.0-alpha.2

- Initialized NeoForge 1.21.1 / Java 21 project from the official NeoForge MDK.
- Added MIT license, dependency audit, provenance, architecture, and handoff docs.
- Added required mod metadata for Apothic Attributes and MariesLib, plus optional Apotheosis metadata.
- Added project logo resource.
- Added bootstrap server/client config specs.
- Added pure Java normalized math and EGO Load aggregation tests.
- Added Windows Prism LAB install script and install report generation.
- Bumped from `0.1.0-alpha.1` after the first install pass to keep Prism test installs version-unique.
