# AGENTS.md

Repository rules for Codex and other agents:

- Target Minecraft 1.21.1, NeoForge 21.1.x, Java 21.
- Mod id: `immersive_ego`; package root: `com.oblixorprime.immersiveego`; license: MIT.
- Do not register custom blocks, items, entities, fluids, machines, workstations, Blockbench models, or copied third-party assets.
- Keep Apothic Attributes and MariesLib as separate runtime dependencies. Never JarJar MariesLib.
- Do not add Cold Sweat, Tough As Nails, or copied All Rights Reserved assets.
- Keep gameplay calculations server-authoritative; never let the client mutate authoritative EGO state.
- Before claiming completion, run `.\gradlew.bat clean build` and record exact results in `docs/IMPLEMENTATION_LOG.md`.
- For Prism LAB test installs, use `scripts/install-mod.ps1` and verify hash, metadata, and one remaining installed jar.
- Keep `TASKS.md`, `docs/CODEX_HANDOFF.md`, and `docs/IMPLEMENTATION_LOG.md` current after each run.
