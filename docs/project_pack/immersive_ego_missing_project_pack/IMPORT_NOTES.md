# Immersive EGO Missing Project Pack Import Notes

Imported on 2026-06-21 from:

- Source archive: `<USER_HOME>\Downloads\immersive_ego_missing_project_pack.zip`
- Safe staging path: `C:\AI-Work\immersive_ego_missing_project_pack_stage_20260621_1718\immersive_ego_missing_project_pack`
- Actual archive SHA-256: `F761679BD909FF422EBF1BC4787CE801B28F8C46FAABA1D45DFE579905215322`
- Pack `SHA256.txt` value: `483878e869ea29d876e6ae1d05f3ad8e5a7cd5e92b12afb8e6d5859a58b46913`

The embedded `SHA256.txt` value does not match the actual downloaded archive hash. The pack was still imported as local complementary material, but this mismatch should be treated as a provenance warning.

## Installed Directly

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

## Preserved As Reference

- Pack metadata: `README_INSTALL.md`, `FILE_INDEX.md`, `project_manifest.json`, and `SHA256.txt`.
- `src/main/resources/META-INF/neoforge.mods.toml.template` was placed under this reference folder instead of active `src/main/resources` so Gradle will not package it into the runtime jar.
- Pack `en_us.json` and `fr_fr.json` were placed under `language_reference/` instead of overwriting active runtime language files.

## Not Overwritten

The pack included bootstrap-era versions of files that already exist and are newer or live-maintained in this repository. These were not overwritten:

- `AGENTS.md`
- `CHANGELOG.md`
- `README.md`
- `README_FR.md`
- `TASKS.md`
- `LICENSE`
- `THIRD_PARTY_NOTICES.md`
- `.github/workflows/build.yml`
- Existing maintained docs under `docs/`
- Active runtime language files under `src/main/resources/assets/immersive_ego/lang/`
