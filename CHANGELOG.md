# Changelog

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
