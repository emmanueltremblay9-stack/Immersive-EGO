# Codex Handoff

## Active correction — approved option B, 2026-10-17

The owner explicitly selected B at 10:44 UTC. This supersedes the undecided A/B/C state in the historical follow-up below. Branch `fix/thermal-tolerance-snapshot`, based on verified merged main `3f7734cc4bbd6802c12c09b5990544b86faac47a`, implements adjusted snapshot pressures feeding existing hydration/acclimation consumers. See [exact contract and diagnostics](THERMAL_TOLERANCE.md). All 4,821 files inventoried across prior proof directories are preserved; previous reports remain historical, not rewritten results. Only an isolated cloud client is authorized. Clean-build, packaged retest and draft PR/CI evidence are being collected; no merge/release/user-PC action is authorized.

## Current follow-up — 2026-10-17

The same original alpha.62 JAR/commit remains the candidate; no gameplay source or binary change. The original 199 inventoried evidence files and ZIP are unchanged. Read the [self-contained follow-up](THERMAL_TOLERANCE_FOLLOWUP.md) for exact DEV/GOV contracts, historical pre-PR2 proof, code path, decision options and the disposition of all nine original NOT_RUN groups.

New evidence: **34 freshly executed required GameTests passed, exit 0**, plus 21 additional packaged-client samples. Environment/scheduler-off pause, rain, snow, boat, actual submersion with drowning damage, End and daytime thunderstorm nap are now observed. The ordinary official asset route now returns 200; all 3,888 unique objects (825,818,935 bytes) passed SHA-1/size checks. New clients show no missing-asset exceptions, real panorama and an initialized sound engine. Process-local OpenAL Wave File Writer records actual stereo audio; no physical speaker exists. Maven metadata for optional Apotheosis returns 403 and was not bypassed. Active synergy integration is not implemented; no new integration was fabricated.

`thermal_tolerance` still has no thermal simulation consumer, already at pre-PR2 `50d9e4b9` and with the same qualitative promise present in `789238c`. DEV/GOV do not specify the formula or ordering. Recommend decision option A (scale the thermal state before existing origin relief), but **do not implement it without the gameplay decision**; B changes broader snapshot consumers, C explicitly defers the capacity. No fix PR or new-commit CI exists because no formula was invented. All follow-up clients/display are stopped and 13 temporary TOMLs restored. Existing original clean-build/CI proofs remain attributed separately.

## Earlier handoff — initial packaged qualification, 2026-10-17

The resumed cloud checkout is at merged-main `3f7734cc4bbd6802c12c09b5990544b86faac47a`, advanced cleanly from `50d9e4b9`; PR2 must not be recreated. User-authorized isolated-cloud qualification is executed. The exact alpha.62 production JAR, SHA-256 `064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6`, was loaded from `mods` by `forgeclient`, with actual controllable singleplayer play and restart. No development `runClient` substitutes for this proof.

Fresh clean build: 175 JUnit passed; no fresh GameTests. Existing exact-main CI 38920721166 is success by metadata readback. Bounded Desertborn and PR2 client scenarios pass; 76 state samples and real screenshots are retained. Seven capabilities showed effects, but `thermal_tolerance` has no consumer in either this commit or the pre-PR2 baseline. Full gate remains OPEN; no arbitrary formula, source fix or new PR was introduced. External assets/audio are incomplete after respected network/environment limits. See [complete qualification and deliverables](DESERTBORN_PACKAGED_QUALIFICATION.md).

All client processes and the private display are stopped; 13 temporary TOMLs are restored to their originals, including disabled core/scheduler. The isolated save and diagnostic datapack remain under `/workspace/ego-qualification-20261017`. Only documentation is changed in this checkout after the candidate build. No merge/release/publication/user-PC install or credential/security change occurred.

Next work must preserve the approved order: finish qualification/resolve the existing thermal-tolerance contract, then shelters without active recovery, then equipment. The historical entries below remain provenance, not current branch status or fresh qualification evidence.

## Historical PR2 cloud cycle — 2026-10-16

