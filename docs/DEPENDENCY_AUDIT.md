# Dependency Audit

Audit date: 2026-06-19.

## Pinned Build Baseline

| Component | Version | Coordinate / Source | License | Reason |
| --- | --- | --- | --- | --- |
| Minecraft | 1.21.1 | Mojang / NeoForge toolchain | Proprietary game dependency | Target game version |
| NeoForge | 21.1.233 | `net.neoforged:neoforge:21.1.233` | LGPL-2.1-only | Loader and modding API |
| Parchment | 2024.11.17 | `org.parchmentmc.data:parchment-1.21.1:2024.11.17` | see upstream | Mappings and parameter names |
| Apothic Attributes | 2.9.1 | `dev.shadowsoffire:ApothicAttributes:1.21.1-2.9.1` | MIT code, All Rights Reserved assets | Required attribute UI/stat dependency |
| Placebo | 9.9.1 | `dev.shadowsoffire:Placebo:1.21.1-9.9.1` | MIT | Required transitively by Apothic Attributes |
| MariesLib | 0.1.0-beta.5 | `maven.modrinth:marieslib:0.1.0-beta.5` | LGPL-3.0-only | Required separate runtime library |
| Apotheosis | 8.5.4 | source branch inspected, optional runtime metadata only | MIT code, All Rights Reserved assets | Optional future compatibility |
| Curios API | 9.5.1+1.21.1 | transitive from Apothic Attributes | LGPL-3.0 | Development/runtime transitive dependency; not used directly |

## Source Evidence

| Project | Branch / version evidence |
| --- | --- |
| NeoForge MDK | `NeoForgeMDKs/MDK-1.21.1-ModDevGradle` main commit `ff9c5e847cb470ee2fb63d4309f7a25a61864ba5` |
| Apothic Attributes | `Shadows-of-Fire/Apothic-Attributes` branch `1.21`, commit `666900510edd2c5289d5e29312182e934b70a381`; upstream `gradle.properties` lists `mod_version=2.9.1` and `neo_version=21.1.187` |
| Placebo | `Shadows-of-Fire/Placebo` branch `1.21`, commit `621c883c49c1d025b88fb626eabf0a84891b34d1`; upstream `gradle.properties` lists `mod_version=9.9.1` |
| Apotheosis | `Shadows-of-Fire/Apotheosis` branch `1.21`, commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370`; upstream `gradle.properties` lists `mod_version=8.5.4` |
| MariesLib | `kgbcupcake/MarieLib` main commit `62aaee5eee001c0ab89115bb8c6aaba632ebd331`; Modrinth version `0.1.0-beta.5`, published 2026-06-19 |

## Repository Decisions

- NeoForge is pinned to `21.1.233`, the current MDK value inspected on 2026-06-19.
- Mod metadata requires NeoForge `[21.1.228,)` because MariesLib declares NeoForge `21.1.228+` in its current source metadata.
- Apothic Attributes and Placebo are compiled as dependencies from Shadows' Maven.
- MariesLib resolves from Modrinth Maven and remains a separate mod dependency.
- Apotheosis is not on the compile classpath yet because no optional compat API is implemented in this phase.
- Curios repository is configured only because Apothic Attributes pulls Curios transitively.

## API Surface Used

- NeoForge: `@Mod`, mod event bus, global event bus, config registration, server start event.
- Apothic Attributes: no direct API calls yet.
- Placebo: no direct API calls.
- MariesLib: no direct API calls yet.
- Apotheosis: no direct API calls yet.
- Curios: no direct API calls.

## Open Audit Items

- Inspect the pinned Apothic Attributes source before implementing attributes, modifier sources, or client mixins.
- Inspect MariesLib stable API annotations before implementing `MarieLibFacade`.
- Inspect Apotheosis data formats before adding optional examples or compatibility code.
