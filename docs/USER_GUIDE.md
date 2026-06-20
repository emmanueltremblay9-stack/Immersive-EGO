# User Guide

Immersive EGO is not gameplay-complete yet. The current build is an early Phase 2 foundation.

## Installing the Current Jar

Install the jar with its required runtime dependencies:

- NeoForge for Minecraft 1.21.1
- Apothic Attributes
- Placebo
- MariesLib
- Cloth Config API on clients

The current jar loads bootstrap code, config, the roadmap SERVER config files, the first Apothic-visible EGO attribute set, a persisted server-side `EgoState`, a mirror update service that reflects that state into display-only attributes, the dynamic per-world `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml` loaders, and a disabled-by-default scheduler foundation with the first EGO Load/Readiness derivation.

Available commands:

- `/ego status` shows the player's current EGO summary.
- `/ego debug state` shows every normalized display value and requires permission level 2.
- `/ego synergy trace` shows a read-only synergy trace from configured edges when present, otherwise from the built-in sample graph, and requires permission level 2.

The survival simulation, origins, sleep, nutrition, and recovery systems are planned but not implemented yet.

## Current Config

`enableSimulation` defaults to `false`. Leave it disabled for normal playtesting unless you explicitly want to test the current scheduler slice.

Roadmap SERVER module configs for environment, physiology, sleep, nutrition, psychology, origins, encumbrance, synergies, Apothic, and performance are registered with `enabled=false` and `debugLogging=false`. These files are placeholders for future slices and do not activate gameplay by themselves.

The dynamic per-world file `serverconfig/immersive_ego/simulation.toml` is created on server startup. Its scheduler values are range-repaired on load. If both `enableSimulation` and `scheduler.enabled` are turned on, the current scheduler derives EGO Load and Readiness from existing server state.

The dynamic per-world file `serverconfig/immersive_ego/synergies.toml` is also created on server startup. It stores optional `synergies.edges` entries using `source -> target : weight`. Missing, malformed, invalid, or cyclic lists are repaired to an empty graph.

## Expected Gameplay Impact

No complete gameplay simulation yet. This is intentional; unfinished systems must not silently alter gameplay. Current display attributes mirror the persisted server state, which defaults to neutral values until future systems update it.
