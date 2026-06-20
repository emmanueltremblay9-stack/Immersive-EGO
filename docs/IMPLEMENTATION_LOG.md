# Implementation Log

## 2026-06-20

### Forbidden Registration and Resource Boundary Guard

Implemented:

- Added `EgoProjectBoundaryTest`.
- Guarded against forbidden custom block, item, entity, fluid, block entity, and menu registration snippets in `src/main/java`.
- Guarded against forbidden block/item/entity model, texture, recipe, and loot-table resource directories under the Immersive EGO namespace.
- Guarded `src/main` text sources/resources against Cold Sweat and Tough As Nails references.
- Bumped the final LAB test build to `0.1.0-alpha.27`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.26` to `0.1.0-alpha.27`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.27`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.27.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.26.jar`.
- Final source and installed SHA-256: `f76dd02414a960a23bb627d59307b9e54f05da1f725adebd2ae3e0ebd8c11329`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.27`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.27 jar has primary mod id `immersive_ego`.

Remaining:

- Boundary coverage is source/resource-level static coverage, not a full legal provenance audit.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Required Server Module Config Registration

Implemented:

- Added `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs`.
- Registered all required roadmap SERVER config files: environment, physiology, sleep, nutrition, psychology, origins, encumbrance, synergies, Apothic, and performance.
- Added default-off `enabled` and `debugLogging` values for each registered module config without activating unfinished gameplay.
- Added English and French translations for the new config entries.
- Bumped the final LAB test build to `0.1.0-alpha.26`.

Validation:

- Initial `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest` failed because the plain JUnit test touched NeoForge `ModConfigSpec` accessors. The test was corrected by splitting pure metadata into `EgoServerModuleConfigCatalog`.
- Final `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- Filesystem readback under `run/config` confirmed no required `immersive_ego*.toml` SERVER config files were missing.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.25` to `0.1.0-alpha.26`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.26`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.26.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.25.jar`.
- Final source and installed SHA-256: `332a4a297b57ebae66f963eeda442cecca1d792d64e27267ab558ce61c70bcd4`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoServerModuleConfigCatalog`, `EgoServerModuleConfigs`, both language files, `ImmersiveEgo`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.26 jar has primary mod id `immersive_ego`.

Remaining:

- The new roadmap SERVER config files are safe default-off gates only; future gameplay slices still need to consume them.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Dynamic Synergy Config Foundation

Implemented:

- Added `EgoDynamicSynergyConfig` and `EgoDynamicSynergyConfigLoader` for `serverconfig/immersive_ego/synergies.toml`.
- Added `synergies.edges` support for declarative `source -> target : weight` entries.
- Repaired missing, malformed, invalid, or cyclic configured synergy lists to an empty graph.
- Updated `/ego synergy trace` to use configured edges when present and fall back to the built-in sample graph when no edges are configured.
- Bumped the final LAB test build to `0.1.0-alpha.25`.

Validation:

- Initial `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoaderTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` failed because the default-file assertion depended on exact TOML formatting. The test was corrected to verify behavior by reloading the config.
- Final `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoaderTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.25`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, and loaded both dynamic config files.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.24` to `0.1.0-alpha.25`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.25.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.24.jar`.
- Final source and installed SHA-256: `7bbf93a190cc3ecb9361864ea1259e3a590e14198c1080a7c0491840862762d1`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoDynamicSynergyConfig`, `EgoDynamicSynergyConfigLoader`, `EgoCommands`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.25 jar has primary mod id `immersive_ego`.

Remaining:

- Configured synergy edges are available to `/ego synergy trace`; full gameplay systems still need to consume configured synergies as those systems are implemented.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Trace Command

Implemented:

