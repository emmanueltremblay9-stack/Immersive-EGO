# Immersive EGO

Immersive EGO is a Minecraft 1.21.1 NeoForge survival-RPG simulation mod for Java 21.

The project goal is one server-authoritative organism simulation covering environment, hydration, respiration, stamina, sleep, nutrition, pain, psychology, biome origin, acclimation, EGO Load, Readiness, Focus, Comfort, and Recovery Capacity.

Current status: Phase 0 bootstrap. The repository builds, pins the initial dependency set, registers only bootstrap config, includes the project logo, and contains starter pure Java tests for normalized math and EGO Load aggregation. Gameplay simulation is disabled by default until the vertical slice is implemented.

## Build

```powershell
.\gradlew.bat clean build
```

The runtime artifact is written to `build/libs/immersive_ego-0.1.0-alpha.3.jar`.

## Required Runtime Mods

- NeoForge 21.1.228 or newer for Minecraft 1.21.1
- Apothic Attributes 2.9.1 or newer
- MariesLib 0.1.0-beta.5 or newer, installed separately
- Cloth Config 15.0.140 or newer on clients because MariesLib declares it client-side
- Placebo as required transitively by Apothic Attributes

Apotheosis 8.5.4 or newer is optional.

## Development Rules

- No custom blocks, items, entities, fluids, machine systems, or copied third-party assets.
- All server-authoritative gameplay state must stay on the server.
- Important character statistics must be visible through Apothic Attributes once Phase 1 is implemented.
- MariesLib must remain a separate runtime install and must not be JarJar'd.
- Cold Sweat is intentionally absent.

See `TASKS.md` for the implementation roadmap and `docs/CODEX_HANDOFF.md` for the current next task.
