# Architecture

Immersive EGO is organized around one server-authoritative simulation frame.

## Package Plan

- `api`: stable public contracts after the core model settles.
- `registry`: attributes, attachments, data components, and payload registration.
- `config`: ModConfigSpec files, dynamic TOML loaders, validation, migration, presets, and reload.
- `data`: persisted EgoState, bounded memories, migrations, and codecs.
- `simulation`: scheduler, immutable frames, curves, synergies, and recovery budget.
- `environment`: climate, exposure, shelter, wetness, and cached snapshots.
- `physiology`: hydration, respiration, stamina, fatigue, encumbrance, pain, and recovery.
- `sleep`: circadian phase, sleep pressure, quality, naps, and safety.
- `nutrition`: group classification, dietary memory, meal quality, gut health, and contamination.
- `psychology`: stress, fear, persistent injury memory, stability, focus, and comfort.
- `origins`: biome origin profiles, persisted origin state, acclimation axes, and modifiers.
- `derived`: EGO Load, Readiness, Focus, Comfort, and Recovery Capacity.
- `compat`: isolated Apothic, MariesLib, and Apotheosis integration.
- `client`: Apothic UX extensions, contextual warnings, and debug presentation.

## State Ownership

Each stat has one authoritative owner:

- High-frequency player simulation state: `EgoState`.
- Origin and slow acclimation identity: `EgoOriginState`.
- Persistent pain and recovery memory: `EgoInjuryMemory`.
- Slow nutrition/source memory where stable API supports it: MariesLib.
- Calculated values: derived package.
- Apothic Attributes display values: mirrors only, never gameplay owners.

## Current Implementation

Current Java code is intentionally minimal:

