# Immersive EGO Full Project Review

Date: 2026-06-22

Review scope: decision-summary review of the current Immersive EGO alpha work, how the systems cooperate, what is done, what remains, proof level, risks, and whether additional GPT/subagent help is needed.

## Executive Summary

Immersive EGO is no longer a bootstrap project. The live checkout is a NeoForge 1.21.1 / Java 21 mod at `0.1.0-alpha.56` with the main survival-RPG simulation foundations implemented behind safe config gates. The current state is best described as a broad, default-off alpha foundation with strong automated proof, LAB install proof, and live Apothic UI proof, but not yet a live-client-proven playable release slice.

The core architecture is coherent:

1. SERVER config gates decide whether the master simulation and each module can run.
2. Player attachments hold authoritative state: `EgoState`, `EgoOriginState`, and `EgoInjuryMemory`.
3. Runtime samplers collect environment, respiration, stamina/activity, inventory load, sleep, nutrition, psychology, and origin/acclimation context.
4. Scheduler frames combine those contexts into server-authoritative state updates.
5. Derived calculators update EGO Load, Readiness, Focus, Comfort, and Recovery Capacity.
6. Display mirrors expose server state through registered player attributes.
7. Commands and docs expose readback and diagnostics.

The biggest remaining gap is not "more code everywhere"; it is proof and integration polish. Live Apothic Attributes visibility is now proven. The first remaining live acceptance item is the Desertborn hot-biome vertical slice.

## Truth Sources Used

- Repository root: `<USER_HOME>\AI Depot\Codex Documents\Immersive EGO`
- Current instructions: `AGENTS.md`
- Roadmap/status: `TASKS.md`
- Handoff/current-state detail: `docs/CODEX_HANDOFF.md`
- Validation history: `docs/IMPLEMENTATION_LOG.md`
- Version/dependencies: `gradle.properties`
- Runtime reports:
  - `build/install-report.json`
  - `build/runtime-deps-report.json`
  - `build/dedicated-server-smoke-report.json`
- Notion hub:
  - `Immersive EGO`
  - `IMMERSIVE_EGO_CODEX_MASTER_PROMPT`
  - `Immersive EGO Missing Project Pack Import - 2026-06-21`
  - `Immersive EGO Full Project Review - 2026-06-22`

## Current Coordinates

- Minecraft: `1.21.1`
- NeoForge: `21.1.233`
- Java: `21`
- Mod id: `immersive_ego`
- Package root: `com.oblixorprime.immersiveego`
- Version: `0.1.0-alpha.56`
- Required runtime dependencies:
  - Apothic Attributes `2.9.1`
  - MariesLib `0.1.0-beta.5`
  - Placebo `9.9.1` as Apothic support/runtime dependency
- Optional dependency:
  - Apotheosis `8.5.4`, optional/absent-safe

## System Map

### Config and Gates

The mod registers the required roadmap SERVER config files and one client config. Gameplay remains safe by default because the master simulation gate and per-module gates are disabled unless configured. Dynamic world TOML loaders handle `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml`, with malformed-input fallback and repair coverage.

Proof level:

- Implemented: yes.
- Automated-tested: yes, through unit tests and GameTests for required config names, dynamic defaults, repair, malformed fallback, and synergy graph validation.
- LAB-installed: yes, alpha.56 jar installed and hash-verified.
- Live-client-proven: not yet.

### Authoritative State

`EgoState` stores the main normalized display/simulation values. `EgoOriginState` stores origin and acclimation axes. `EgoInjuryMemory` stores recent injury, lingering pain, and recovery debt. The attachments are server-authoritative and serialize through versioned codecs.

Proof level:

- Implemented: yes.
- Automated-tested: yes, including codec/unit coverage, runtime mutation readback, player NBT save/load round-trip, and death clone-event copy behavior.
- LAB-installed: yes.
- Live-client-proven: partially; installed and dedicated-server-smoked, but no live client save/reload play evidence yet.

### Simulation Scheduler and Derived State

The scheduler owns periodic player simulation. It builds frames from optional module contexts, applies module services, and derives EGO Load and Readiness. The current scheduler is deliberately config-gated and default-off.

Proof level:

- Implemented: yes.
- Automated-tested: yes, including pure frame tests and runtime progression GameTests for hydration, respiratory mapping, stamina, sleep, nutrition, and psychology.
- LAB-installed: yes.
- Live-client-proven: not yet as a full playable slice.

