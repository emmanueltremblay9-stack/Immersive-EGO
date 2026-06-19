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

- `ImmersiveEgo`: mod bootstrap and config registration.
- `EgoCoreConfig` and `EgoClientConfig`: first safe config specs.
- `EgoMath` and `EgoLoadCalculator`: pure deterministic calculation seed.
