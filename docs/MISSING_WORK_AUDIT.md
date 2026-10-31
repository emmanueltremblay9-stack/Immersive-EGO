# Missing Work Audit — Immersive EGO

> Pack-origin note: imported on 2026-06-21 from `immersive_ego_missing_project_pack.zip` as a historical bootstrap audit. The live repository is already beyond parts of this baseline; use `TASKS.md`, `docs/IMPLEMENTATION_LOG.md`, and source as the current authority.

## Source state

The Notion root page contains a child master execution prompt. The prompt defines a complete project contract, but the repository still needs actual project files, governance docs, configs, translations, CI, and implementation scaffolding.

## Files required by the specification and supplied by this pack

- `AGENTS.md`
- `TASKS.md`
- `LICENSE`
- `THIRD_PARTY_NOTICES.md`
- `README.md`
- `README_FR.md`
- `CHANGELOG.md`
- `docs/SOURCE_PROVENANCE.md`
- `docs/LICENSE_MATRIX.md`
- `docs/ASSET_POLICY.md`
- `docs/DEPENDENCY_AUDIT.md`
- `docs/DECISIONS.md`
- `docs/IMPLEMENTATION_LOG.md`
- `docs/CODEX_HANDOFF.md`
- `docs/ARCHITECTURE.md`
- `docs/CONFIG_REFERENCE.md`
- `docs/USER_GUIDE.md`
- `docs/APOTHIC_INTEGRATION.md`
- `docs/MARIESLIB_INTEGRATION.md`
- `docs/APOTHEOSIS_COMPAT.md`
- `docs/PERFORMANCE.md`
- `docs/TEST_PLAN.md`
- `docs/MODPACK_GUIDE.md`
- `docs/CURSEFORGE_DESCRIPTION.md`
- `docs/CREDITS.md`
- `.github/workflows/build.yml`
- language templates for `en_us.json` and `fr_fr.json`
- config templates for server/client/world profile files
- Codex phase prompts

## Still not supplied as finished code

- Java implementation of the simulation modules
- Real NeoForge Gradle project generation from the official MDK if repo is empty
- Generated Gradle wrapper binaries
- GameTests and unit tests with actual pass/fail output
- Verified runtime integration with Apothic Attributes and MariesLib
- Release JAR
