# MariesLib Integration

Status: planned.

MariesLib is required as a separate runtime mod and resolves from Modrinth Maven as `maven.modrinth:marieslib:0.1.0-beta.5`.

MariesLib `0.1.0-beta.5` declares `cloth_config` `[15.0.0,)` as a client-side dependency. Prism LAB currently uses Cloth Config API `15.0.140+neoforge`.

## Rules

- Do not JarJar MariesLib.
- Keep all MariesLib imports behind `compat.marieslib`.
- Use only stable API elements after source/JAR inspection.
- Use MariesLib for slow, memory-based source values when its API is suitable.
- Keep high-frequency tick state in Immersive EGO's own server-authoritative state.

## Next Work

- Inspect `kgbcupcake/MarieLib` main commit `62aaee5eee001c0ab89115bb8c6aaba632ebd331`.
- Implement `MarieLibFacade`.
- Register nutrition groups only after the official registration window and API are confirmed.
