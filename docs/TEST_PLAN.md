# Test Plan

## Current Automated Tests

- `EgoMathTest`
- `EgoCurveTest`
- `EgoProjectBoundaryTest`
- `EgoLoadCalculatorTest`
- `EgoAttributesTest`
- `EgoDerivedStateCalculatorTest`
- `EgoDynamicServerConfigLoaderTest`
- `EgoDynamicSynergyConfigLoaderTest`
- `EgoServerModuleConfigsTest`
- `EgoSimulationFrameTest`
- `EgoSimulationScheduleTest`
- `EgoSynergyCycleDetectorTest`
- `EgoSynergyEdgeParserTest`
- `EgoSynergyEvaluatorTest`
- `EgoSynergyValidatorTest`
- `EgoStateTest`

Run:

```powershell
.\gradlew.bat clean build
```

Current GameTests:

- `player_attribute_supplier_contains_every_ego_attribute`
- `mirror_service_updates_display_attributes_without_duplicate_modifiers`
- `server_registers_ego_status_and_debug_state_commands`, including `/ego synergy trace`

Run:

```powershell
.\gradlew.bat runGameTestServer
```

## Required Future Unit Tests

- config validation and migration
- codec round-trip under the NeoForge runtime classpath
- Recovery Budget allocation
- origin modifier resolution
- acclimation gain and decay
- weight precedence
- food-memory diminishing returns
- sleep-quality calculation
- serialization round-trip

## Required Future GameTests

- mod loads on dedicated server
- attribute catalog has unique paths, valid ranges, and correct positive/negative display sentiment
- state attachment persists
- death and respawn policy
- dimension change
- hydration consumption
- stamina cost and recovery
- fatigue and sleep debt progression
- valid sleep recovery
- nap behavior
- nutrition value registration through MariesLib
- origin assignment
- malformed config fallback
- command permissions beyond registration
- optional Apotheosis absence
- no client class load on server