### Environment and Thermoregulation

The environment system models climate profiles, season phase, sky exposure, shelter, wetness, thermal snapshots, player-scoped cache, and debug counters. Environment pressure can feed scheduler frames and display mirrors when enabled.

Proof level:

- Implemented: yes.
- Automated-tested: yes, through climate profile, thermal snapshot, cache, sampler, and scheduler/frame coverage.
- LAB-installed: yes.
- Live-client-proven: not yet in a player walk-through.

### Physiology: Hydration, Respiration, Stamina, Encumbrance

Physiology is the largest code surface. It includes hydration demand/recovery, vanilla drink recognition, water quality and purification recognition using existing items, altitude/depth/dimension respiratory strain, stamina activity cost/regeneration, inventory weight resolution/cache, encumbrance tiers, and stamina/fatigue load synergies.

Proof level:

- Implemented: yes.
- Automated-tested: yes, with unit tests and runtime GameTests for drink recognition, hydration progression, respiration dimension mapping, stamina progression, and encumbrance math.
- LAB-installed: yes.
- Live-client-proven: not yet through a full travel/sprint/load scenario.

### Sleep and Fatigue

Sleep models circadian phase, sleep pressure, fatigue, night sleep quality, naps, and phantom rest qualification assumptions. The design keeps multiplayer time behavior safe by default.

Proof level:

- Implemented: yes.
- Automated-tested: yes, with calculator tests and runtime GameTests for Minecraft sleep/stat assumptions, sleep recovery, awake-night sleep debt, and day-nap behavior.
- LAB-installed: yes.
- Live-client-proven: not yet.

### Nutrition and MariesLib

MariesLib is isolated behind `EgoMariesLibFacade` and remains a separate runtime dependency. Immersive EGO registers six nutrition groups, maps vanilla foods, uses MariesLib dietary memory, and converts readback into meal quality, gut health, and `nutrition_balance`.

Proof level:

- Implemented: yes.
- Automated-tested: yes, including six-group catalog tests, pure nutrition service tests, runtime food-catalog GameTest, and live food-consumption event GameTest.
- LAB-installed: yes, MariesLib dependency hash-verified.
- Live-client-proven: not yet in normal client play.

### Psychology, Pain, and Recovery

Psychology and recovery currently cover stress, fear pressure, pain pressure, stability, focus, comfort, recovery capacity, and persistent injury memory. The runtime records health-deficit injury memory and applies pain/fear pressure through server-authoritative state when enabled.

Proof level:

- Implemented: yes for the default-off foundation.
- Automated-tested: yes, including pure psychology tests and the alpha.56 runtime GameTest.
- LAB-installed: yes.
- Live-client-proven: not yet. Tuning still needs live play evidence.

### Origins and Acclimation

Origins map biome families to profile ids and track heat, cold, altitude, and humidity acclimation axes. Origin assignment and readback are gated by config and exposed through debug command output.

Proof level:

- Implemented: yes for default profiles, assignment, debug readback, and persistent acclimation axes.
- Automated-tested: yes, with pure service tests and runtime default-origin/attachment coverage.
- LAB-installed: yes.
- Live-client-proven: not yet. The full Desertborn hot-biome slice is still open.

### Attributes, Apothic, Commands, and UX

The project registers capability and display-mirror attributes, attaches them to players, and mirrors server state without duplicate transient modifiers. Commands currently cover `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace`.

Proof level:

- Implemented: mostly yes.
- Automated-tested: yes for attribute catalog uniqueness, player attribute attachment, duplicate-free mirrors, command registration, and permission guards.
- LAB-installed: yes.
- Live-client-proven: yes for Apothic visibility. The 2026-06-22 LAB screenshot shows `EGO Acclimation Gain`, `EGO Hydration Efficiency`, and `EGO Load` in the native Apothic `Attributes` panel.

## Done vs. Not Done

### Done With Strong Evidence

