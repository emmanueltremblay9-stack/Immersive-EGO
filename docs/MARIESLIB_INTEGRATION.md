# MariesLib Integration

Status: first integration implemented in `0.1.0-alpha.40` and startup fallback hardened in `0.1.0-alpha.41`.

MariesLib is required as a separate runtime mod and resolves from Modrinth Maven as `maven.modrinth:marieslib:0.1.0-beta.5`.

MariesLib `0.1.0-beta.5` declares `cloth_config` `[15.0.0,)` as a client-side dependency. Prism LAB currently uses Cloth Config API `15.0.140+neoforge`.

## Rules

- Do not JarJar MariesLib.
- Keep all MariesLib imports behind `compat.marieslib`.
- Use only stable API elements after source/JAR inspection.
- Use MariesLib for slow, memory-based source values when its API is suitable.
- Keep high-frequency tick state in Immersive EGO's own server-authoritative state.

## Current Integration

- `EgoMariesLibFacade` is the only package with direct MariesLib imports.
- The facade uses the stable `MarieAPI`, `ValueDefinition`, `ValueSourceTrigger`, `MariePlayerData`, and `ApplicationHistoryView` APIs inspected from `marieslib-0.1.0-beta.5.jar`.
- The facade registers a delegate-backed `MarieLibContext` before value registration because `MarieAPI.registerValue` requires both a registered context and a registration delegate in MariesLib `0.1.0-beta.5`.
- Six nutrition values are registered during Immersive EGO mod construction, before MariesLib freezes mod-only registrations during common setup.
- Vanilla food source classifications are registered through `MarieAPI.registerSourceClassification`.
- The facade creates MariesLib's expected `config/immersive_ego/scanner_spec.json` if it is missing before context registration.
- Server-side food completion fires `ValueSourceTrigger.itemConsumed` only when the default-off nutrition module and core simulation are enabled.
- Dietary memory is read back from MariesLib and converted into Immersive EGO meal quality, gut health, and server-authoritative `nutrition_balance`.

## Next Work

- Add a live client/server smoke pass with core simulation plus environment, physiology, sleep, encumbrance, nutrition, psychology, origins, synergies, and Apothic module gates selectively enabled.
- Keep investigating the MariesLib `ItemScanner` warning if it appears outside GameTest/dev startup.
