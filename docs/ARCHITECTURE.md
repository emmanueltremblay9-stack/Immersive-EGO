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
- `psychology`: stress, fear, stability, focus, and comfort.
- `adaptation`: biome origin profiles, acclimation axes, and modifiers.
- `derived`: EGO Load, Readiness, Focus, Comfort, and Recovery Capacity.
- `compat`: isolated Apothic, MariesLib, and Apotheosis integration.
- `client`: Apothic UX extensions, contextual warnings, and debug presentation.

## State Ownership

Each stat has one authoritative owner:

- High-frequency player simulation state: `EgoState`.
- Slow nutrition/source memory where stable API supports it: MariesLib.
- Calculated values: derived package.
- Apothic Attributes display values: mirrors only, never gameplay owners.

## Current Implementation

Current Java code is intentionally minimal:

- `ImmersiveEgo`: mod bootstrap, attachment registration, attribute registration, and config registration.
- `EgoCommands`: `/ego status` and `/ego debug state` server commands.
- `EgoCoreConfig` and `EgoClientConfig`: first safe config specs.
- `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs`: required roadmap SERVER config file registration with default-off module gates.
- `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoDynamicSynergyConfig`, and `EgoDynamicSynergyConfigLoader`: per-world dynamic TOML config foundation under `serverconfig/immersive_ego/`.
- `EgoAttributeCatalog` and `EgoAttributes`: first capability and display-only attribute registry.
- `EgoState`, `EgoStateCodecs`, and `EgoAttachments`: first persisted player state attachment.
- `EgoAttributeMirrorService`: server-side display mirror updater with stable transient modifier IDs.
- `EgoSimulationFrame`, `EgoSimulationSchedule`, and `EgoSimulationScheduler`: immutable frames and a double-gated scheduler foundation.
- `EgoCurvePoint` and `EgoCurve`: normalized validated curve primitive for config-driven simulation response curves.
- `EgoSynergyEdge`, `EgoSynergyEdgeParser`, `EgoSynergyCycleDetector`, `EgoSynergyValidator`, and `EgoSynergyEvaluator`: first declarative synergy edge parser, cycle detector, catalog validator, topological evaluator, TOML-backed graph input, and evaluation trace.
- `EgoDerivedStateCalculator`: first derived-state bridge for scheduler-driven EGO Load and Readiness updates.
- `EgoMath` and `EgoLoadCalculator`: pure deterministic calculation seed.
