# Immersive EGO

Immersive EGO is a Minecraft 1.21.1 NeoForge survival-RPG simulation mod for Java 21.

The project goal is one server-authoritative organism simulation covering environment, hydration, respiration, stamina, sleep, nutrition, pain, psychology, biome origin, acclimation, EGO Load, Readiness, Focus, Comfort, and Recovery Capacity.

Current status: alpha Phase 11 hardening. The repository builds, pins the initial dependency set, registers bootstrap and roadmap SERVER config files, registers capability and display-only EGO attributes, registers persisted `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` attachments, mirrors server state into display attributes with stable modifier IDs, loads the dynamic `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml` foundations, exposes `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace`, and includes immutable simulation frames, scheduler foundation, normalized curves, synergy parsing/validation/evaluation, TOML-backed synergy graph loading, derived EGO Load/Readiness, climate profiles, live environment sampling, hydration, respiration, stamina, encumbrance, sleep, MariesLib-backed nutrition, psychology/recovery budget, persistent injury memory, biome origin, acclimation foundations, command permission guards, optional-Apotheosis absence guards, server-side source boundary guards, and a repeatable dedicated-server startup smoke. Gameplay simulation remains disabled by default; if the relevant gates are manually enabled, the current active slices can update thermal strain, respiratory strain, hydration, stamina, fatigue, sleep recovery, nutrition balance, persistent injury pain pressure, stress/focus/comfort/recovery capacity, origin acclimation benefits, and then derive EGO Load and Readiness from server-authoritative state.

## Build

```powershell
.\gradlew.bat clean build
```

The runtime artifact is written to `build/libs/immersive_ego-0.1.0-alpha.56.jar`.

## Required Runtime Mods

- NeoForge 21.1.228 or newer for Minecraft 1.21.1
- Apothic Attributes 2.9.1 or newer
- MariesLib 0.1.0-beta.5 or newer, installed separately
- Cloth Config 15.0.140 or newer on clients because MariesLib declares it client-side
- Placebo as required transitively by Apothic Attributes

Apotheosis 8.5.4 or newer is optional.

## Prism LAB Install

```powershell
.\scripts\install-mod.ps1
```

The installer verifies and installs the pinned runtime dependency jars by default, then installs Immersive EGO and writes `build/runtime-deps-report.json` and `build/install-report.json`. It refuses to create a full missing target tree; only an existing mods directory or a missing final `mods` folder under an existing parent is accepted.

## Dedicated Server Smoke

```powershell
.\scripts\smoke-dedicated-server.ps1 -AcceptEula
```

The smoke starts the Gradle `runServer` profile, waits for required startup markers, terminates the dev server after success, and writes `build/dedicated-server-smoke-report.json`.

## Current Commands

- `/ego status` shows the current server-authoritative EGO summary for the executing player.
- `/ego debug state` shows all normalized display mirror values and requires permission level 2.
- `/ego debug environment` shows the current cached environment sample and requires permission level 2.
- `/ego debug origin` shows the persisted origin id and acclimation axes and requires permission level 2.
- `/ego synergy trace` prints a read-only synergy trace from configured edges when present, otherwise from the built-in sample graph, and requires permission level 2.

## Current Server Config

- `config/immersive_ego-core.toml` and `config/immersive_ego-client.toml` are NeoForge config files.
- `config/immersive_ego-environment.toml`, `config/immersive_ego-physiology.toml`, `config/immersive_ego-sleep.toml`, `config/immersive_ego-nutrition.toml`, `config/immersive_ego-psychology.toml`, `config/immersive_ego-origins.toml`, `config/immersive_ego-encumbrance.toml`, `config/immersive_ego-synergies.toml`, `config/immersive_ego-apothic.toml`, and `config/immersive_ego-performance.toml` are registered SERVER config files with default-off module gates.
- `serverconfig/immersive_ego/simulation.toml` is generated per world and stores the opt-in scheduler toggle, scheduler interval, and display mirror update epsilon.
- `serverconfig/immersive_ego/synergies.toml` is generated per world and stores optional declarative synergy edges in `source -> target : weight` format. Missing, malformed, invalid, or cyclic lists are repaired to an empty graph.

## Development Rules

- No custom blocks, items, entities, fluids, machine systems, or copied third-party assets.
- All server-authoritative gameplay state must stay on the server.
- Important character statistics must be visible through Apothic Attributes once Phase 1 is implemented.
- MariesLib must remain a separate runtime install and must not be JarJar'd.
- Cold Sweat is intentionally absent.

See `TASKS.md` for the implementation roadmap and `docs/CODEX_HANDOFF.md` for the current next task.