- NeoForge 1.21.1 / Java 21 project setup.
- Dependency pinning and metadata for NeoForge, Minecraft, Apothic Attributes, Placebo, MariesLib, and optional Apotheosis.
- MIT project rules, third-party notices, and no custom blocks/items/entities/fluids policy.
- Server-authoritative attachment model.
- Config-gated runtime modules.
- Dynamic config and synergy loaders with malformed-input fallback.
- Core simulation frame and derived state foundation.
- Environment, hydration, respiration, stamina, encumbrance, sleep, nutrition, psychology, injury memory, origins, and acclimation foundations.
- Repeatable build/install/smoke workflow.
- Missing project pack imported as complementary/reference material and mirrored into Notion.
- Release-grade source provenance and credits review refreshed on 2026-06-22, including the complementary pack hash mismatch and runtime-jar packaging exclusion policy.
- CurseForge metadata draft expanded on 2026-06-22 with current alpha.56 status, dependencies, verification status, release gaps, boundaries, credits, and suggested metadata.
- Live Apothic Attributes UI proof for EGO attributes.

### Done But Needs Better Live Proof

- Installed alpha.56 is safe to launch in Prism LAB for further testing, based on current hash/metadata/dependency proof.
- Dedicated-server startup is proven.
- Core runtime GameTests are proven.
- Full client play behavior is not yet proven.
- Desertborn hot/dry vertical slice is not yet proven end-to-end.

### Not Done

- Live vertical-slice playtest: Desertborn, hot dry biome, heavy inventory, sprinting, heat/hydration/stamina/load/fatigue/sleep/recovery/readiness.
- Pain/recovery tuning from live play evidence.
- Full configured synergy-edge set beyond the currently safe/sample/foundation behavior.
- Optional Apotheosis presence test.
- Final CurseForge metadata completion after screenshots/live proof.
- Final release-ready git/Notion/push evidence.

## Refreshed Validation Evidence

Commands run during this review:

```powershell
.\gradlew.bat --no-daemon compileJava
.\gradlew.bat --no-daemon test
.\gradlew.bat --no-daemon runGameTestServer
.\gradlew.bat --no-daemon clean build
.\scripts\install-mod.ps1
.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180
```

Results:

- `compileJava`: passed.
- `test`: passed.
- `runGameTestServer`: passed with all 23 required tests.
- GameTest runtime loaded Immersive EGO `0.1.0-alpha.56`, Apothic Attributes `2.9.1`, Placebo `9.9.1`, MariesLib `0.1.0-beta.5`, and Curios.
- GameTest runtime loaded MariesLib scanner spec from `run/config/immersive_ego/scanner_spec.json`.
- GameTest runtime reported `scanAndApply complete: 1333 confident, 0 uncertain`.
- `clean build`: passed and produced `build/libs/immersive_ego-0.1.0-alpha.56.jar`.
- `install-mod.ps1`: passed and regenerated `build/install-report.json` and `build/runtime-deps-report.json`.
- Dedicated-server smoke: passed and regenerated `build/dedicated-server-smoke-report.json`.

Installed alpha.56 proof:

- Built jar: `<USER_HOME>\AI Depot\Codex Documents\Immersive EGO\build\libs\immersive_ego-0.1.0-alpha.56.jar`
- Installed jar: `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.56.jar`
- Source size: `464244`
- Installed size: `464244`
- Source SHA-256: `5bba60ff0b79bd2bfaac7bc89a9ac65fe385740aefdc89066d7bb481e317fd73`
- Installed SHA-256: `5bba60ff0b79bd2bfaac7bc89a9ac65fe385740aefdc89066d7bb481e317fd73`
- Hash match: `true`
- Remaining installed `immersive_ego` jars by primary mod metadata: `1`

Runtime dependency proof:

- Apothic Attributes: hash match true, remaining count 1.
- Placebo: hash match true, remaining count 1.
- MariesLib: hash match true, remaining count 1.
- Curios: hash match true, remaining count 1.
- Cloth Config: hash match true, remaining count 1.

Live Apothic UI proof:

- Prism LAB alpha.56 opened the native Apothic `Attributes` panel from the survival inventory.
- Visible EGO rows: `EGO Acclimation Gain`, `EGO Hydration Efficiency`, and `EGO Load`.
- Screenshot: `docs/proof/apothic-live-ui-alpha56-20260622-060914.png`
- Screenshot SHA-256: `A08C1F866DE35C6851911B23227CA4D95D6E1BF675217DB0FC5369870EE7357A`

Dedicated-server smoke proof:

- Success: `true`
- Timed out: `false`
- Required markers seen:
  - `Immersive EGO bootstrap loaded`
  - `Loaded Immersive EGO dynamic server config`
  - `Loaded Immersive EGO synergy config`
  - `Loaded scanner spec from`
  - `Done (`
- Failure matches: none.

## Dirty Worktree State