The current work is `feat/persistent-wetness`, based on merged PR1/main `9eb621b684791162862f2262fa586b8ddcc35f4d`. The new local build identity is alpha.62; this does not authorize a release or user installation. See [wetness design](WETNESS.md) and [qualification matrix](WETNESS_VALIDATION.md). User's complete PR2 mandate supersedes historical planning-only instructions. No environment configuration/network permissions were changed. Historical alpha.61 JAR/JUnit/GameTest/Desertborn proofs remain under `/workspace/cloud-setup/`, with the old JAR and JUnit reports copied to `pr2-alpha61-preserved` before the new clean build.

## Current State

Repository is bootstrapped and buildable as a NeoForge 1.21.1 / Java 21 project. The mod id is `immersive_ego`, package root is `com.oblixorprime.immersiveego`, and version is `0.1.0-alpha.62` (merged PR2; bounded packaged-client qualification recorded above, not a release).

Complementary material from `<USER_HOME>\Downloads\immersive_ego_missing_project_pack.zip` has been installed without overwriting live alpha files. Additive content is in `branding/`, `codex_prompts/`, `config_templates/`, root `*.template` files, `scripts/verify_ego_repo.ps1`, and missing planning docs. Pack metadata, the resource TOML template, and older language catalogs are preserved under `docs/project_pack/immersive_ego_missing_project_pack/` as reference material.

Source provenance and credits were refreshed on 2026-06-22 in `docs/SOURCE_PROVENANCE.md`, `docs/CREDITS.md`, `docs/LICENSE_MATRIX.md`, and `THIRD_PARTY_NOTICES.md`. The complementary pack hash mismatch is documented, and imported reference folders remain excluded from the runtime jar.

On 2026-06-27, `EgoMath.weightedMean` was hardened to reject non-finite input values before aggregation. The bug was that non-finite values could be silently saturated through `clamp01` while non-finite weights were already rejected. Alpha.57 passed the targeted `EgoMathTest`, `compileJava`, `runGameTestServer` with all 23 required GameTests, `clean build`, and Prism LAB install verification. The installed jar is `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.57.jar`, with matching source/installed SHA-256 `b04bad518361be95095e16f1a600bb26a9ed74334d869737173b03bd1d5c7e9e` and exactly one installed `immersive_ego` jar.

On 2026-06-28, three additional boundary bugs were fixed and verified as alpha.58: MariesLib dietary levels now treat non-finite tracked values as missing instead of full nutrition, synergy evaluation rejects non-finite external capability source values, and capability multiplier attributes now have a `0.25` minimum to match the runtime multiplier floor and avoid valid zero-valued attributes feeding contexts that require positive multipliers. Alpha.58 passed targeted tests, full `test`, `compileJava`, `runGameTestServer` with all 23 required GameTests, `clean build`, and Prism LAB install verification. The installed jar is `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.58.jar`, with matching source/installed SHA-256 `6bfc8983f272d2132158039fd5d320f6c040909a726f53355087f817927c3c68` and exactly one installed `immersive_ego` jar.

Later on 2026-06-28, the standard workflow was re-applied as a versioned validation/install pass without behavior changes. Alpha.59 passed `compileJava processResources --stacktrace`, full `test --stacktrace`, `runGameTestServer --stacktrace` with all 23 required GameTests, `build --stacktrace`, `clean build --stacktrace`, and Prism LAB install verification. The installed jar is `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.59.jar`, with matching source/installed SHA-256 `4eae856e00497b7eb049c87a0074df03e2d73f473d80a2da6c09a4a2a4a732fd` and exactly one installed `immersive_ego` jar by primary mod metadata.

On 2026-07-06, the cache lifecycle bug hunt fixed two runtime edge cases: stale environment/inventory/encumbrance state could survive after logout, and per-player cache refresh windows stayed at startup defaults even when runtime scheduler interval changed. These are fixed in `alpha.60`, which now runs `.\gradlew.bat clean build` and `.\scripts\install-mod.ps1`; installed jar is `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.60.jar` with matching source/installed SHA-256 `6cf92bd26f7c7629b204d551e456082f694a2cddd315ec685cd5315c894d3932` and exactly one installed `immersive_ego` jar by primary mod metadata.
On 2026-07-07, the latest work pass updated the LAB install without gameplay changes: `alpha.61` now runs `.\gradlew.bat clean build` and `.\scripts\install-mod.ps1`; installed jar is `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.61.jar` with matching source/installed SHA-256 `23a342a141124d2149f2c59f56161718ff5e39c2521ee70600281c164c87ee21` and exactly one installed `immersive_ego` jar by primary mod metadata.

