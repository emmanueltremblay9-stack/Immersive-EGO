# Codex Handoff

## Current State

Repository is bootstrapped and buildable as a NeoForge 1.21.1 / Java 21 project. The mod id is `immersive_ego`, package root is `com.oblixorprime.immersiveego`, and version is `0.1.0-alpha.4`.

The project currently registers only bootstrap server/client config and safe event hooks. No custom blocks, items, entities, fluids, menus, workstations, or copied third-party assets are registered.

## Verified Build

Last verified command:

```powershell
.\gradlew.bat clean build
```

Result: passed.

## Installed Jar

Use:

```powershell
.\scripts\install-mod.ps1
```

Expected target:

`C:\Users\Emmanuel Tremblay\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods`

The script writes `build/install-report.json`.

By default it also runs `scripts/install-runtime-deps.ps1`, which installs and verifies pinned runtime dependencies and writes `build/runtime-deps-report.json`. Use `-SkipRuntimeDependencies` only for a deliberate jar-only reinstall.

## Important Boundaries

- Do not add Cold Sweat.
- Do not copy Apothic Attributes or Apotheosis assets.
- Do not JarJar MariesLib.
- Do not implement Apothic, MariesLib, or Apotheosis APIs without inspecting the pinned source/JAR first.

## Next Task

Inspect the Apothic Attributes 2.9.1 source and implement the Phase 1 attribute registration foundation:

1. Register capability attributes with sane min/default/max values.
2. Register display-only mirror attributes.
3. Add tags or metadata needed to keep display-only attributes out of future affix generation.
4. Add tests or GameTests where possible.