The working tree is intentionally dirty and contains a broad alpha implementation plus imported complementary pack files. This review did not revert unrelated changes.

Important modified tracked areas:

- Governance/docs: `AGENTS.md`, `CHANGELOG.md`, `README.md`, `README_FR.md`, `TASKS.md`, and maintained docs under `docs/`.
- Build/version/install scripts: `gradle.properties`, `scripts/install-mod.ps1`, `scripts/install-runtime-deps.ps1`.
- Runtime source: `ImmersiveEgo.java`, commands, config loaders, attachments, simulation scheduler/mirrors, GameTests, and language files.
- Tests: config loader tests, simulation frame tests, boundary tests.

Important untracked areas:

- Imported complementary pack material: `branding/`, `codex_prompts/`, `config_templates/`, root `*.template` files, and `docs/project_pack/`.
- New/reconstructed runtime packages: `compat/`, `environment/`, `nutrition/`, `origins/`, `physiology/`, `psychology/`, `sleep/`, and `EgoSimulationFrameCalculator.java`.
- New/reconstructed tests for environment, nutrition, origins, physiology, psychology, sleep, and mirror services.
- `scripts/smoke-dedicated-server.ps1` and `scripts/verify_ego_repo.ps1`.

## Risks and Constraints

- 2026-06-22 next-gate attempt: the active LAB modpack had an incompatible companion jar, `immersive_ego_civitas-0.1.0-alpha.35.jar`, whose metadata required `immersive_ego 0.1.0-alpha.44` while LAB was installed with alpha.56. The jar was moved to `minecraft\mods-disabled`, leaving one active alpha.56 Immersive EGO jar.
- After that cleanup, LAB launched to the main menu and an existing save reached survival inventory, but Apothic GUI proof did not close. The existing save killed the player before capture, the fresh-world attempt stopped advancing after data-pack reload, and the desktop switched to a Windows sign-in/lock overlay.
- Evidence from that attempt is in `docs/IMPLEMENTATION_LOG.md` under `Apothic UI Gate Attempt and LAB Companion Cleanup`.
- 2026-06-22 follow-up: a controlled LAB pass hosted the world to LAN with commands enabled, set Peaceful, disabled mob spawning, returned to Survival Mode, opened the Apothic panel, and captured EGO rows. Apothic visibility is now complete at live-client proof level.
- Do not mark the vertical slice complete until the Desertborn/hot-dry/heavy-load/rest/meal/sleep flow is playable and documented.
- Do not treat source inspection or GameTests as proof of GUI behavior.
- Do not add a client mixin unless normal Apothic registration fails to surface the EGO attributes.
- Do not bundle MariesLib; it must remain separate.
- Do not add Cold Sweat, Tough As Nails, custom blocks/items/entities/fluids, or copied ARR assets.
- The complementary pack's embedded `SHA256.txt` does not match the downloaded archive hash; it is reference material, not release provenance.

## Recommended Next Work

1. Run the first live vertical-slice playtest.
   - Enable the needed modules in LAB/server config.
   - Exercise Desertborn/hot dry biome/heavy inventory/sprint/rest/meal/sleep.
   - Record `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and `/ego synergy trace`.
   - Keep screenshots/logs as evidence.

2. Tune pain/recovery and synergy edges from live evidence.
   - Use observed state transitions, not guesses.
   - Keep defaults conservative and configurable.

3. Finish public release docs and metadata.
   - Add final screenshots and live-proof results to the CurseForge metadata.
   - Re-check source provenance only if new runtime assets, copied/adapted code, or release-pack material is added.
   - Ensure `TASKS.md`, `CODEX_HANDOFF.md`, and `IMPLEMENTATION_LOG.md` are current after each proof pass.

## Extra GPT / Subagent Recommendation

No extra GPT/subagents are needed for this decision-summary review. The current path is clear enough for one main agent.

Use explorer subagents only if the next request expands into a deeper audit:

- Architecture/data-flow explorer.
- Test/evidence-gap explorer.
- Docs/Notion/release-readiness explorer.

Use worker subagents only after the review identifies concrete implementation tasks with disjoint file ownership.

## Bottom Line

Immersive EGO alpha.56 has a strong automated and installed foundation, and EGO attributes are now live-proven in Apothic Attributes. The mod is safe to continue testing in Prism LAB, but it is not release-ready and should not be described as a completed playable vertical slice. The next high-leverage move is the live Desertborn hot-biome vertical-slice playtest.