`docs/CURSEFORGE_DESCRIPTION.md` was expanded on 2026-06-22 into a release-facing alpha metadata draft with dependencies, proof status, known release gaps, boundaries, credits, and suggested CurseForge metadata. It still must be finalized after live vertical-slice proof, release screenshots, and final release evidence.

The current decision-summary review is captured in `docs/FULL_PROJECT_REVIEW.md` and mirrored to the Notion dashboard page `Immersive EGO Full Project Review - 2026-06-22` at `<PRIVATE_NOTION_REFERENCE>`. It reconciles the master prompt, live repo state, refreshed build/GameTest/install/smoke evidence, dirty worktree state, remaining blockers, and the recommendation that no extra GPT/subagents are needed unless a deeper code-level audit is requested.

Next-gate work on 2026-06-22 removed one LAB runtime blocker: `immersive_ego_civitas-0.1.0-alpha.35.jar` was moved out of the active mods tree because its metadata required `immersive_ego 0.1.0-alpha.44` while LAB is now on alpha.59. A later controlled LAB pass opened the Apothic Attributes `Attributes` panel from the vanilla survival inventory and captured EGO rows including `EGO Acclimation Gain`, `EGO Hydration Efficiency`, and `EGO Load`. Proof screenshot: `docs/proof/apothic-live-ui-alpha56-20260622-060914.png`.

The next Desertborn proof attempt on 2026-06-22 did not close the gate. The correct LAB instance and alpha.56 jar were verified, LAB simulation/module gates were temporarily enabled, and the existing `New World` save was checked for a missing origin attachment so a desert-start arid assignment could still be proven. A temporary datapack plus `JoinWorldOnLaunch=New World` probe was removed after Prism did not pass quickplay/world arguments and no world-load `EGO_PROOF` markers appeared in `latest.log`. The supported Windows-control plugin also failed before attaching with an internal `@oai/sky` export error, and fallback focus/input attempts could not make Minecraft the foreground window. Runtime config was restored to default-off and no Java client was left running. Desertborn remains unproven until a controllable live client session captures desert/hot-dry environment, arid origin assignment, scheduler-updated state, and the expected vertical-slice progression/readbacks.

The project currently registers bootstrap server/client config, all required roadmap SERVER config files, safe event hooks, the first EGO attribute foundation, persisted server-authoritative `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` attachments, a display mirror update service, read-only `/ego` state commands, dynamic per-world `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml` loaders with malformed TOML fallback, a disabled-by-default scheduler foundation, a normalized curve primitive, a synergy edge parser/validator/evaluator, TOML-backed synergy graph loading, derived EGO Load/Readiness, immutable climate profiles, live server-side environment sampling, player-scoped environment caching with debug counters, thermal snapshots, scheduler-driven thermal strain handoff, hydration demand/recovery primitives, vanilla drink recognition, water quality tiers, existing-item purification recognition, gated server-side drink handling, respiratory strain sampling for altitude/depth/submersion/dimension context, stamina activity cost/regeneration, inventory weight resolving/caching, encumbrance hysteresis, stamina/fatigue load synergies, circadian phase modeling, sleep pressure, sleep quality, nap recovery, vanilla phantom-rest stat modeling, MariesLib-backed nutrition values and vanilla food classifications, dietary memory, meal quality, gut health, psychology/recovery budget calculation, persistent injury memory, origin profile assignment, persistent acclimation axes, command permission guards, optional-Apotheosis absence guards, scanner-spec startup hardening, repeatable dedicated-server startup smoke with client-class-load failure markers, and automated boundary guards for forbidden registrations/resources and client-only source references. No custom blocks, items, entities, fluids, menus, workstations, or copied third-party assets are registered.

## Attribute Foundation

