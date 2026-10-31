# CurseForge Description Draft

## Short Summary

Immersive EGO is a configurable server-authoritative survival-RPG simulation foundation for Minecraft 1.21.1 on NeoForge.

## Description

Immersive EGO connects body, mind, environment, and adaptation systems into one configurable EGO simulation. The long-term goal is to make harsh worlds feel coherent instead of arbitrary: heat, hydration, respiration, stamina, encumbrance, sleep, nutrition, pain, psychology, biome origin, acclimation, EGO Load, Readiness, Focus, Comfort, and Recovery Capacity all feed the same server-owned state model.

The current alpha is intentionally conservative. Gameplay systems are behind config gates, and the project avoids custom blocks, items, fluids, entities, workstations, machines, Blockbench models, and copied third-party assets. Immersive EGO uses existing Minecraft play context and separate dependency mods instead of bundling or copying other projects.

## Current Alpha Status

Version: 0.1.0-alpha.56

Target:

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21

Implemented and automatically tested alpha foundations include:

- Server-authoritative EGO player state and persisted attachments.
- Config-gated simulation scheduler.
- Dynamic per-world simulation and synergy config loading.
- EGO Load and Readiness derived state.
- Environment and thermal strain sampling.
- Hydration demand, drink recognition, and water quality services.
- Respiratory strain for altitude, depth, submersion, and dimension context.
- Stamina, encumbrance, inventory weight, and fatigue pressure.
- Sleep pressure, recovery, naps, sleep quality, and phantom-rest assumptions.
- MariesLib-backed nutrition groups, dietary memory, meal quality, and gut health.
- Persistent injury memory, pain pressure, stress, focus, comfort, and recovery capacity.
- Biome origin assignment and acclimation axes.
- Local Apothic Attributes capability/display attributes and display mirrors.
- Debug/status commands for inspection and tracing.

## Required Dependencies

- Apothic Attributes 2.9.1 or compatible 1.21.1 build
- Placebo 9.9.1 or compatible 1.21.1 build
- MariesLib 0.1.0-beta.5
- Curios API and Cloth Config API are required at runtime through the dependency stack used by Apothic Attributes and MariesLib.

## Optional Compatibility Target

- Apotheosis 8.5.4 is treated as an optional compatibility target. Absence is tested; presence still needs live runtime proof before release claims are expanded.

## Verification Status

The current alpha.56 build has passed:

- Java/unit tests.
- 23 required NeoForge GameTests.
- Clean Gradle build.
- Dedicated-server startup smoke.
- Prism LAB install verification with source/installed SHA-256 match.
- Runtime dependency hash/readback verification.
- Live Apothic Attributes UI proof showing EGO rows in the native `Attributes` panel; screenshot: `docs/proof/apothic-live-ui-alpha56-20260622-060914.png`.
- Boundary checks for forbidden assets, forbidden registrations, bundled scanner resources, and banned integrations.

## Known Release Gaps

This draft must not be used to announce a finished public release until the remaining release gates are complete:

- Live Desertborn hot-biome vertical-slice playtest.
- Live pain/recovery and synergy tuning from real play evidence.
- Optional Apotheosis-present runtime proof.
- Final screenshots, changelog, dependency list, and release evidence pass.

## Project Boundaries

Immersive EGO does not add custom blocks, items, entities, fluids, machines, workstations, or copied third-party assets.

Immersive EGO does not bundle MariesLib, Apothic Attributes, Placebo, Apotheosis, Curios, or Cloth Config. Those remain separate dependencies.

No Apothic Attributes, Apotheosis, Cold Sweat, Tough As Nails, or other third-party mod textures, models, sounds, language files, GUI atlases, or logos are copied into this repository.

## Credits And License

Immersive EGO is MIT-licensed original project work by Emmanuel Tremblay with Codex-assisted implementation.

Immersive EGO depends on separate projects including NeoForge, Apothic Attributes, Placebo, MariesLib, Curios API, and Cloth Config API. Optional compatibility targets include Apotheosis. Immersive EGO is not affiliated with or endorsed by those projects.

See `THIRD_PARTY_NOTICES.md`, `docs/LICENSE_MATRIX.md`, `docs/CREDITS.md`, and `docs/SOURCE_PROVENANCE.md` for current provenance and dependency notes.

## Suggested CurseForge Metadata

- Game version: Minecraft 1.21.1
- Mod loader: NeoForge
- Java: 21
- Status: Alpha / release candidate only after live proof gates pass
- Categories: Adventure and RPG, Utility and QoL, Server Utility
- Required dependencies: Apothic Attributes, Placebo, MariesLib
- Additional runtime dependency stack: Curios API, Cloth Config API
- Optional dependency/compatibility target: Apotheosis