- `ImmersiveEgo`: mod bootstrap, attachment registration, attribute registration, and config registration.
- `EgoCommands`: `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace` server commands.
- `EgoCoreConfig` and `EgoClientConfig`: first safe config specs.
- `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs`: required roadmap SERVER config file registration with default-off module gates.
- `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoDynamicSynergyConfig`, and `EgoDynamicSynergyConfigLoader`: per-world dynamic TOML config foundation under `serverconfig/immersive_ego/`.
- `EgoAttributeCatalog` and `EgoAttributes`: first capability and display-only attribute registry.
- `EgoState`, `EgoStateCodecs`, `EgoOriginState`, `EgoOriginStateCodecs`, `EgoInjuryMemory`, `EgoInjuryMemoryCodecs`, and `EgoAttachments`: persisted player state attachments.
- `EgoAttributeMirrorService`: server-side display mirror updater with stable transient modifier IDs.
- `EgoSimulationFrame`, `EgoSimulationFrameCalculator`, `EgoSimulationSchedule`, and `EgoSimulationScheduler`: immutable frames, pure frame calculation, and a double-gated scheduler foundation.
- `EgoCurvePoint` and `EgoCurve`: normalized validated curve primitive for config-driven simulation response curves.
- `EgoSynergyEdge`, `EgoSynergyEdgeParser`, `EgoSynergyCycleDetector`, `EgoSynergyValidator`, and `EgoSynergyEvaluator`: first declarative synergy edge parser, cycle detector, catalog validator, topological evaluator, TOML-backed graph input, and evaluation trace.
- `EgoClimateProfile` and `EgoClimateProfileCatalog`: immutable climate profile primitives and default profile catalog for the first environment slice.
- `EgoExposureSample`, `EgoSeasonPhase`, `EgoThermalMode`, `EgoThermalSnapshot`, and `EgoThermalSnapshotCalculator`: pure thermal snapshot inputs and normalized pressure calculation.
- `EgoEnvironmentSampler`, `EgoEnvironmentCache`, `EgoEnvironmentDebugCounters`, `EgoEnvironmentSnapshot`, and `EgoEnvironmentService`: live server-side climate sampling, player-scoped cache refresh, debug counters, and scheduler handoff for environment-derived thermal strain.
- `EgoPhysiologyModule`, `EgoEncumbranceModule`, `EgoHydrationDemand`, `EgoHydrationDemandInput`, `EgoHydrationDemandContext`, `EgoHydrationDemandCalculator`, `EgoDrinkProfile`, `EgoDrinkProfileCatalog`, `EgoDrinkItemResolver`, `EgoWaterQuality`, `EgoWaterPurificationMethod`, `EgoWaterPurificationService`, `EgoWaterPurificationItemResolver`, `EgoHydrationService`, `EgoHydrationEvents`, `EgoRespiratoryDimension`, `EgoRespiratorySample`, `EgoRespiratoryPressureCalculator`, `EgoRespiratoryStrainCalculator`, `EgoRespiratorySampler`, `EgoRespirationRuntime`, `EgoRespirationService`, `EgoStaminaActivityContext`, `EgoStaminaActivityDelta`, `EgoStaminaActivityCalculator`, `EgoStaminaActivitySampler`, `EgoStaminaRuntime`, `EgoStaminaService`, `EgoWeightedStack`, `EgoItemWeightResolver`, `EgoInventoryWeightCalculator`, `EgoInventoryWeightSnapshot`, `EgoInventoryWeightCache`, `EgoInventoryWeightSampler`, `EgoInventoryWeightService`, `EgoEncumbranceTier`, `EgoEncumbranceHysteresis`, `EgoEncumbranceContext`, `EgoEncumbranceLoadCalculator`, `EgoEncumbranceRuntime`, and `EgoEncumbranceService`: first physiology/encumbrance module gates, hydration demand/recovery primitives, vanilla drink recognition, water quality, existing-item purification recognition, gated server-side drink handling, respiratory strain from altitude, depth, submersion, and dimension context, stamina activity cost/regeneration, inventory weight resolving/caching, encumbrance hysteresis, and stamina/fatigue load synergies.
- `EgoCircadianPhase`, `EgoSleepContext`, `EgoSleepInput`, `EgoSleepDelta`, `EgoSleepCalculator`, `EgoSleepService`, `EgoSleepModule`, `EgoSleepSampler`, and `EgoSleepRuntime`: first sleep/fatigue module gate, circadian phase, sleep pressure, sleep quality, nap recovery, vanilla `time_since_rest` phantom stat modeling, and scheduler handoff using server day time and player sleep state.
- `EgoNutritionGroup`, `EgoFoodNutritionProfile`, `EgoVanillaFoodNutritionCatalog`, `EgoDietaryMemory`, `EgoMealQuality`, `EgoMealQualityCalculator`, `EgoNutritionContext`, `EgoNutritionService`, `EgoNutritionModule`, `EgoNutritionRuntime`, `EgoNutritionEvents`, and `EgoMariesLibFacade`: first nutrition module gate, MariesLib stable API facade, six EGO nutrition values, vanilla food source classifications, server-side food consumption triggers, dietary memory readback, meal quality, gut health, and scheduler handoff into `nutrition_balance`.
- `EgoInjuryMemory`, `EgoInjuryMemoryContext`, `EgoInjuryMemoryService`, `EgoPsychologyContext`, `EgoPsychologyDelta`, `EgoRecoveryBudget`, `EgoPsychologyCalculator`, `EgoPsychologyService`, `EgoPsychologyModule`, and `EgoPsychologyRuntime`: first psychology module gate, persistent injury memory, lingering pain pressure, stability/comfort support, focus/comfort/stress updates, and recovery-budget allocation.
- `EgoOriginProfile`, `EgoOriginProfileCatalog`, `EgoOriginState`, `EgoOriginStateCodecs`, `EgoAcclimationContext`, `EgoAcclimationCalculator`, `EgoOriginsModule`, `EgoOriginsService`, and `EgoOriginsRuntime`: persisted origin identity, default biome profile mapping, heat/cold/altitude/humidity acclimation axes, and scheduler handoff for origin benefits.
- `EgoDerivedStateCalculator`: first derived-state bridge for scheduler-driven EGO Load and Readiness updates.
- `EgoMath` and `EgoLoadCalculator`: pure deterministic calculation seed.