- `EgoAttributeCatalog` defines 8 capability multiplier attributes and 12 normalized display-only mirror attributes.
- `EgoAttributes` registers those attributes as syncable NeoForge `PercentageAttribute` values and attaches them to players.
- Local tags under `data/immersive_ego/tags/attribute/` mark `capability` and `display_only` attributes.
- `EgoAttributeMirrorService` mirrors `EgoState` display values into player attributes with stable transient modifier IDs and honors configured mirror-update epsilon values before reapplying modifiers.
- Apothic Attributes 2.9.1 source was inspected at commit `666900510edd2c5289d5e29312182e934b70a381`; its GUI displays normal registered player attributes without a client mixin.
- Installed Apothic Attributes 2.9.1 bytecode/lang inspection confirms the player attributes UI is attached to the vanilla survival inventory through the `Show Attributes` button. Live LAB proof on 2026-06-22 confirms EGO rows appear in that GUI.
- Apotheosis 8.5.4 source was inspected at commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370`; current affix and gem attribute entries are explicit data/codecs, with no discovered automatic attribute-exclusion tag.

## State Foundation

- `EgoState` stores the first normalized display mirror values and always normalizes to schema version `1`.
- `EgoStateCodecs` provides the DFU/NeoForge codec with defaulted fields, legacy schema `0` read support, and rejection for negative or future schema versions.
- `EgoOriginState` stores persisted origin id plus heat, cold, altitude, and humidity acclimation axes and always normalizes to schema version `1`.
- `EgoOriginStateCodecs` provides a separate defaulted DFU/NeoForge codec for the origin attachment.
- `EgoInjuryMemory` stores persisted recent injury, lingering pain, and recovery debt and always normalizes to schema version `1`.
- `EgoInjuryMemoryCodecs` provides a separate defaulted DFU/NeoForge codec for the injury-memory attachment.
- `EgoAttachments` registers `immersive_ego:ego_state`, `immersive_ego:ego_origin_state`, and `immersive_ego:ego_injury_memory` in `NeoForgeRegistries.Keys.ATTACHMENT_TYPES`, serialized through codecs and copied on player death.
- `EgoStateTest` covers defaults, version migration guard, clamping, and display-only catalog access on the pure JUnit classpath.

## Commands and GameTests

- `/ego status` reads the executing player's server-side `EgoState`, refreshes display mirrors, and returns a summary.
- `/ego debug state` requires permission level 2, reads the same state, refreshes display mirrors, and returns all normalized display values.
- `/ego debug environment` requires permission level 2, samples/caches the executing player's environment, and returns cache counters plus exposure and thermal snapshot details.
- `/ego debug origin` requires permission level 2 and returns the executing player's persisted origin id plus heat, cold, altitude, and humidity acclimation axes.
- `/ego synergy trace` requires permission level 2 and prints a read-only synergy trace from configured edges when present, otherwise from the built-in sample graph, without writing back to `EgoState`.
- `EgoGameTests` currently proves attribute catalog uniqueness/bounds/sentiment, player attribute attachment, duplicate-free mirror updates, command registration including `/ego debug origin`, permission-level-2 requirements for `/ego debug` and `/ego synergy`, vanilla drink/purification item recognition, Minecraft sleep/phantom-stat assumptions, vanilla food coverage for the six MariesLib nutrition groups, the default unassigned origin attachment, the default clear injury-memory attachment, EGO state/origin/injury attachment mutation readback, player NBT save/load round-tripping for the state/origin/injury attachments, death-respawn clone-event attachment copy policy, malformed dynamic config fallback, hydration demand progression, respiratory dimension mapping into server state, stamina consume/recover progression, sleep recovery progression, awake-night sleep debt progression, day-nap behavior, vanilla-food nutrition-balance progression, the live food-consumption event path updating MariesLib dietary memory plus persisted nutrition state, and psychology runtime recording of live injury memory plus persisted stress/focus/comfort/recovery pressure.

## Dynamic Server Config

- `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs` register all required roadmap SERVER config files with default-off `enabled` and `debugLogging` values.
- Runtime GameTest startup generated and loaded `immersive_ego-core.toml`, `immersive_ego-environment.toml`, `immersive_ego-physiology.toml`, `immersive_ego-sleep.toml`, `immersive_ego-nutrition.toml`, `immersive_ego-psychology.toml`, `immersive_ego-origins.toml`, `immersive_ego-encumbrance.toml`, `immersive_ego-synergies.toml`, `immersive_ego-apothic.toml`, and `immersive_ego-performance.toml`.
- `EgoServerModuleConfigsTest` guards the required filename list and verifies the module gates stay default-off.
- `EgoDynamicServerConfigLoader` loads `serverconfig/immersive_ego/simulation.toml` from the world root during server startup.
- Missing files are created with defaults.
- Scheduler interval and display mirror epsilon values are clamped and persisted on load. Malformed TOML parser failures reset only this dynamic file to safe defaults.
- Scheduler interval repair clamps huge numeric TOML values before narrowing to `int`.
- `EgoDynamicServerConfigLoaderTest` covers default creation, out-of-range repair, and huge integer repair on the plain JUnit classpath.
- `EgoDynamicSynergyConfigLoader` loads `serverconfig/immersive_ego/synergies.toml` from the world root during server startup.
- Missing synergy files are created with an empty graph.
- Configured `synergies.edges` entries use `source -> target : weight`; invalid, malformed, duplicate, unsafe, or cyclic edge lists are repaired to an empty graph.
- Malformed TOML parser failures reset only this dynamic synergy file to an empty graph.
- `EgoDynamicSynergyConfigLoaderTest` covers default file creation, valid configured edges, invalid edge repair, and cyclic graph repair on the plain JUnit classpath.

## Scheduler Foundation

- `EgoSimulationFrame` is an immutable input/output state frame.
- `EgoSimulationFrameCalculator` owns pure frame calculation on the plain JUnit classpath.
- `EgoSimulationSchedule` owns interval-boundary checks.
- `EgoSimulationScheduler` is wired to server player post-tick events, but it only runs when both `enableSimulation` and `scheduler.enabled` are true.
- Current frames can apply an enabled environment snapshot to `thermal_strain`, apply optional respiratory samples to `respiratory_strain`, apply optional hydration demand, apply optional stamina activity cost/regeneration, refresh the inventory weight cache when the encumbrance module is enabled, apply optional encumbrance load pressure to stamina and fatigue, apply optional sleep pressure/recovery, apply optional nutrition balance from MariesLib dietary memory, derive EGO Load and Readiness from the resulting state, persist changes when needed, and refresh display mirrors.

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

## Environment Foundation

- `EgoClimateProfile` stores immutable normalized climate profile pressures.
- `EgoClimateProfileCatalog` defines default temperate, hot dry, hot humid, cold dry, and cold wet profiles.
- `EgoExposureSample`, `EgoSeasonPhase`, `EgoThermalMode`, `EgoThermalSnapshot`, and `EgoThermalSnapshotCalculator` turn profile, season, sky exposure, shelter, and wetness inputs into normalized heat/cold/wetness/exposure pressures.
- `EgoEnvironmentSampler` reads live server biome/weather/player context into exposure samples.
- `EgoEnvironmentCache`, `EgoEnvironmentDebugCounters`, `EgoEnvironmentSnapshot`, and `EgoEnvironmentService` provide player-scoped snapshot caching, counters, scheduler handoff, and debug command readback.
- The environment layer updates player `thermal_strain` only when disabled-by-default simulation and environment gates are enabled.
- `EgoClimateProfileTest` covers validation, derived pressure helpers, immutable defaults, unique ids, and lookup by id.
- `EgoThermalSnapshotCalculatorTest` covers invalid exposure inputs, shelter relief, wet cold winter pressure, and sheltered temperate neutral behavior.
- `EgoEnvironmentCacheTest` and `EgoEnvironmentSamplerTest` cover cache refresh/hit/skip/error/eviction behavior plus deterministic season and climate selection.

## Physiology Foundation

- `EgoHydrationDemand`, `EgoHydrationDemandInput`, `EgoHydrationDemandContext`, and `EgoHydrationDemandCalculator` calculate interval-scaled hydration demand from thermal strain, respiratory strain, fatigue, and hydration efficiency.
- `EgoDrinkProfile` and `EgoDrinkProfileCatalog` define direct vanilla drink profiles for water potions, milk buckets, and honey bottles.
- `EgoDrinkItemResolver` recognizes direct vanilla drink item stacks and excludes non-water and splash potion forms.
- `EgoWaterQuality`, `EgoWaterPurificationMethod`, `EgoWaterPurificationService`, and `EgoWaterPurificationItemResolver` model water quality and recognize charcoal/coal filtering plus campfire/furnace/smoker boiling using existing items.
- `EgoHydrationService` applies demand and drink recovery to server-authoritative `EgoState`.
- `EgoHydrationEvents` listens for `LivingEntityUseItemEvent.Finish` and applies drink recovery only when `enableSimulation` and the physiology module are enabled.
- `EgoPhysiologyModule` centralizes the shared `enableSimulation` plus physiology module gate.
- `EgoRespiratoryPressureCalculator`, `EgoRespiratorySample`, `EgoRespiratoryStrainCalculator`, and `EgoRespirationService` calculate respiratory strain from altitude, depth, submersion, dimension pressure, and respiration efficiency.
- `EgoRespiratorySampler` and `EgoRespirationRuntime` sample server-side player/level state and hand the optional respiratory sample to scheduler frames only when the physiology module is enabled. The sampler exposes dimension-key classification so runtime tests can guard Overworld/Nether/End/other behavior under the Minecraft classpath.
- `EgoStaminaActivityContext`, `EgoStaminaActivityDelta`, `EgoStaminaActivityCalculator`, and `EgoStaminaService` calculate stamina activity cost/regeneration from movement intensity, sprinting, swimming, airborne state, item use, scheduler interval, and stamina capacity.
- `EgoStaminaActivitySampler` and `EgoStaminaRuntime` sample server-side player movement state and hand the optional stamina activity context to scheduler frames only when the physiology module is enabled.
- `EgoEncumbranceModule` gates the default-off encumbrance slice.
- `EgoWeightedStack`, `EgoItemWeightResolver`, `EgoInventoryWeightCalculator`, and `EgoInventoryWeightSnapshot` resolve item-stack weights into load-tolerance-scaled inventory load snapshots.
- `EgoInventoryWeightCache`, `EgoInventoryWeightSampler`, and `EgoInventoryWeightService` cache server-side inventory load snapshots without mutating `EgoState` yet.
- `EgoEncumbranceTier`, `EgoEncumbranceHysteresis`, `EgoEncumbranceContext`, `EgoEncumbranceLoadCalculator`, `EgoEncumbranceRuntime`, and `EgoEncumbranceService` convert cached inventory load into hysteretic load tiers and stamina/fatigue pressure.
- `EgoHydrationDemandCalculatorTest` and `EgoHydrationServiceTest` cover demand math, profile validation, recovery, and clamping.
- `EgoWaterPurificationServiceTest` covers quality improvement, purification item ids, contamination reduction, and quality-adjusted hydration/stress behavior.
- `EgoEncumbranceLoadCalculatorTest` covers hysteresis thresholds, load deltas, service application, clamping, and validation.
- `EgoInventoryWeightCalculatorTest` covers weight heuristics, load ratio/capacity scaling, overburden clamping, cache refresh/hit/error behavior, and validation.
- `EgoRespiratoryStrainCalculatorTest` covers altitude/depth/submersion/dimension pressure mapping, weighting, efficiency scaling, service application, and validation.
- `EgoStaminaActivityCalculatorTest` covers rest recovery, sprint/swim/airborne costs, item-use recovery reduction, capacity scaling, clamping, and validation.
- `drink_item_resolver_recognizes_only_vanilla_drinks` covers drink and purification item recognition in runtime GameTests.

## Sleep Foundation

- `EgoCircadianPhase` maps Minecraft day time to dawn, day, dusk, and night.
- `EgoSleepContext`, `EgoSleepInput`, `EgoSleepDelta`, and `EgoSleepCalculator` model awake sleep-pressure fatigue gain, sleep quality, night sleep recovery, lower-quality day naps, 100-tick vanilla sleep qualification, and 72000-tick phantom spawn eligibility from `Stats.TIME_SINCE_REST`.
- `EgoSleepService` applies sleep pressure/recovery to server-authoritative `EgoState` without adding new persisted schema fields.
- `EgoSleepModule`, `EgoSleepSampler`, and `EgoSleepRuntime` sample server day time, player sleep state, sleep ticks, `Stats.TIME_SINCE_REST`, and the `sleep_recovery` capability attribute behind the default-off sleep module.
- `EgoSleepCalculatorTest` covers phase boundaries, fatigue pressure, sleep quality, nap behavior, service clamping, phantom-rest threshold modeling, and validation.
- `EgoSimulationFrameTest` covers sleep recovery ordering before derived EGO Load/Readiness.
- `minecraft_sleep_api_matches_phantom_rest_model` guards the Minecraft 1.21.1 public sleep/stat assumptions in GameTest.

## Nutrition Foundation

- `EgoMariesLibFacade` isolates all direct MariesLib API use and registers a delegate-backed `MarieLibContext` before value/source registration.
- The facade creates MariesLib's expected `config/immersive_ego/scanner_spec.json` when missing; the bundled fallback resource path is intentionally not packaged and is guarded by `EgoProjectBoundaryTest`.
- `EgoNutritionGroup` defines the six stable nutrition value keys: grains, protein, vegetables, fruit, fats, and minerals.
- `EgoVanillaFoodNutritionCatalog` maps vanilla foods into those groups without custom items, copied assets, or recipes.
- `EgoNutritionEvents` fires MariesLib item-consumption triggers only when core simulation and the default-off nutrition module are enabled.
- `EgoNutritionRuntime`, `EgoNutritionService`, `EgoMealQualityCalculator`, and `EgoDietaryMemory` convert MariesLib readback into meal quality, gut health, and server-authoritative `nutrition_balance`.
- `EgoNutritionGroupTest`, `EgoVanillaFoodNutritionCatalogTest`, `EgoMealQualityCalculatorTest`, `EgoNutritionServiceTest`, and `EgoSimulationFrameTest` cover the pure nutrition layer, including sub-second nutrition balance rate limiting.
- `vanilla_food_catalog_exposes_six_marieslib_nutrition_groups` guards runtime food catalog assumptions in GameTest.

## Psychology and Origins Foundation

- `EgoPsychologyContext`, `EgoPsychologyCalculator`, `EgoRecoveryBudget`, and `EgoPsychologyService` convert fear pressure, pain pressure, stability support, comfort support, and stress resilience into server-authoritative stress, focus, comfort, and recovery capacity changes.
- `EgoInjuryMemoryService` updates persisted recent injury, lingering pain, and recovery debt from current health deficit and recovery capacity.
- `EgoPsychologyRuntime` supplies optional scheduler contexts only when the master simulation gate and default-off psychology module are enabled, and it feeds pain pressure from persistent injury memory.
- `EgoOriginProfileCatalog` defines temperate, arid, cold, highland, and humid origin profiles and maps common biome path families to profiles.
- `EgoOriginsService` assigns a birth-biome-derived origin when the origins module is enabled, advances persistent heat, cold, altitude, and humidity acclimation axes, and applies small strain/comfort benefits before derived EGO Load/Readiness.
- `EgoInjuryMemoryServiceTest`, `EgoPsychologyServiceTest`, `EgoOriginsServiceTest`, and `EgoSimulationFrameTest` cover persistent injury memory, psychology/recovery budget math, origin profile mapping, acclimation movement, origin benefits, and scheduler ordering.

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
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoEncumbranceLoadCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.environment.EgoClimateProfileTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.environment.EgoClimateProfileTest --tests com.oblixorprime.immersiveego.environment.EgoThermalSnapshotCalculatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.environment.EgoEnvironmentCacheTest --tests com.oblixorprime.immersiveego.environment.EgoEnvironmentSamplerTest --tests com.oblixorprime.immersiveego.environment.EgoThermalSnapshotCalculatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoHydrationDemandCalculatorTest --tests com.oblixorprime.immersiveego.physiology.EgoHydrationServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoHydrationDemandCalculatorTest --tests com.oblixorprime.immersiveego.physiology.EgoHydrationServiceTest --tests com.oblixorprime.immersiveego.physiology.EgoWaterPurificationServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoInventoryWeightCalculatorTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoRespiratoryStrainCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoStaminaActivityCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.sleep.EgoSleepCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat test --tests com.oblixorprime.immersiveego.nutrition.* --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest
.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.psychology.EgoPsychologyServiceTest --tests com.oblixorprime.immersiveego.origins.EgoOriginsServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.psychology.EgoInjuryMemoryServiceTest --tests com.oblixorprime.immersiveego.psychology.EgoPsychologyServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest
.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest
.\gradlew.bat --no-daemon test
.\gradlew.bat --no-daemon compileJava
.\gradlew.bat --no-daemon runGameTestServer
.\gradlew.bat --no-daemon clean build
.\scripts\install-mod.ps1
.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180
```

