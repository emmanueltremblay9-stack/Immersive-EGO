# Immersive EGO 🧬

Immersive EGO is a configurable survival-RPG organism simulation mod for Minecraft 1.21.1 on NeoForge.

It links environment, exposure, hydration, respiration, stamina, fatigue, sleep, nutrition, gut health, psychology, pain, recovery, biome origins, acclimation, EGO Load, Readiness, Focus, Comfort, and Recovery Capacity into one deterministic server-authoritative simulation.

## Target

- Minecraft: `1.21.1`
- NeoForge: `21.1.x`
- Java: `21`
- Mod ID: `immersive_ego`
- Package: `com.oblixorprime.immersiveego`
- License: MIT

## Dependencies

Required:

- Apothic Attributes
- MariesLib

Transitive/runtime:

- Placebo, through Apothic Attributes unless the resolved build requires a direct declaration.

Optional:

- Apotheosis

Forbidden:

- Cold Sweat dependency or copied implementation
- Copied All Rights Reserved assets
- GPL code copied into the MIT repository
- Custom blocks, items, entities, fluids, machines, or Blockbench models

## Design

Immersive EGO avoids extra HUD clutter by surfacing important character statistics through Apothic Attributes. The authoritative state belongs to the server-side EGO state attachment and MariesLib-backed long-term values where appropriate. Display-only attributes mirror values for visibility and must never drive gameplay.

## First playable slice

A Desertborn player enters a hot dry biome, carries a heavy inventory, sprints, becomes hot and dehydrated, rests in shelter, eats a balanced meal, and sleeps. Completion requires persistence, Apothic mirrors, config-driven coefficients, and `/ego synergy trace`.

## Build

Use the official NeoForge 1.21.1 MDK/ModDevGradle conventions. After dependency audit:

```bash
./gradlew build
./gradlew test
./gradlew runServer
```

On Windows:

```powershell
.\gradlew.bat build
.\gradlew.bat test
.\gradlew.bat runServer
```

Do not report a successful build until the command was executed.
