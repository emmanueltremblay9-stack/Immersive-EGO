# Local alpha.61 preservation snapshot

Captured on 2026-10-13 from the existing Immersive EGO local worktree, without modifying that original worktree.

## Provenance

- Original Git branch: `main`.
- Original HEAD: `789238c475ecabc19808b9ac7d99df7f457670b8` (alpha.27).
- Original state: 44 modified tracked files and 31 untracked status entries (some entries are directories).
- Local `gradle.properties`: `mod_version=0.1.0-alpha.61`.
- Present local artifact: `build/libs/immersive_ego-0.1.0-alpha.61.jar`, 465307 bytes, 143 classes, internal mod metadata `immersive_ego` / `0.1.0-alpha.61`.
- Artifact SHA-256 recalculated during read-only inspection: `23a342a141124d2149f2c59f56161718ff5e39c2521ee70600281c164c87ee21`.
- The existing local install report records the same source/installed hash. The external installed artifact was not inspected in this preservation task.
- `LOCAL_ALPHA61_SOURCE_MANIFEST.json` records the original and copied file byte hashes for the 262 selected files before Git line-ending normalization. Differences cover privacy adaptations, preservation notes in TASKS/handoff/implementation log and the added local-only ignore rules; no gameplay source edits were made.

## Scope and privacy adaptations

Preserved sources, tests, resources, build configuration, wrapper, scripts, documentation, branding, configuration templates, prompts and reference project pack. All 133 main Java files and 38 test Java files are included.

Excluded local-only material: build outputs and mod/dependency JARs, Gradle caches, run directories and logs, world/player backups, all `docs/proof/` screenshots and runtime backups, CodeGraph cache, local agent/config folders and IDE/account files. The existing tracked Gradle wrapper JAR remains part of the build scaffold; no runtime JAR is uploaded.

Personal Windows home paths are replaced with `<USER_HOME>` in documentation. Private Notion page URLs are replaced with `<PRIVATE_NOTION_REFERENCE>`. The two install scripts derive the same Prism LAB default path from `$env:APPDATA` rather than embedding a personal Windows username. Installation scripts were syntax-checked but not executed.

Original local files, including excluded evidence, remain untouched. Historical documentation references to excluded screenshots/logs refer to local evidence, not files included in this public snapshot. Existing stale version labels and historical claims are preserved and are not new validation results.

## Validation and limits

- Static scan of selected text files found no matching credential patterns, personal Windows home paths, private Notion URLs or player UUID from the excluded backups after privacy adaptations. This bounded scan is not a guarantee that every possible sensitive value is recognizable.
- Seven JSON files parsed; four PowerShell scripts parsed without syntax errors.
- A fresh offline `clean build` was attempted on the separate copy with Java 21.0.11. Gradle failed before task execution with `java.io.IOException: Unable to establish loopback connection` when starting its single-use daemon. No compilation or JUnit result is claimed for that attempt.
- Existing historical local JUnit reports contain 156 tests, zero failures/errors/skips; these reports were excluded and do not validate this new commit.
- Archived `run/logs/2026-06-28-3.log.gz` identifies alpha.58 and records all 23 required GameTests passed on 2026-06-28 at 02:14:48.954. This is historical automated test evidence, not a fresh live-client proof.
- Alpha.56/58 binaries and separately committed source snapshots are absent in the inspected folder. The alpha.61 worktree includes the documented alpha.58 boundary fixes, but an exact source-to-binary correspondence is not established without an independent rebuild and comparison. JAR timestamps can also prevent byte-for-byte reproducibility.
- Desertborn hot-biome live-client proof remains open. No game, dedicated/GameTest server or installation was launched for this preservation task.
- This branch is for a draft PR only. No merge, release publication or new feature is authorized by this snapshot.