Result: dynamic server config and synergy config tests passed, including huge integer scheduler interval repair, malformed dynamic TOML fallback, invalid/cyclic synergy graph repair, and configured mirror epsilon behavior; the required SERVER config filename guard passed; the project boundary guards passed, including forbidden resources, banned integrations, client-only source references, and optional Apotheosis metadata/source checks; climate profile, thermal snapshot, environment cache, environment sampler, hydration demand, hydration service, water purification, inventory weight, respiratory strain, stamina activity, encumbrance load, sleep calculator, nutrition, injury memory, psychology, and origins tests passed; synergy evaluator/cycle/parser/validator, curve, derived, frame, and schedule tests passed; `runGameTestServer` passed with 23 required tests including attribute catalog integrity, command permission guards, the Minecraft sleep/phantom stat guard, MariesLib nutrition-group catalog guard, origin-state attachment guard, injury-memory attachment guard, attachment mutation readback, player NBT attachment save/load round-trip, death-respawn clone-event attachment copy policy, malformed dynamic config fallback, respiratory dimension mapping/state update, hydration progression, stamina consume/recover progression, sleep recovery progression, awake-night sleep debt progression, day-nap behavior, vanilla-food nutrition progression, live food-consumption event memory/state progression, and psychology runtime injury-memory/state progression; command registration covers `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace`; vanilla drink and purification item recognition passed under the Minecraft classpath; all registered SERVER config files plus both dynamic config files loaded; the MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`; the dedicated-server smoke script passed for `0.1.0-alpha.56` with bootstrap, dynamic config, synergy config, scanner config, vanilla startup completion markers, and no failure matches; the bundled scanner resource path is absent from the built and installed jar; `clean build` passed for `0.1.0-alpha.56`; and `scripts/install-mod.ps1` installed the verified alpha.56 jar into Prism LAB.

## Installed Jar

Use:

```powershell
.\scripts\install-mod.ps1
```

Expected target:

`<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods`

The script writes `build/install-report.json`.

By default it also runs `scripts/install-runtime-deps.ps1`, which installs and verifies pinned runtime dependencies and writes `build/runtime-deps-report.json`. Use `-SkipRuntimeDependencies` only for a deliberate jar-only reinstall. The installers refuse to create a full missing target tree; they accept an existing mods directory or a missing final `mods` folder under an existing parent. Installed-jar matching reads declared `[[mods]]` entries only, so dependency `modId` entries do not cause companion jars to be removed.

Latest verified installed jar:

- `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.56.jar`
- SHA-256: `5bba60ff0b79bd2bfaac7bc89a9ac65fe385740aefdc89066d7bb481e317fd73`
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.

## Important Boundaries

- Do not add Cold Sweat.
- Do not copy Apothic Attributes or Apotheosis assets.
- Do not JarJar MariesLib.
- Do not implement Apothic, MariesLib, or Apotheosis APIs without inspecting the pinned source/JAR first.
- `EgoProjectBoundaryTest` guards against forbidden custom registrations, forbidden resource directories, bundled MariesLib scanner resources, Cold Sweat references, and Tough As Nails references.

## Next Task

Continue toward the next active simulation slice:

1. Run the live Desertborn hot-biome vertical-slice proof in Prism LAB.
2. For the next attempt, use an unlocked controllable desktop or user-assisted live input, then capture `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and Apothic/attribute screenshots after loading a desert biome with simulation modules enabled.
3. Add a live client/server smoke pass with simulation modules enabled.
4. Tune pain/recovery from live playtest evidence and add broader runtime GameTests for full real-world save/load persistence, full live death/respawn, live dimension transfer, broader food-consumption variety and diminishing returns, optional Apotheosis presence, and packaged dedicated-server client-class-load proof.

## 2026-10-13 preservation snapshot

The local alpha.61 worktree was preserved on a separate draft-PR branch. See [snapshot provenance and validation](LOCAL_ALPHA61_SNAPSHOT.md) for included files, privacy adaptations and the fresh build limitation. This entry records preservation only; it does not close runtime proof gates.
