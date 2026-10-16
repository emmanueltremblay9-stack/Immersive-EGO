# Test Plan

## Current Automated Tests

- `EgoMathTest`
- `EgoCurveTest`
- `EgoProjectBoundaryTest`
- `EgoLoadCalculatorTest`
- `EgoAttributesTest`
- `EgoClimateProfileTest`
- `EgoEnvironmentCacheTest`
- `EgoEnvironmentSamplerTest`
- `EgoHydrationDemandCalculatorTest`
- `EgoHydrationServiceTest`
- `EgoInjuryMemoryServiceTest`
- `EgoInventoryWeightCalculatorTest`
- `EgoRespiratoryStrainCalculatorTest`
- `EgoStaminaActivityCalculatorTest`
- `EgoWaterPurificationServiceTest`
- `EgoThermalSnapshotCalculatorTest`
- `EgoDerivedStateCalculatorTest`
- `EgoDynamicServerConfigLoaderTest`
- `EgoDynamicSynergyConfigLoaderTest`
- `EgoEncumbranceLoadCalculatorTest`
- `EgoMealQualityCalculatorTest`
- `EgoNutritionGroupTest`
- `EgoNutritionServiceTest`
- `EgoOriginsServiceTest`
- `EgoPsychologyServiceTest`
- `EgoServerModuleConfigsTest`
- `EgoSleepCalculatorTest`
- `EgoSimulationFrameTest`
- `EgoSimulationScheduleTest`
- `EgoSynergyCycleDetectorTest`
- `EgoSynergyEdgeParserTest`
- `EgoSynergyEvaluatorTest`
- `EgoSynergyValidatorTest`
- `EgoStateTest`
- `EgoVanillaFoodNutritionCatalogTest`

Run:

```powershell
.\gradlew.bat clean build
```

Current regression coverage:

- `EgoProjectBoundaryTest.bundledMariesLibScannerResourcesAreNotPackaged` guards against packaging `data/immersive_ego/immersive_ego/scanner` or `scanner_spec.json`.
- `EgoProjectBoundaryTest.mainSourcesDoNotReferenceClientOnlyClasses` guards against accidental client-only class references in main server-loaded sources.
- `EgoProjectBoundaryTest.apotheosisMetadataStaysOptionalAndSourcesDoNotRequireIt` guards optional Apotheosis absence by checking metadata and preventing hard source references.
- `EgoNutritionServiceTest.nutritionBalanceRateLimitScalesBelowOneSecond` guards one-tick nutrition updates against full-second over-application.
- `EgoDynamicServerConfigLoaderTest.clampsHugeIntegerValuesBeforeNarrowing` guards scheduler interval repair against integer wraparound.
- `EgoDynamicServerConfigLoaderTest.repairsMalformedSimulationTomlToDefaults` guards malformed dynamic simulation TOML fallback.
- `EgoDynamicSynergyConfigLoaderTest.repairsMalformedSynergyTomlToEmpty` guards malformed dynamic synergy TOML fallback.
- `EgoAttributeMirrorServiceTest.mirrorUpdatesRespectConfiguredDeltaThreshold` guards configured mirror-update epsilon behavior.
- `EgoInjuryMemoryServiceTest` covers persistent injury recording, lingering pain pressure after health recovery, recovery decay, and context validation.
- `EgoPsychologyServiceTest` covers fear/pain pressure, stability/comfort recovery, and recovery-budget clamping.
- `EgoOriginsServiceTest` covers default biome-profile mapping, origin assignment seeding, acclimation gain, and acclimation benefits.

Current GameTests:

- `attribute_catalog_paths_are_unique_bounded_and_sentimented`
- `player_attribute_supplier_contains_every_ego_attribute`
- `drink_item_resolver_recognizes_only_vanilla_drinks`, including purification item recognition
- `minecraft_sleep_api_matches_phantom_rest_model`
- `mirror_service_updates_display_attributes_without_duplicate_modifiers`
- `server_registers_ego_status_and_debug_state_commands`, including `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace`
- `debug_and_synergy_commands_require_permission_level_two`
- `injury_memory_attachment_defaults_to_clear`
- `state_origin_and_injury_attachments_round_trip_on_mock_player`
- `state_origin_and_injury_attachments_survive_player_nbt_round_trip`
- `state_origin_and_injury_attachments_copy_on_death_clone_event`
- `hydration_progression_reduces_server_authoritative_state`
- `food_consumption_event_updates_marieslib_memory_and_server_state`
- `psychology_runtime_records_injury_and_updates_server_state`
- `respiratory_dimension_mapping_updates_server_state`
- `malformed_dynamic_configs_repair_to_safe_defaults`
- `sleep_progression_recovers_server_authoritative_state`
- `nutrition_progression_from_vanilla_food_updates_server_state`
- `stamina_progression_consumes_and_recovers_server_state`
- `sleep_debt_progression_increases_server_fatigue`
- `nap_progression_recovers_less_fatigue_than_night_sleep`
- `origin_state_attachment_defaults_to_unassigned`
- `vanilla_food_catalog_exposes_six_marieslib_nutrition_groups`

Run:

```powershell
.\gradlew.bat runGameTestServer
```

## Dedicated Server Smoke

Run:

```powershell
.\scripts\smoke-dedicated-server.ps1 -AcceptEula
```

The smoke starts the Gradle `runServer` profile, waits for Immersive EGO bootstrap, dynamic simulation config, dynamic synergy config, MariesLib scanner config, and vanilla dedicated-server completion markers, then terminates the dev server after success. It treats generic Java failures and specific `net.minecraft.client` class-load signatures as failures. It writes `build/dedicated-server-smoke-report.json` plus stdout/stderr logs under `build/`.

## Required Future Unit Tests

- config validation and migration
- codec round-trip under the NeoForge runtime classpath
- persistent injury-memory tuning from live playtest evidence
- origin modifier balance from live playtest evidence
- weight precedence
- food-memory diminishing returns
- serialization round-trip

## Required Future GameTests

- full state attachment save/load persistence across a real world restart
- full live death/respawn flow beyond clone-event attachment policy
- live dimension transfer
- broader live food-consumption variety and diminishing-return scenarios
- live origin assignment across multiple biome starts
- malformed NeoForge SERVER module config fallback beyond dynamic files, if release scope requires it
- optional Apotheosis presence
- no client class load on server under a packaged dedicated-server install