- Added `/ego synergy trace` as a permission-level-2 read-only command.
- Added a built-in sample graph using the current synergy evaluator until TOML-backed synergy loading exists.
- Added English and French command translations.
- Extended GameTest command registration coverage for `/ego synergy trace`.
- Bumped the final LAB test build to `0.1.0-alpha.24`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.24`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.23` to `0.1.0-alpha.24`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.24.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.23.jar`.
- Final source and installed SHA-256: `ef63db9bab9807e26af0cd30f70c14bda3f9056d19cd4a92799493fcf568a2ee`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoCommands`, `EgoSynergyEvaluator`, both language files, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.24 jar has primary mod id `immersive_ego`.

Remaining:

- TOML-backed synergy loading remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Edge Evaluation

Implemented:

- Added `EgoSynergyEvaluator` to apply validated acyclic synergy edges in topological order.
- Added `EgoSynergyEvaluationResult` and `EgoSynergyEvaluationStep` for traceable source value, weight, delta, before value, and after value records.
- Added normalized external source support for non-state sources such as capability attributes.
- Added `EgoSynergyEvaluatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.23`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.23`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.22` to `0.1.0-alpha.23`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.23.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.22.jar`.
- Final source and installed SHA-256: `462cdac06f952e45918cd7bc4406da9573b0c30f68cc8f728e136f9f14ebe2ed`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyEvaluator`, `EgoSynergyEvaluationResult`, `EgoSynergyEvaluationStep`, `EgoSynergyCycleDetector`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.23 jar has primary mod id `immersive_ego`.

Remaining:

- `/ego synergy trace` and TOML-backed synergy loading remain future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Cycle Detection

Implemented:

- Added `EgoSynergyCycleDetector` for deterministic cycle detection over synergy source-target graphs.
- Wired cycle rejection into `EgoSynergyValidator`.
- Added `EgoSynergyCycleDetectorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.22`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.22`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.21` to `0.1.0-alpha.22`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.22.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.21.jar`.
- Final source and installed SHA-256: `86f75d3c07a5645f53ecbadc20ad27a3c1735ec64613c3d484256509cc7d07d6`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyCycleDetector`, `EgoSynergyValidator`, `EgoSynergyEdge`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.22 jar has primary mod id `immersive_ego`.

Remaining:

- Synergy edge evaluation remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Edge Parser and Validator

Implemented:

- Added `EgoSynergyEdge` for source path, target path, and finite non-zero bounded weight.
- Added `EgoSynergyEdgeParser` for the first declarative `source -> target : weight` format.
- Added `EgoSynergyValidator` for catalog endpoint validation, display-only targets, duplicate source-target pair rejection, and defensive list copying.
- Added `EgoSynergyEdgeParserTest` and `EgoSynergyValidatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.21`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEdgeParserTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.21`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.20` to `0.1.0-alpha.21`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.21.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.20.jar`.
- Final source and installed SHA-256: `b4f041b5f33b51d6e3412a7a0b1a598c73cd4ce2ae02e6cc5690432391326c50`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyEdge`, `EgoSynergyEdgeParser`, `EgoSynergyValidator`, `EgoCurve`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.21 jar has primary mod id `immersive_ego`.

Remaining:

- Cycle detection and edge evaluation remain future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Configurable Curve Foundation

Implemented:

