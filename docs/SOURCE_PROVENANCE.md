# Source Provenance

Review status: refreshed 2026-06-22 against the current dirty worktree and imported reference pack.

## Original Project Sources

- Java package `com.oblixorprime.immersiveego`: original implementation for Immersive EGO.
- `README.md`, `README_FR.md`, `TASKS.md`, and `docs/`: original project documentation generated from the Notion master prompt and verified repository state.
- `src/main/resources/immersive_ego_logo_1024.png`: user-provided project logo at `<USER_HOME>\AI Depot\Image\Mod Logo\immersive_ego_logo_1024.png`.

## Adapted Template Files

The Gradle and NeoForge project scaffold was adapted from:

- Repository: https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle
- Commit: `ff9c5e847cb470ee2fb63d4309f7a25a61864ba5`
- License: MIT, copyright 2023 NeoForged project

Adapted files:

- `.gitattributes`
- `.gitignore`
- `.github/workflows/build.yml`
- `build.gradle`
- `gradle.properties`
- `gradle/wrapper/gradle-wrapper.properties`
- `gradlew`
- `gradlew.bat`
- `settings.gradle`
- `src/main/templates/META-INF/neoforge.mods.toml`

The MDK example mod Java classes, example block/item registrations, creative tab registration, and example language file were removed.

## Complementary Project Pack Material

The complementary missing project pack was imported as local governance, prompt, template, and planning material on 2026-06-21.

- Source archive: `<USER_HOME>\Downloads\immersive_ego_missing_project_pack.zip`
- Safe staging path: `C:\AI-Work\immersive_ego_missing_project_pack_stage_20260621_1718\immersive_ego_missing_project_pack`
- Actual archive SHA-256: `F761679BD909FF422EBF1BC4787CE801B28F8C46FAABA1D45DFE579905215322`
- Embedded pack `SHA256.txt`: `483878e869ea29d876e6ae1d05f3ad8e5a7cd5e92b12afb8e6d5859a58b46913`

The embedded pack hash does not match the downloaded archive hash. The imported pack is therefore treated as local complementary reference material, not as external upstream release provenance.

Installed reference/governance material:

- `branding/`
- `codex_prompts/`
- `config_templates/`
- `build.gradle.template`
- `gradle.properties.template`
- `settings.gradle.template`
- `scripts/verify_ego_repo.ps1`
- `docs/API_SURFACE_PLAN.md`
- `docs/COMMAND_TREE.md`
- `docs/MISSING_WORK_AUDIT.md`
- `docs/VERTICAL_SLICE_PLAN.md`

Preserved reference material:

- `docs/project_pack/immersive_ego_missing_project_pack/`
- Pack metadata, `neoforge.mods.toml.template`, and language-reference files kept under `docs/project_pack/` instead of active runtime locations.

Packaging policy:

- `branding/`, `codex_prompts/`, `config_templates/`, root `*.template` files, and `docs/project_pack/` are release/reference material only and must not be packaged into the runtime mod jar.
- Active runtime language files remain under `src/main/resources/assets/immersive_ego/lang/` and were not overwritten by pack language references.

## External Code

No external gameplay code has been copied or adapted in this phase.

## External Assets

No third-party mod assets have been copied.

`branding/immersive_ego_logo.svg` and related branding prompts are original placeholder/reference material from the complementary pack, not copied third-party mod assets. The active runtime logo remains `src/main/resources/immersive_ego_logo_1024.png`.
