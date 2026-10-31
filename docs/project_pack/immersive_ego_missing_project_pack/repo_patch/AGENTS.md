# Immersive EGO — Codex Operating Rules

Target: Minecraft 1.21.1 / NeoForge 21.1.x / Java 21  
Mod ID: `immersive_ego`  
Base package: `com.oblixorprime.immersiveego`  
License: MIT

## Non-negotiables

- Inspect the repository before editing.
- If the repository is empty, bootstrap a complete NeoForge 1.21.1 project.
- If work exists, preserve valid code and resume from the first incomplete item in `TASKS.md`.
- Never invent external APIs. Inspect official source, dependency source JARs, generated sources, or official docs first.
- Never claim a command passed unless it was executed in this repo.
- No copied third-party assets.
- No GPL code copied into this MIT project.
- No Cold Sweat dependency, copied implementation, or integration.
- Do not register custom blocks, items, entities, fluids, machines, workstations, or Blockbench models.
- Server-authoritative gameplay only. No client-authoritative stat mutation.
- Keep client-only classes out of common/server code.
- Keep MariesLib as a separate runtime dependency. Do not JarJar it.
- Keep Apotheosis optional and classloading-safe when absent.

## Required end-of-run updates

Every Codex run must update:

- `TASKS.md`
- `docs/IMPLEMENTATION_LOG.md`
- `docs/CODEX_HANDOFF.md`
- `docs/DECISIONS.md`, when a meaningful decision was made

Final report must include:

1. IMPLEMENTED
2. FILES CHANGED
3. BUILD AND TESTS
4. DEPENDENCIES
5. LICENSE/ASSET CHECK
6. REMAINING WORK
7. NEXT ACTION