- Added `EgoCurvePoint` for finite normalized curve control points.
- Added `EgoCurve` for immutable 0..1 response curves with full-domain validation, strictly increasing inputs, linear interpolation, and clamped runtime inputs.
- Wired identity response curves into the derived EGO Load and Readiness bridge without changing current output behavior.
- Added `EgoCurveTest`.
- Bumped the final LAB test build to `0.1.0-alpha.20`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoCurveTest --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.20`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.19` to `0.1.0-alpha.20`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.20.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.19.jar`.
- Final source and installed SHA-256: `62140d80b5d9755323347106c9a6d9218aa1e85afd1be03057c6b6775807c99d`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoCurve`, `EgoCurvePoint`, `EgoDerivedStateCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.20 jar has primary mod id `immersive_ego`.

Remaining:

- Curves are implemented as deterministic primitives and identity hooks; TOML-backed curve preset loading remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Derived State Bridge

Implemented:

- Added `EgoDerivedStateCalculator` as the first scheduler-driven derived-state bridge.
- Derived EGO Load from fatigue, thermal strain, respiratory strain, stress, and prior load persistence.
- Derived Readiness from hydration, stamina, nutrition balance, focus, comfort, recovery capacity, and inverse EGO Load.
- Updated `EgoSimulationScheduler` so enabled scheduler frames persist changed derived state and refresh mirrors.
- Added `EgoDerivedStateCalculatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.19`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.18` to `0.1.0-alpha.19`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.19`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.19.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.18.jar`.
- Final source and installed SHA-256: `fb3626510d13573b2ed841b1af17b04183af050b9b84683b9f0de427dbdd47ef`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoDerivedStateCalculator`, `EgoSimulationFrame`, `EgoSimulationSchedule`, `EgoSimulationScheduler`, `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoCommands`, `EgoAttributeMirrorService`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.19 jar has primary mod id `immersive_ego`.

Remaining:

- The scheduler remains disabled by default and requires both `enableSimulation` and `scheduler.enabled` to persist derived values.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Simulation Frame and Scheduler Foundation

Implemented:

- Added immutable `EgoSimulationFrame` records with game time, interval, input state, and output state.
- Added `EgoSimulationSchedule` for deterministic interval-boundary checks and interval clamping.
- Added `EgoSimulationScheduler` on server player post-tick events.
- Kept scheduler execution double-gated behind `EgoCoreConfig.ENABLE_SIMULATION` and the dynamic `scheduler.enabled` flag.
- Current scheduler frames are no-op frames that can refresh mirrors but do not mutate gameplay state.
- Added `EgoSimulationFrameTest` and `EgoSimulationScheduleTest`.
- Bumped the final LAB test build to `0.1.0-alpha.18`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.17` to `0.1.0-alpha.18`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.18`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.18.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.17.jar`.
- Final source and installed SHA-256: `be101f52b4d57dd5cbd6af5b7dd2327002b88c9f6423db8d1306ff87fc68fab6`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.18.jar` and `jar tf` on the installed LAB jar confirm `EgoSimulationFrame`, `EgoSimulationSchedule`, `EgoSimulationScheduler`, `EgoDynamicServerConfig`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.18 jar has primary mod id `immersive_ego`.

Remaining:

- The scheduler intentionally advances no-op frames until the first real derived-state calculation is implemented.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Dynamic Server Config Foundation

Implemented:

- Added `EgoDynamicServerConfig` for the first per-world dynamic server config values.
- Added `EgoDynamicServerConfigLoader` for `serverconfig/immersive_ego/simulation.toml`.
- The loader creates the missing directory/file, writes defaults, clamps out-of-range numeric values, persists repaired values, and loads during `ServerStartingEvent`.
- Added `EgoDynamicServerConfigLoaderTest` for missing-file default creation and out-of-range value repair.
- Added explicit NightConfig `core` and `toml` `testRuntimeOnly` dependencies pinned to `3.8.3`, matching the NeoForge runtime jars.
- Bumped the final LAB test build to `0.1.0-alpha.17`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest` first failed because the plain JUnit runtime lacked NightConfig; fixed by adding explicit NightConfig test runtime dependencies.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest` then passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and logged that it loaded `serverconfig/immersive_ego/simulation.toml`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.16` to `0.1.0-alpha.17`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.17`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.17.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.16.jar`.
- Final source and installed SHA-256: `0e4af22f671f775626c46993fea82594723003f1c1a159604c597b5b66cd1ee4`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.17.jar` and `jar tf` on the installed LAB jar confirm `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoCommands`, `EgoAttributeMirrorService`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.2.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.17 jar has primary mod id `immersive_ego`.

Remaining:

- `serverconfig/immersive_ego/simulation.toml` currently stores scheduler placeholder values only; the active scheduler and simulation frames remain next.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Runtime Mirror and Command Vertical Slice

Implemented:

- Fixed the GameTest template lookup by disabling NeoForge's class-name prefix for the local `empty` structure.
- Added `EgoAttributeMirrorService`, which mirrors server-side `EgoState` display values into player display attributes through stable transient modifier IDs under `immersive_ego:mirror/*`.
- Added duplicate-modifier protection by removing the previous mirror modifier before applying the current value.
- Exposed display-only attribute holders through `EgoAttributes.displayAttributes()`.
- Registered the mirror service on player login and respawn.
- Added `/ego status` for a player-facing state summary.
- Added `/ego debug state` for permission-level-2 state inspection.
- Added English and French command translations.
- Added runtime GameTests for player attribute attachment, mirror update behavior, duplicate-modifier prevention, and command registration.
- Added the local `data/immersive_ego/structure/empty.nbt` GameTest fixture.
- Bumped the final LAB test build to `0.1.0-alpha.16`. The intermediate `0.1.0-alpha.15` pass was superseded after the first GameTest run exposed the prefixed template path issue.

Validation:

- `.\gradlew.bat compileJava` passed after the GameTest template fix and alpha.16 version bump.
- `.\gradlew.bat runGameTestServer` passed with `All 1 required tests passed` after the template fix.
- `.\gradlew.bat compileJava` passed after adding the mirror service and duplicate-modifier GameTest.
- `.\gradlew.bat runGameTestServer` passed with `All 2 required tests passed`.
- `.\gradlew.bat compileJava` passed after adding `/ego` commands and command translations.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.16`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.16.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.14.jar`.
- Final source and installed SHA-256: `be1d549867091a735bb48cb70a95c33c5a547ecc3c8d1b3b14573cd8b4b07b4e`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.16.jar` and `jar tf` on the installed LAB jar confirm `EgoCommands`, `EgoAttributeMirrorService`, `EgoGameTests`, `data/immersive_ego/structure/empty.nbt`, language files, and `META-INF/neoforge.mods.toml` are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.16`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.2.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.16 jar has primary mod id `immersive_ego`.

Remaining:

- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Dynamic TOML loading, active simulation frames, and scheduler remain next.

### State Attachment Foundation

Implemented:

- Added pure `EgoState` with normalized display mirror values, schema version `1`, clamping, display-only catalog access, and schema read guards.
- Added `EgoStateCodecs` with defaulted codec fields, legacy schema `0` migration into the current state shape, and rejection for negative or future schemas.
- Registered `immersive_ego:ego_state` through `EgoAttachments` as a serialized NeoForge attachment copied on player death.
- Added `EgoStateTest` for defaults, clamping, schema read guards, and display-only catalog access.
- Hardened `scripts/install-mod.ps1` filename matching so `immersive_ego_civitas-*.jar` no longer matches as an `immersive_ego` jar by filename.
- Bumped the final LAB test build to `0.1.0-alpha.14`. An intermediate `0.1.0-alpha.13` pass was superseded after the installer matcher was tightened.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.data.EgoStateTest` first failed because plain JUnit tests cannot see DFU/Gson codec classes; fixed by moving the codec to `EgoStateCodecs` and keeping `EgoState` pure.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.data.EgoStateTest` passed.
- Installer matcher check confirmed `immersive_ego-0.1.0-alpha.13.jar`, `immersive_ego.jar`, and `immersive-ego-0.1.0.jar` match, while `immersive_ego_civitas-0.1.0-alpha.1.jar` and `other.jar` do not.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.14`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.14.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.13.jar`.
- Final installed SHA-256: `cb7abe3310fd105ae7dd7771efebd27904b3298eda04ab6da4da45cdc30b3232`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.14.jar` and `jar tf` on the installed LAB jar confirm `EgoState`, `EgoStateCodecs`, `EgoAttachments`, `ImmersiveEgo`, and `META-INF/neoforge.mods.toml` are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.14`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- No live client/server smoke test or GameTest was run for persisted attachment behavior.
- Mirror update service and `/ego` commands remain next.
- During the intermediate `0.1.0-alpha.13` installer pass, the previous broad filename matcher also removed `immersive_ego_civitas-0.1.0-alpha.1.jar` by filename only. The final `0.1.0-alpha.14` pass used the narrowed matcher and removed only the intended prior `immersive_ego` jar.

### Attribute Foundation

Implemented:

- Inspected Apothic Attributes `1.21` source at commit `666900510edd2c5289d5e29312182e934b70a381` and confirmed the native attributes GUI displays registered player attributes with translation descriptions.
- Inspected Apotheosis `1.21` source at commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370` and found current affix/gem attribute use is explicit data/codecs rather than an automatic all-attributes scan.
- Added `EgoAttributeCatalog` with 8 capability multiplier attributes and 12 normalized display-only mirror attributes.
- Registered the attributes as syncable NeoForge `PercentageAttribute` values through `EgoAttributes`.
- Attached all EGO attributes to players with `EntityAttributeModificationEvent`.
- Added local `immersive_ego:capability` and `immersive_ego:display_only` attribute tags for future compat/datagen guards.
- Added English and French names/descriptions for Apothic GUI display.
- Added pure Java unit coverage for unique attribute paths, ranges, roles, and positive/negative display sentiment.
- Bumped the final LAB test build to `0.1.0-alpha.12` after the installer revealed Prism LAB already had `0.1.0-alpha.11`; the temporary `0.1.0-alpha.8` pass was not kept as final.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.registry.EgoAttributesTest` first failed because the test imported Minecraft `Attribute` on the plain JUnit classpath; fixed by separating the pure catalog/sentiment model from the Minecraft-backed registry.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.registry.EgoAttributesTest` passed after the catalog split.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.12`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.12.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.8.jar`.
- Final installed SHA-256: `e41f9d1bdc7c07bbb4b935b8b228f0267ea09f923cda32cb94637e0084ba91b0`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.12.jar` and `jar tf` on the installed LAB jar confirm mod metadata, registry classes, language files, logo, and both attribute tag files are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.12`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- The Apothic GUI path is source-verified and the attributes are attached to players, but no live client GUI screenshot/smoke test was run in this pass.
- Mirror update service, server-authoritative `EgoState`, and runtime/GameTest attribute supplier proof remain next.

## 2026-06-19

### Bug Hunt 6

Implemented:

- Fixed `EgoMath.weightedMean` overflow behavior for very large finite weights by scaling all weights against the maximum input weight before accumulation.
- Added regression coverage for large finite weights that previously could overflow both weighted sum and total weight.
- Bumped the test build from `0.1.0-alpha.6` to `0.1.0-alpha.7`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoMathTest` passed, including the new large finite weight regression.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.7`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.7.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.6.jar`.
- Final installed SHA-256: `9e75a6b31b671ec9063d7cde54cc486dff46437bf9b62077411e248882cd43eb`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.7.jar` confirms mod metadata, compiled classes, and the project logo are present.
- `git diff --check` passed.

### Bug Hunt 5

Implemented:

- Fixed installer path safety: `scripts/install-mod.ps1` and `scripts/install-runtime-deps.ps1` no longer create an entire missing target tree from a mistyped `-ModsDir`.
- Added a shared guard pattern that accepts an existing mods directory, or creates only a missing final `mods` folder when its parent already exists.
- Bumped the test build from `0.1.0-alpha.5` to `0.1.0-alpha.6`.

Validation:

- `.\scripts\install-runtime-deps.ps1 -ModsDir <missing-parent-test-path>` refused to create a full missing target tree before download/install work.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.6`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.6.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.5.jar`.
- Final installed SHA-256: `f0e86aadafc965da20fc22effeadda9caae06ae1dc40756bd85f4fd131eb3959`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.6.jar` confirms mod metadata, compiled classes, and the project logo are present.
- `git diff --check` passed.
- GitHub Actions `Build` passed on run `27846334653`.

### Bug Hunt 2

Implemented:

- Fixed GitHub Actions wrapper execution risk by tracking `gradlew` with executable mode `100755`.
- Added `.gitattributes` rules for wrapper/script line endings.
- Added explicit workflow `chmod +x ./gradlew` guard before the Linux build step.
- Hardened `EgoMath.weightedMean` against NaN and infinite weights.
- Added tests for non-finite weight rejection.
- Bumped the test build from `0.1.0-alpha.2` to `0.1.0-alpha.3`.

Validation:

- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.3`.
- `.\scripts\install-mod.ps1` installed `immersive_ego-0.1.0-alpha.3.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.2.jar`.
- Final installed SHA-256: `b790c3f21838f6afa4233d97c11db2819577bf3fdeb8cf5cb31ebc58cee895fc`.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.

### Bug Hunt 3

Implemented:

- Fixed the reproducibility gap where required LAB runtime dependencies were installed manually outside the repo workflow.
- Added `scripts/install-runtime-deps.ps1` with pinned URLs and SHA-256 checks for Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config API.
- Updated `scripts/install-mod.ps1` to run the runtime dependency installer by default, with `-SkipRuntimeDependencies` for deliberate jar-only installs.
- Bumped the test build from `0.1.0-alpha.3` to `0.1.0-alpha.4`.

Validation:

- `.\scripts\install-runtime-deps.ps1` passed and wrote `build/runtime-deps-report.json` with hash matches for all dependencies.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.4`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.4.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.3.jar`.
- Final installed SHA-256: `ab913e19eefbf065dba1e175c5e094c0844fb2b4c60ce13b58882bdd9275bdcc`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.

### Bug Hunt 4

Implemented:

- Fixed the GitHub Actions Node 20 deprecation warning path by updating action pins to current Node 24 based majors.
- Bumped the test build from `0.1.0-alpha.4` to `0.1.0-alpha.5`.

Validation:

- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.5`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.5.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.4.jar`.
- Final installed SHA-256: `f8ca4ff12b6bc1ae7cffab21503a9eaeff55e60ffa31516d3c14925f6aa2b399`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- GitHub Actions `Build` passed on run `27845439246`.

### Bootstrap

Implemented:

- Cloned empty GitHub repository into `C:\Users\Emmanuel Tremblay\AI Depot\Codex Documents\Immersive EGO`.
- Bootstrapped from NeoForge MDK 1.21.1 ModDevGradle.
- Replaced MDK example mod with `immersive_ego` package and metadata.
- Added required dependency repositories and pinned versions.
- Added project logo resource.
- Added MIT license, third-party notices, audit docs, roadmap, and handoff docs.
- Added bootstrap server/client config specs.
- Added pure Java `EgoMath` and `EgoLoadCalculator`.
- Added JUnit 5 tests.
- Bumped the installed test build from `0.1.0-alpha.1` to `0.1.0-alpha.2` after the first install pass.

Validation:

- `.\gradlew.bat clean build` failed once because Curios' repository was missing.
- Added `https://maven.theillusivec4.top/`.
- `.\gradlew.bat clean build` failed once due exact floating-point assertion.
- Added tolerance to the assertion.
- `.\gradlew.bat clean build` passed on 2026-06-19.
- Final `.\gradlew.bat clean build` passed for version `0.1.0-alpha.2`.
- `.\scripts\install-mod.ps1` installed `immersive_ego-0.1.0-alpha.2.jar` into Prism LAB.
- Final installed SHA-256: `1d85636bd8bc0bfc04e85d04c8eb7ce96c9b6e0bfb050f317e716bedb8f9c654`.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed required LAB dependencies after metadata inspection: Apothic Attributes `2.9.1`, Placebo `9.9.1`, MariesLib `0.1.0-beta.5`, Curios `9.5.1+1.21.1`, and Cloth Config API `15.0.140+neoforge`.

Next exact task:

- Inspect Apothic Attributes 2.9.1 source and implement the first capability/display attribute registration set without copied assets.
