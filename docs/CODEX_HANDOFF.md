# Codex Handoff

## Current State

Repository is bootstrapped and buildable as a NeoForge 1.21.1 / Java 21 project. The mod id is `immersive_ego`, package root is `com.oblixorprime.immersiveego`, and version is `0.1.0-alpha.27`.

The project currently registers bootstrap server/client config, all required roadmap SERVER config files, safe event hooks, the first EGO attribute foundation, a persisted server-authoritative `EgoState` attachment, a display mirror update service, read-only `/ego` state commands, dynamic per-world `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml` loaders, a disabled-by-default scheduler foundation, a normalized curve primitive, a synergy edge parser/validator/evaluator, TOML-backed synergy graph loading, the first derived EGO Load/Readiness bridge, and an automated boundary guard for forbidden registrations/resources. No custom blocks, items, entities, fluids, menus, workstations, or copied third-party assets are registered.

## Attribute Foundation

- `EgoAttributeCatalog` defines 8 capability multiplier attributes and 12 normalized display-only mirror attributes.
- `EgoAttributes` registers those attributes as syncable NeoForge `PercentageAttribute` values and attaches them to players.
- Local tags under `data/immersive_ego/tags/attribute/` mark `capability` and `display_only` attributes.
- `EgoAttributeMirrorService` mirrors `EgoState` display values into player attributes with stable transient modifier IDs and removes any previous mirror modifier before reapplying.
- Apothic Attributes 2.9.1 source was inspected at commit `666900510edd2c5289d5e29312182e934b70a381`; its GUI displays normal registered player attributes without a client mixin.
- Apotheosis 8.5.4 source was inspected at commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370`; current affix and gem attribute entries are explicit data/codecs, with no discovered automatic attribute-exclusion tag.

## State Foundation

- `EgoState` stores the first normalized display mirror values and always normalizes to schema version `1`.
- `EgoStateCodecs` provides the DFU/NeoForge codec with defaulted fields, legacy schema `0` read support, and rejection for negative or future schema versions.
- `EgoAttachments` registers `immersive_ego:ego_state` in `NeoForgeRegistries.Keys.ATTACHMENT_TYPES`, serialized through the codec and copied on player death.
- `EgoStateTest` covers defaults, version migration guard, clamping, and display-only catalog access on the pure JUnit classpath.

## Commands and GameTests

- `/ego status` reads the executing player's server-side `EgoState`, refreshes display mirrors, and returns a summary.
- `/ego debug state` requires permission level 2, reads the same state, refreshes display mirrors, and returns all normalized display values.
- `/ego synergy trace` requires permission level 2 and prints a read-only synergy trace from configured edges when present, otherwise from the built-in sample graph, without writing back to `EgoState`.
- `EgoGameTests` currently proves player attribute attachment, duplicate-free mirror updates, and command registration.

## Dynamic Server Config

- `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs` register all required roadmap SERVER config files with default-off `enabled` and `debugLogging` values.
- Runtime GameTest startup generated and loaded `immersive_ego-core.toml`, `immersive_ego-environment.toml`, `immersive_ego-physiology.toml`, `immersive_ego-sleep.toml`, `immersive_ego-nutrition.toml`, `immersive_ego-psychology.toml`, `immersive_ego-origins.toml`, `immersive_ego-encumbrance.toml`, `immersive_ego-synergies.toml`, `immersive_ego-apothic.toml`, and `immersive_ego-performance.toml`.
- `EgoServerModuleConfigsTest` guards the required filename list and verifies the module gates stay default-off.
- `EgoDynamicServerConfigLoader` loads `serverconfig/immersive_ego/simulation.toml` from the world root during server startup.
- Missing files are created with defaults.
- Scheduler interval and display mirror epsilon values are clamped and persisted on load.
- `EgoDynamicServerConfigLoaderTest` covers default creation and out-of-range repair on the plain JUnit classpath.
- `EgoDynamicSynergyConfigLoader` loads `serverconfig/immersive_ego/synergies.toml` from the world root during server startup.
- Missing synergy files are created with an empty graph.
- Configured `synergies.edges` entries use `source -> target : weight`; invalid, malformed, duplicate, unsafe, or cyclic edge lists are repaired to an empty graph.
- `EgoDynamicSynergyConfigLoaderTest` covers default file creation, valid configured edges, invalid edge repair, and cyclic graph repair on the plain JUnit classpath.

## Scheduler Foundation

- `EgoSimulationFrame` is an immutable input/output state frame.
- `EgoSimulationSchedule` owns interval-boundary checks.
- `EgoSimulationScheduler` is wired to server player post-tick events, but it only runs when both `enableSimulation` and `scheduler.enabled` are true.
- Current frames derive EGO Load and Readiness from existing server state, persist the changed state when needed, and refresh display mirrors.

## Curve Foundation

- `EgoCurvePoint` validates normalized finite control points.
- `EgoCurve` requires full 0..1 coverage, strictly increasing input points, immutable point storage, and deterministic linear interpolation.
- `EgoCurveTest` covers interpolation, input clamping, identity behavior, invalid shapes, invalid point values, and defensive copying.

## Synergy Foundation

- `EgoSynergyEdge` stores source path, target path, and finite non-zero bounded weight.
- `EgoSynergyEdgeParser` parses the first declarative edge format: `source -> target : weight`.
- `EgoSynergyCycleDetector` finds cyclic source-target paths in deterministic input order.
- `EgoSynergyValidator` validates edges against the attribute catalog, requires display-only targets, rejects duplicate source-target pairs, and rejects cyclic graphs.
- `EgoSynergyEvaluator` applies validated acyclic edges in topological order and returns an evaluation trace.
- `EgoDynamicSynergyConfigLoader` supplies configured graph edges to `/ego synergy trace`; an empty configured graph keeps the safe built-in sample trace path available.
- Non-state sources such as capability attributes require normalized external source values at evaluation time.

## Derived State Foundation

- `EgoDerivedStateCalculator` derives EGO Load from fatigue, thermal strain, respiratory strain, stress, and prior load persistence.
- Readiness is derived from hydration, stamina, nutrition balance, focus, comfort, recovery capacity, and inverse EGO Load.
- The current response curves are identity curves until TOML-backed curve presets are added.
- `EgoDerivedStateCalculatorTest` covers neutral defaults and a strained player state.

## Verified Build

Last verified commands:

```powershell
.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoCurveTest --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEdgeParserTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoaderTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest
.\gradlew.bat runGameTestServer
.\gradlew.bat clean build
```

Result: dynamic server config and synergy config tests passed, the required SERVER config filename guard passed, the project boundary guard passed, synergy evaluator/cycle/parser/validator, curve, derived, frame, and schedule tests passed, `runGameTestServer` passed with 3 required tests, verified `/ego synergy trace` command registration, loaded all registered SERVER config files plus both dynamic config files, and `clean build` passed for `0.1.0-alpha.27`.

## Installed Jar

Use:

```powershell
.\scripts\install-mod.ps1
```

Expected target:

`C:\Users\Emmanuel Tremblay\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods`

The script writes `build/install-report.json`.

By default it also runs `scripts/install-runtime-deps.ps1`, which installs and verifies pinned runtime dependencies and writes `build/runtime-deps-report.json`. Use `-SkipRuntimeDependencies` only for a deliberate jar-only reinstall. The installers refuse to create a full missing target tree; they accept an existing mods directory or a missing final `mods` folder under an existing parent.

Latest verified installed jar:

- `C:\Users\Emmanuel Tremblay\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.27.jar`
- SHA-256: `f76dd02414a960a23bb627d59307b9e54f05da1f725adebd2ae3e0ebd8c11329`
- `build/install-report.json` confirms source/installed hashes match and exactly one installed `immersive_ego` jar remains.

## Important Boundaries

- Do not add Cold Sweat.
- Do not copy Apothic Attributes or Apotheosis assets.
- Do not JarJar MariesLib.
- Do not implement Apothic, MariesLib, or Apotheosis APIs without inspecting the pinned source/JAR first.
- `EgoProjectBoundaryTest` guards against forbidden custom registrations, forbidden resource directories, Cold Sweat references, and Tough As Nails references.

## Next Task

Continue toward the next active simulation slice:

1. Run a client smoke pass to verify EGO attributes are visible in Apothic Attributes.
2. Wire the configured synergy graph into full gameplay-derived systems once those systems exist.
3. Keep investigating the MariesLib `ItemScanner` warning if it appears outside GameTest/dev startup.
