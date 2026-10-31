# Dependency Audit

Audit date: 2026-06-21  
Target: Minecraft 1.21.1 / NeoForge 21.1.x / Java 21

## Official references checked

| Area | Reference |
|---|---|
| NeoForge 1.21.1 setup | `https://docs.neoforged.net/docs/1.21.1/gettingstarted/` |
| NeoForge data attachments | `https://docs.neoforged.net/docs/1.21.1/datastorage/attachments/` |
| NeoForge custom payloads | `https://docs.neoforged.net/docs/1.21.1/networking/payload/` |
| NeoForge config | `https://docs.neoforged.net/docs/1.21.1/misc/config/` |
| Apothic Attributes source branch | `https://github.com/Shadows-of-Fire/Apothic-Attributes/tree/1.21` |
| Apothic Attributes gradle props | `https://raw.githubusercontent.com/Shadows-of-Fire/Apothic-Attributes/1.21/gradle.properties` |
| Placebo gradle props | `https://raw.githubusercontent.com/Shadows-of-Fire/Placebo/1.21/gradle.properties` |
| Apotheosis gradle props | `https://raw.githubusercontent.com/Shadows-of-Fire/Apotheosis/1.21/gradle.properties` |
| MariesLib source | `https://github.com/kgbcupcake/MarieLib` |
| MariesLib gradle props | `https://raw.githubusercontent.com/kgbcupcake/MarieLib/master/gradle.properties` |

## Observed versions from official project files

| Project | Observed version | MC | Java | NeoForge/Forge field | Notes |
|---|---:|---:|---:|---:|---|
| Apothic Attributes | `2.9.1` | `1.21.1` | `21` | `21.1.187` | `requiredDeps=placebo`; branch gradle references `placeboVersion=9.9.0`. |
| Placebo | `9.9.1` | `1.21.1` | not separately listed as Java field in table | `21.1.187` | Branch version differs from Apothic gradle's placeboVersion. Resolve in Gradle before pinning. |
| Apotheosis | `8.5.4` | `1.21.1` | `21` | `21.1.187` | Optional only for Immersive EGO. Requires several projects in its own ecosystem; do not make mandatory. |
| MariesLib | `0.1.0-beta.5` | `1.21.1` | not listed separately | `21.1.229` / range `[21.1.228,)` | LGPL-3.0-only observed; keep as separate dependency. |

## Resolver decision required

Do not finalize Gradle coordinates from this document alone. Codex must inspect:

1. Modrinth/Curse Maven or official artifact repositories available to the repo.
2. The exact source/JAR API for Apothic Attributes, Placebo, MariesLib, and optional Apotheosis.
3. Whether Apothic Attributes resolves Placebo transitively or requires a direct declaration in the chosen Gradle setup.
4. Whether MariesLib's published artifact coordinates match `dev.marie.MariesLib` or use a lowercase Maven convention.

## Hard build rules

- Pin exact versions in `gradle.properties`.
- Keep MariesLib as separate installed dependency.
- Keep Apotheosis optional.
- Do not directly call Placebo APIs unless unavoidable.
- Record source commits/tags after dependency resolution.
