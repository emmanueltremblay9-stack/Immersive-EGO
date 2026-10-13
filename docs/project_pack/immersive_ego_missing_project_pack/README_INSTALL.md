# Immersive EGO — Missing Project Pack 🧬

Generated: 2026-06-21

This ZIP is a **drop-in project completion kit** for the Immersive EGO repository.  
It was built from the Notion project page and the child `IMMERSIVE_EGO_CODEX_MASTER_PROMPT`.

## What this contains

- `repo_patch/` — files to copy into the root of the mod repository.
- `codex_prompts/` — execution prompts for Codex runs.
- `branding/` — original SVG placeholder logo/banner plus an image-generation prompt.
- `project_manifest.json` — source and content manifest.

## Intended use

1. Extract the ZIP.
2. Copy the contents of `repo_patch/` into the existing `immersive_ego` repo root.
3. Run `repo_patch/scripts/verify_ego_repo.ps1` from the repo root on Windows PowerShell.
4. Give Codex `codex_prompts/01_phase0_bootstrap_and_audit.txt`.
5. Do not mark Phase 0 complete until the actual repo builds with Java 21.

## Important limitation

This pack intentionally avoids pretending that a build passed. It provides the missing project files, documentation, configs, translations, and Codex execution material.  
The actual NeoForge source code still needs to be generated/implemented inside the target repository, using official APIs and the resolved dependency source/JARs.

## Strict project constraints carried forward

- Minecraft `1.21.1`
- NeoForge `21.1.x`
- Java `21`
- Mod ID `immersive_ego`
- Base package `com.oblixorprime.immersiveego`
- Project license `MIT`
- Required: Apothic Attributes + MariesLib
- Optional: Apotheosis
- Forbidden: Cold Sweat dependency/integration, copied ARR assets, GPL code copied into MIT source, custom blocks/items/entities/fluids.
