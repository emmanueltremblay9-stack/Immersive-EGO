# Implementation Log

## 2026-10-17 — approved thermal tolerance option B

Implemented server effective-tolerance projection on the existing thermal snapshot, with separately reported raw/adjusted values. No dependency, version, config-default, wetness model, scheduler-order or unrelated coefficient change. The owner selected B at 10:44 UTC; see [contract](THERMAL_TOLERANCE.md).

Targeted command `python /workspace/cloud-setup/gradle-cloud.py --offline test --tests '*EgoThermalToleranceTest'`: corrected run exit 0, 14 tests passed. The first run had six incorrect test expectations (climatic wetness uses the existing humidity/precipitation weighted mean); those expectations were corrected without changing production formulas and the failed log/results are preserved. Initial isolated `runGameTestServer` run: 37 required tests passed, exit 0. An independent read-only agent review found no corrective finding; it recommended modifier coverage, now added before final validation. Clean build and exact packaged-client/CI results will be recorded after execution.

## 2026-10-17 — thermal contract investigation and qualification follow-up

Preserved the original JAR, all 199 inventoried original evidence files and ZIP byte-for-byte. Same source candidate `3f7734cc4bbd6802c12c09b5990544b86faac47a`; no gameplay change or version bump. DEV lang/catalog define a positive heat/cold-tolerance multiplier, default 1 and bounds .25–4, but not a formula. GOV at `8004452ee3eb831a8840a99fa0ad0b487e0986d7` gives no numeric rule. Traced snapshot→thermal state→origin relief and verified the missing thermal-capacity consumer in both `50d9e4b9` and older source. No arbitrary fix/PR/CI: explicit A/B/C decision documented in [complete follow-up](THERMAL_TOLERANCE_FOLLOWUP.md).

Executed `python /workspace/cloud-setup/gradle-cloud.py --offline --no-configuration-cache -I /workspace/ego-followup-20261017/gametest.init.gradle runGameTestServer` in an isolated GameTest run directory: **exit 0, all 34 required tests passed freshly**. Existing source tests include actual API/stat mapping for 100/72000 rest thresholds and day-nap versus night recovery. No redundant JUnit/clean build was run because gameplay/build inputs did not change; the prior fresh 175-JUnit clean build remains separately identified.

Three additional production `forgeclient` processes loaded the unchanged packaged JAR and exited 0. Added 21 samples and nine passing numerical checks for separate environment/scheduler pauses, rain, snow, boat, submersion with air depletion/health damage, End, and actual daytime thunderstorm sleep. Snapshot protocols and clock boundaries are retained. The scheduler TOML reload was exercised through real server restart as implemented.

Current normal-route asset check returned HTTP 200; downloaded all 3,888 official unique index-17 objects, 825,818,935 bytes, each with verified size/SHA-1. Earlier 403 evidence remains intact. Missing assets disappear in two new client logs; panorama renders normally. With no container audio device, the existing OpenAL Soft Wave File Writer backend was selected only for the test processes; sound engine starts and real 48-kHz/16-bit stereo audio is recorded (55.34-second sample, nonzero output). No global audio/security/credential changes. Two vanilla empty-event warnings and Minecraft public-key/Placebo Patreon DNS errors remain documented. Optional Apotheosis metadata on the declared Maven returns 403; stopped without alternate route. No active synergy/affix integration or later mechanics were invented.

Restored 13 temporary TOMLs and test gamerules, stopped clients/display, deleted ephemeral display cookie. Original evidence remains immutable; follow-up artifacts are under `/workspace/ego-followup-20261017`. Updated documentation only. Full gate remains open for the thermal gameplay decision and the independently scoped remaining validations.

## 2026-10-17 — isolated packaged-client qualification

Read applicable DEV/GOV contracts, verified live main and fast-forwarded the initially clean cloud checkout from `50d9e4b9baca046f00277f61ba17728be9215d2b` to already-merged PR2/main `3f7734cc4bbd6802c12c09b5990544b86faac47a`. No PR2 duplication or gameplay source modification. GOV reference: `8004452ee3eb831a8840a99fa0ad0b487e0986d7` in `emmanueltremblay9-stack/Immersve-EGO-GPT`.

Executed `python /workspace/cloud-setup/gradle-cloud.py --offline --no-build-cache clean build`, the selected Linux environment's wrapper equivalent of the AGENTS Windows clean-build command: exit 0, BUILD SUCCESSFUL, 8 tasks executed, **175 fresh JUnit / zero failure, error or skip**. Minecraft preparation caches were reused; EGO compilation/JAR/tests were fresh after clean. No new GameTest run; the prior 34 are historical. Produced 500,323-byte `immersive_ego-0.1.0-alpha.62.jar`, SHA-256 `064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6`. Hash, metadata and exactly one installed EGO JAR were verified before/after execution. Existing post-merge CI 38920721166 metadata confirms success on this exact SHA, not a newly executed CI run.

Prepared and ran official production NeoForge 21.1.233 `forgeclient` with Minecraft 1.21.1, Java 21.0.8+9 and separate pinned Apothic/Placebo/MariesLib JARs. Installer offline mappings download failed DNS; verified cached official mappings and remaining official local processors produced the production client. Class-load logs prove EGO came from the packaged `mods` JAR. Three actual clients exited 0, with physical singleplayer input, Apothic UI, death/respawn, dimensions, sleep and process restart. This was not Gradle `runClient`.

Recorded 76 exact samples and 27 numerical reconciliation checks. The delivered scenario matrix separates 28 PASS, one FAIL and nine NOT_RUN. Bounded Desertborn and PR2 invariants passed, including W restart identity .9578190577818768 and max thermal projection. Seven capabilities showed effects; `thermal_tolerance` is registered but unused in both current and pre-PR2 source, matching live 1→2/no-effect observation. No defined formula was invented to close that preexisting gap; no regression fix/PR was warranted. The complete gate stays open. Asset-host refusal, missing assets/audio and public-key/Placebo network errors are retained as limitations, not EGO successes. No EGO ERROR header or process crash was observed.

Restored 13 TOMLs byte-for-byte, all changed attribute bases and temporary gamerules; stopped all clients and private display; removed its ephemeral cookie. Retained isolated test save/datapack and evidence, separate from historical results. Only documentation changed in DEV. No merge, release, publication upload, user-PC install, payment or existing security/credential change. Full outcome: [packaged qualification](DESERTBORN_PACKAGED_QUALIFICATION.md), evidence root `/workspace/ego-qualification-20261017`.

## 2026-10-16 — PR2 cloud implementation

Implemented persistent server surface wetness on `feat/persistent-wetness`, based on merged PR1/main `9eb621b684791162862f2262fa586b8ddcc35f4d`. Alpha.62 is a local development identity, with no mod release or user installation. One separate versioned attachment, pure exponential acquisition/drying, ordered per-tick observation/write before thermal, explicit mode/core/environment/scheduler pause and death policy, current-W thermal projection, FR/EN discreet notices and read-only diagnostics. Added 19 JUnit tests and 11 server GameTests plus GameTest execution in the existing CI workflow. See [design/coefficients/limits](WETNESS.md) and [validation matrix](WETNESS_VALIDATION.md).

The real development client exposed a translation-argument bug in the new debug command (raw enum argument rejected by Minecraft). Corrected to enum names; a GameTest now actually executes and serializes all diagnostic output and checks mutation-free opt-out. Earlier failed fixtures and client logs are retained; they are not represented as successful runs. The original alpha.61 jar/hash, 156 JUnit and 23 GameTests remain historical proofs, separately preserved, not PR2 validation.

No cloud configuration, network policy, new dependency/install, other machine, production world, merge or release was changed. Final verification and remote availability are recorded in the linked validation report.

## 2026-07-07

### Version-Only LAB Sync

Updated workflow execution:

- Bumped the verified LAB test build from `0.1.0-alpha.60` to `0.1.0-alpha.61` for the latest work-in-progress bundle.
- No gameplay source logic changed in this pass.

Validation:

- `.\gradlew.bat clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.61.jar`.
- `.\scripts\install-mod.ps1` passed and installed `immersive_ego-0.1.0-alpha.61.jar` into `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods`.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.60.jar`.
- Final installed SHA-256: `23a342a141124d2149f2c59f56161718ff5e39c2521ee70600281c164c87ee21`.
- `build/install-report.json` confirms source/installed hash match, installed mod metadata (`modId="immersive_ego"`, `version="0.1.0-alpha.61"`), and exactly one installed `immersive_ego` jar.
- `build/runtime-deps-report.json` confirms pinned dependency jars are present and verified.

## 2026-07-06

### Player Cache Hygiene and Dynamic Scheduler Interval Fix

Implemented:

- Added logout-time cache cleanup for environment snapshots, inventory-weight snapshots, and encumbrance tiers so player-authoritative state is cleared when players leave the server.
- Wired environment and inventory caches to the current dynamic scheduler interval on each refresh so runtime `schedulerIntervalTicks` changes take effect without a restart.
- Bumped the verified LAB test build from `0.1.0-alpha.59` to `0.1.0-alpha.60`.

Validation:

- `.\gradlew.bat clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.60.jar`.
- `.\scripts\install-mod.ps1` passed and installed `immersive_ego-0.1.0-alpha.60.jar` into `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods`.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.59.jar`.
- Final installed SHA-256: `6cf92bd26f7c7629b204d551e456082f694a2cddd315ec685cd5315c894d3932`.
- `build/install-report.json` confirms source/installed hash match, installed mod metadata (`modId="immersive_ego"`, `version="0.1.0-alpha.60"`), and exactly one installed `immersive_ego` jar.
- `build/runtime-deps-report.json` confirms pinned dependency jars are present and verified.

Remaining:

- Live Desertborn vertical-slice client proof is still blocked by current session control limitations, not by this fix pass.

## 2026-06-28

### Standard Workflow Revalidation and LAB Install

Implemented:

- Re-applied the current Minecraft mod verification workflow to the active NeoForge 1.21.1 project.
- Bumped the verified LAB test build from `0.1.0-alpha.58` to `0.1.0-alpha.59`.
- Made no behavior/source logic changes beyond the versioned test-build metadata and documentation readback.

Validation:

- CodeGraph status reported version `1.1.2` with `initialized=false`; `.codegraph/` is absent, so direct targeted reads were used after the status check.
- `.\gradlew.bat --no-daemon compileJava processResources --stacktrace` passed and regenerated alpha.59 metadata.
- `.\gradlew.bat --no-daemon test --stacktrace` passed.
- `.\gradlew.bat --no-daemon runGameTestServer --stacktrace` passed with Immersive EGO metadata version `0.1.0-alpha.59`, MariesLib scanner spec loaded from `run\config\immersive_ego\scanner_spec.json`, scanner applied 1333 classifications, and `All 23 required tests passed`.
- `.\gradlew.bat --no-daemon build --stacktrace` passed.
- `.\gradlew.bat --no-daemon clean build --stacktrace` passed and produced `build/libs/immersive_ego-0.1.0-alpha.59.jar`.
- `.\scripts\install-mod.ps1` passed, regenerated `build/install-report.json` and `build/runtime-deps-report.json`, deleted old installed jar `immersive_ego-0.1.0-alpha.58.jar`, installed `immersive_ego-0.1.0-alpha.59.jar`, verified matching source/installed SHA-256 `4eae856e00497b7eb049c87a0074df03e2d73f473d80a2da6c09a4a2a4a732fd`, and confirmed exactly one installed `immersive_ego` jar remains by primary mod metadata.
- Installed jar metadata readback confirmed `modId="immersive_ego"`, `version="0.1.0-alpha.59"`, `displayName="Immersive EGO"`, required Apothic Attributes `[2.9.1,)`, and required MariesLib `[0.1.0-beta.5,)`.
- Built jar inspection confirmed `EgoDietaryMemory.class`, `EgoSynergyEvaluator.class`, `EgoAttributeCatalog.class`, and `META-INF/neoforge.mods.toml` are present, with no bundled `data/immersive_ego/immersive_ego/scanner/scanner_spec.json` entry matched.
- LAB dependency readback found the active `immersive_ego_civitas-0.1.0-alpha.38.jar` companion accepts `immersive_ego` version range `[0,)` and its required MineColonies, MCA, Modern Companions, Structurize, BlockUI, Domum Ornamentum, Multipiston, Waystones, and Balm jars are present.

Remaining:

- This pass is automated-tested and LAB-installed. It is not new live-client proof because no gameplay behavior changed.
- The live Desertborn hot-biome vertical-slice gate remains open.

### Three Boundary Hardening Bug Fixes

Implemented:

- Fixed MariesLib dietary-memory normalization so non-finite tracked levels are treated as missing instead of being saturated into full nutrition.
- Fixed synergy external-source normalization so non-finite capability values are rejected instead of being saturated into full source pressure.
- Fixed capability multiplier attribute bounds by raising capability minimums from `0.0` to `0.25`, matching the runtime multiplier floor used by hydration, respiration, stamina, encumbrance, sleep, psychology, and acclimation calculations.
- Bumped the verified LAB test build from `0.1.0-alpha.57` to `0.1.0-alpha.58`.

Validation:

- Initial focused helper test against `EgoMariesLibFacade` failed before assertions with `NoClassDefFoundError: net/minecraft/world/entity/player/Player`; the pure sanitizer coverage was moved to `EgoDietaryMemory` to avoid loading Minecraft-bound facade signatures on the plain JUnit classpath.
- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.nutrition.EgoDietaryMemoryTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest --tests com.oblixorprime.immersiveego.registry.EgoAttributesTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with Immersive EGO metadata version `0.1.0-alpha.58` and `All 23 required tests passed`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.58.jar`.
- `.\scripts\install-mod.ps1` passed, regenerated `build/install-report.json`, deleted old installed jar `immersive_ego-0.1.0-alpha.57.jar`, installed `immersive_ego-0.1.0-alpha.58.jar`, verified matching source/installed SHA-256 `6bfc8983f272d2132158039fd5d320f6c040909a726f53355087f817927c3c68`, and confirmed exactly one installed `immersive_ego` jar remains.
- Built jar inspection confirmed `EgoMariesLibFacade.class`, `EgoDietaryMemory.class`, `EgoSynergyEvaluator.class`, `EgoAttributeCatalog.class`, and `META-INF/neoforge.mods.toml` are present.

Remaining:

- These fixes are automated-tested and LAB-installed. They are not separate live-client proof items because they harden data-boundary and attribute-bound behavior.

## 2026-06-27

### Weighted Mean Non-Finite Value Guard

Implemented:

- Fixed `EgoMath.weightedMean` so non-finite input values (`NaN`, positive infinity, negative infinity) are rejected before aggregation.
- Kept `EgoMath.clamp01` behavior unchanged because existing state normalization intentionally clamps non-finite and out-of-range raw state values.
- Added unit coverage for non-finite weighted-mean values.
- Bumped the verified LAB test build from `0.1.0-alpha.56` to `0.1.0-alpha.57`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.simulation.EgoMathTest` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with Immersive EGO metadata version `0.1.0-alpha.57` and `All 23 required tests passed`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.57.jar`.
- `.\scripts\install-mod.ps1` passed, regenerated `build/install-report.json`, deleted old installed jar `immersive_ego-0.1.0-alpha.56.jar`, installed `immersive_ego-0.1.0-alpha.57.jar`, verified matching source/installed SHA-256 `b04bad518361be95095e16f1a600bb26a9ed74334d869737173b03bd1d5c7e9e`, and confirmed exactly one installed `immersive_ego` jar remains.
- Built jar inspection confirmed `com/oblixorprime/immersiveego/simulation/EgoMath.class` and `META-INF/neoforge.mods.toml` are present.

Remaining:

- The fix is automated-tested and LAB-installed. It has not been separately live-client-proven because this was a pure math/input-hardening defect.

## 2026-06-22

### Desertborn Hot-Biome Vertical-Slice Proof Attempt

Implemented:

- Attempted the live Desertborn hot-biome vertical-slice proof against the verified Prism LAB `1.21.1 TesT LaB` instance and installed `immersive_ego-0.1.0-alpha.56.jar`.
- Confirmed the first accidental launch target was invalid because it opened `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1`; relaunched with the quoted instance id and verified the Java command line used `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB`.
- Backed up and temporarily enabled LAB runtime gates for the proof attempt, including `enableSimulation = true`, module `enabled = true` files, and the existing `New World` scheduler `enabled = true`.
- Confirmed the existing LAB player NBT had `immersive_ego:ego_state` and no `immersive_ego:ego_origin_state`, so an arid assignment could still be proven if the player were loaded in a desert/hot-dry biome.
- Tried a temporary `ego_desertborn_probe` datapack plus Prism `JoinWorldOnLaunch=New World` to run existing `/ego` commands on world load without changing mod code.
- Removed the temporary datapack, restored `JoinWorldOnLaunch=`, and restored the LAB simulation/module config files to their pre-attempt default-off state after the proof attempt did not reach a valid world-load command readback.

Validation:

- Correct LAB launch evidence: Java process used the GraalVM LAB Java path and included `1.21.1 TesT LaB` in its command line.
- `latest.log` for the LAB instance confirmed `immersive_ego-0.1.0-alpha.56.jar` was discovered from the LAB mods folder and Immersive EGO bootstrap loaded.
- Prism `JoinWorldOnLaunch=New World` did not pass a `quickPlay` or `New World` argument to the Java command line, and no integrated-world markers or `EGO_PROOF` datapack markers appeared in `latest.log`.
- The supported Windows-control plugin failed before attachment with an internal `@oai/sky` export error: `Package subpath './dist/project/cua/sky_js/src/targets/windows/internal/computer_use_client_base.js' is not defined by "exports"`.
- Fallback focus attempts could not make Minecraft the foreground window; Windows reported foreground handle `0`, and mouse/window-message attempts did not change the LAB log.
- Cleanup verification showed `JoinWorldOnLaunch=` restored, `ego_desertborn_probe` removed, `enableSimulation = false`, module gates back to `enabled = false`, the `New World` scheduler back to `enabled = false`, and no Java client left running.

Remaining:

- The Desertborn live vertical-slice gate remains open.
- No evidence was captured for desert/hot-dry biome, arid origin assignment, scheduler-updated EGO state, sprint/rest/eat/sleep progression, or Apothic display changes during the Desertborn slice.
- Next attempt needs a controllable unlocked desktop session or user-assisted live input, then should capture `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin`, and screenshots/log readback after loading a desert biome with simulation gates enabled.

### Apothic Live UI Proof Gate

Implemented:

- Closed the live Apothic Attributes visibility gate for `immersive_ego` alpha.56 without Java/source behavior changes.
- Preserved the live proof screenshot at `docs/proof/apothic-live-ui-alpha56-20260622-060914.png`.
- Used the existing LAB install with `immersive_ego_civitas` still disabled because that companion jar targets alpha.44, not alpha.56.

Validation:

- Active installed Immersive EGO jar remained `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\immersive_ego-0.1.0-alpha.56.jar`.
- `build/install-report.json` previously verified the installed alpha.56 source/target SHA-256 as `5bba60ff0b79bd2bfaac7bc89a9ac65fe385740aefdc89066d7bb481e317fd73`, with hash match true and exactly one installed `immersive_ego` jar.
- Active LAB runtime included Apothic Attributes `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods\ApothicAttributes-1.21.1-2.9.1.jar`.
- Opened the existing `New World` save through Prism LAB, respawned, hosted the integrated world over LAN with commands enabled, set difficulty to Peaceful, disabled mob spawning, and switched back to Survival Mode so the vanilla survival inventory was available.
- `latest.log` confirmed `Local game hosted on port [55491]`, `The difficulty has been set to Peaceful`, `Gamerule doMobSpawning is now set to: false`, and `Set own game mode to Survival Mode`.
- Opened the Apothic Attributes `Attributes` panel from the survival inventory and scrolled to EGO rows. The captured panel visibly shows `EGO Acclimation Gain`, `EGO Hydration Efficiency`, and `EGO Load`.
- Proof screenshot SHA-256: `A08C1F866DE35C6851911B23227CA4D95D6E1BF675217DB0FC5369870EE7357A`.

Remaining:

- The Apothic Attributes visibility gate is closed at live-client proof level.
- The next release gate is still the live Desertborn hot-biome vertical-slice proof.
- No build or install was rerun in this pass because no source/runtime jar changes were made.

### Apothic UI Gate Attempt and LAB Companion Cleanup

Implemented:

- Moved the incompatible LAB-only companion jar `immersive_ego_civitas-0.1.0-alpha.35.jar` from `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods` to `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\mods-disabled`.
- Left the active LAB mods tree with exactly one active `immersive_ego` runtime jar: `immersive_ego-0.1.0-alpha.56.jar`.
- Inspected the installed Apothic Attributes 2.9.1 jar enough to confirm the player attributes UI is attached to the vanilla survival inventory via the `Show Attributes` button. No Immersive EGO code was changed.

Validation:

- Crash report `<USER_HOME>\AppData\Roaming\PrismLauncher\instances\1.21.1 TesT LaB\minecraft\crash-reports\crash-2026-06-22_02.39.31-fml.txt` showed `immersive_ego_civitas` required `immersive_ego 0.1.0-alpha.44` while the active installed mod was `0.1.0-alpha.56`.
- Disabled companion jar SHA-256 after move: `60CC0E3241B50C3A570144FAE86C6528D8FD7B3B8D94EC36029FC3689A74FFAA`.
- Active installed Immersive EGO jar SHA-256 remained `5BBA60FF0B79BD2BFAAC7BC89A9AC65FE385740AEFDC89066D7BB481E317FD73`.
- LAB launched after the companion cleanup and reached the Minecraft main menu with NeoForge 21.1.233 and 39 mods; screenshot: `<USER_HOME>\AppData\Local\Temp\immersive_ego_lab_minecraft_20260622_041808.png`.
- Existing save `New World` reached survival inventory after `/gamemode survival`; screenshot: `<USER_HOME>\AppData\Local\Temp\immersive_ego_lab_gamemode_survival_20260622_042209.png`.
- The existing save proof attempt ended before Apothic UI capture because the player died in that world.
- A fresh world proof attempt reached world creation/data-pack reload; screenshot: `<USER_HOME>\AppData\Local\Temp\immersive_ego_lab_create_world_screen_20260622_044112.png`.
- `latest.log` stopped advancing after `2026-06-22 04:41:16` during the fresh world reload, and the visible desktop was blocked by a Windows sign-in/lock overlay; screenshot: `<USER_HOME>\AppData\Local\Temp\immersive_ego_lab_current_20260622_044621.png`.
- Stopped the launched Minecraft `javaw` process `21076` after the proof attempt could no longer advance.

Remaining:

- The Apothic Attributes visibility gate remains open. There is still no live screenshot/manual proof of the Apothic Attributes GUI showing Immersive EGO attributes.
- The next proof pass should run with the Windows session kept unlocked and should use a controlled survival test world instead of the stale creative/death-prone save.
- Superseded later on 2026-06-22 by `Apothic Live UI Proof Gate`, which captured the required live Apothic UI screenshot with EGO rows visible.

### Release Provenance and Credits Refresh

Implemented:

- Refreshed `docs/SOURCE_PROVENANCE.md` with the complementary project pack import source, staging path, actual archive SHA-256, embedded `SHA256.txt` mismatch, installed reference/governance material, preserved `docs/project_pack/` material, and runtime packaging policy.
- Updated `docs/CREDITS.md`, `docs/LICENSE_MATRIX.md`, and `THIRD_PARTY_NOTICES.md` so credits, license decisions, and third-party notices reflect the imported pack/reference material without treating it as third-party runtime assets.
- Updated `TASKS.md` to mark the source provenance and credits review complete while leaving live Apothic UI proof, live Desertborn vertical-slice proof, remaining runtime progression tests, CurseForge metadata, and final release evidence open.
- Updated `docs/CODEX_HANDOFF.md` and `docs/FULL_PROJECT_REVIEW.md` so the release state no longer lists provenance/credits as an open blocker.
- Expanded `docs/CURSEFORGE_DESCRIPTION.md` into a release-facing alpha metadata draft with current alpha.56 status, dependencies, verification status, known release gaps, project boundaries, credits/license language, and suggested CurseForge metadata.
- Refreshed the Notion dashboard page `Immersive EGO Full Project Review - 2026-06-22` at `<PRIVATE_NOTION_REFERENCE>` with the current release blockers, time/token estimate, and updated proof summary.

Validation:

- `.\scripts\verify_ego_repo.ps1` passed and confirmed all required governance/docs files are present; Java reported OpenJDK `21.0.11`.
- `git diff --check` exited 0; Git printed only existing LF-to-CRLF working-copy normalization warnings.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.56.jar | Select-String -Pattern 'neoforge\.mods\.toml\.template|project_pack|config_templates|codex_prompts|^branding/'` returned no matches before and after the clean build/install pass.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.56.jar`.
- `.\scripts\install-mod.ps1` passed, regenerated `build/install-report.json` and `build/runtime-deps-report.json`, and verified source/installed alpha.56 hashes match with exactly one installed `immersive_ego` jar.
- Notion readback verified the review page title `Immersive EGO Full Project Review - 2026-06-22`, parent hub `Immersive EGO`, links to the master prompt and missing project pack import page, live-client blocker list, and no-extra-GPT recommendation.

Remaining:

- Public release is still blocked by live Apothic Attributes UI proof, live Desertborn vertical-slice client proof, remaining runtime progression checks, final screenshots/live-proof additions to CurseForge metadata, and final release evidence.

## 2026-06-21

### Full Project Review and Evidence Refresh

Implemented:

- Added `docs/FULL_PROJECT_REVIEW.md` as the synchronized review artifact for the Immersive EGO master prompt, current alpha.56 implementation, system data flow, proof levels, dirty worktree state, remaining blockers, and next actions.
- Created the matching Notion child dashboard page under the existing Immersive EGO hub, now titled `Immersive EGO Full Project Review - 2026-06-22`: `<PRIVATE_NOTION_REFERENCE>`.
- Updated `TASKS.md` to mark the full review/Notion mirror complete and keep the live Desertborn vertical-slice client proof open.
- Refreshed build, GameTest, Prism LAB install, runtime dependency, and dedicated-server smoke evidence for alpha.56.
- Documented that Apothic Attributes UI visibility and the Desertborn vertical slice remain live-client proof gaps.
- Documented that no extra GPT/subagents are needed for the decision-summary review; explorer subagents are only recommended for a future deeper code-level audit.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with all 23 required GameTests, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.56.jar`.
- `.\scripts\install-mod.ps1` passed, regenerated `build/install-report.json` and `build/runtime-deps-report.json`, and verified source/installed alpha.56 hashes match with exactly one installed `immersive_ego` jar.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed with all required startup markers and no failure matches.
- Notion readback verified the review page title, parent hub `Immersive EGO`, links to the master prompt and missing project pack import page, alpha.56 proof summary, Apothic UI live-client gap, and no-extra-GPT recommendation.
- `git diff --check` exited 0; Git printed only existing LF-to-CRLF working-copy normalization warnings.

Remaining:

- No remaining artifact-sync work for this review pass. The live-client proof gaps remain documented in `docs/FULL_PROJECT_REVIEW.md`.

### Complementary Missing Project Pack Import

Implemented:

- Inspected `<USER_HOME>\Downloads\immersive_ego_missing_project_pack.zip` and staged it under `C:\AI-Work\immersive_ego_missing_project_pack_stage_20260621_1718`.
- Installed additive pack material into `branding/`, `codex_prompts/`, `config_templates/`, root `*.template` files, `scripts/verify_ego_repo.ps1`, and missing planning docs under `docs/`.
- Preserved pack metadata, the `neoforge.mods.toml.template`, and older language catalogs under `docs/project_pack/immersive_ego_missing_project_pack/`.
- Did not overwrite current live alpha files such as `AGENTS.md`, `README.md`, `README_FR.md`, `CHANGELOG.md`, `TASKS.md`, existing maintained docs, `.github/workflows/build.yml`, `LICENSE`, `THIRD_PARTY_NOTICES.md`, or active runtime language JSON files.

Validation:

- `.\scripts\verify_ego_repo.ps1` passed and confirmed all required governance/docs files are present; Java reported OpenJDK `21.0.11`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.56.jar`.
- Built jar SHA-256 remained `5BBA60FF0B79BD2BFAAC7BC89A9AC65FE385740AEFDC89066D7BB481E317FD73`; size remained `464244` bytes.
- `jar tf build/libs/immersive_ego-0.1.0-alpha.56.jar | Select-String -Pattern 'neoforge\.mods\.toml\.template|project_pack|config_templates|codex_prompts|^branding/'` returned no matches, confirming the imported reference/template material was not packaged into the runtime jar.
- `git diff --check` exited 0; Git printed only existing CRLF normalization warnings for dirty working-copy files.

Remaining:

- The pack `SHA256.txt` value does not match the actual downloaded archive SHA-256; see `docs/project_pack/immersive_ego_missing_project_pack/IMPORT_NOTES.md`.
- Imported planning docs are reference material and must be checked against live source before being treated as current implementation status.

### Phase 11 Psychology Runtime GameTest

Implemented:

- Added runtime GameTest `psychology_runtime_records_injury_and_updates_server_state`.
- The GameTest enables the core simulation and psychology module flags temporarily, creates a server player with a live health deficit, verifies `EgoPsychologyRuntime.context` records persisted `EgoInjuryMemory`, and verifies pain/fear pressure updates server-authoritative stress, focus, comfort, and recovery capacity through `EgoPsychologyService`.
- Bumped the verified LAB test build to `0.1.0-alpha.56`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 23 required tests passed`, mod metadata version `0.1.0-alpha.56`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.56.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoPsychologyRuntime.class`, `EgoPsychologyService.class`, `EgoInjuryMemory.class`, and `META-INF/neoforge.mods.toml` are present.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.56.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.55.jar`.
- Final source and installed SHA-256: `5bba60ff0b79bd2bfaac7bc89a9ac65fe385740aefdc89066d7bb481e317fd73`.
- Source and installed jar size: `464244` bytes.
- `build/install-report.json` confirms source/installed hashes match, metadata `modId="immersive_ego"`, version `0.1.0-alpha.56`, display name `Immersive EGO`, and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.56 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Full live death/respawn beyond clone-event attachment policy, full real world restart save/load persistence, live dimension transfer, broader live food-consumption variety and diminishing-return behavior, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted the generic Gradle 10 deprecation notice; no repo-owned warning was isolated in this run.

### Phase 11 MariesLib Food-Consumption Runtime GameTest

Implemented:

- Added runtime GameTest `food_consumption_event_updates_marieslib_memory_and_server_state` to post the live NeoForge food-use finish event for bread with the core simulation and nutrition module temporarily enabled.
- The GameTest verifies MariesLib dietary memory records the consumed food source, raises the grains memory level, and persists a higher `nutrition_balance` into server-authoritative `EgoState`.
- Replaced the first mock-player approach with a bare `ServerPlayer` construction so the test does not fake a full client join and trigger Apothic Attributes client config packet sync inside the GameTest harness.
- Bumped the verified LAB test build to `0.1.0-alpha.55`.

Validation:

- Initial `.\gradlew.bat --no-daemon runGameTestServer` failed when the test used `GameTestHelper.makeMockPlayer(GameType.SURVIVAL)` because the production event handler requires a `ServerPlayer`.
- A second `.\gradlew.bat --no-daemon runGameTestServer` failed when the test used `GameTestHelper.makeMockServerPlayerInLevel()` because Apothic Attributes attempted to send `apothic_attributes:config` to the mock client connection.
- `.\gradlew.bat --no-daemon compileJava` passed after switching to a bare `ServerPlayer`.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 22 required tests passed`, mod metadata version `0.1.0-alpha.55`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.55.jar`.
- `.\gradlew.bat --no-daemon clean build --warning-mode all` passed and did not print repo-owned deprecation details in this run.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoNutritionEvents.class`, `EgoMariesLibFacade.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Built and installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.55"`, and display name `Immersive EGO`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.55.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.54.jar`.
- Final source and installed SHA-256: `b8e4c48bbcc6e48c379b76612f10df56a885ce64de7a63b197a2f3eef1ee7de9`.
- Source and installed jar size: `463302` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- Rechecking after the warning-mode clean build confirmed the rebuilt source jar and installed jar still match SHA-256 `b8e4c48bbcc6e48c379b76612f10df56a885ce64de7a63b197a2f3eef1ee7de9`.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.55 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Full live death/respawn beyond clone-event attachment policy, full real world restart save/load persistence, live dimension transfer, broader live food-consumption variety and diminishing-return behavior, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- The normal `clean build` footer emitted the generic Gradle 10 deprecation notice; the follow-up `--warning-mode all` pass did not expose a repo-owned warning to patch in this run.

### Phase 11 Death-Respawn Clone Policy GameTest

Implemented:

- Added runtime GameTest `state_origin_and_injury_attachments_copy_on_death_clone_event` to post NeoForge `PlayerEvent.Clone` with `wasDeath=true` and verify `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` copy onto the respawned player through the `copyOnDeath` attachment path.
- Bumped the verified LAB test build to `0.1.0-alpha.54`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 21 required tests passed`, mod metadata version `0.1.0-alpha.54`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.53` to `0.1.0-alpha.54`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.54.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoAttachments.class`, `EgoState.class`, `EgoOriginState.class`, `EgoInjuryMemory.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Built and installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.54"`, and display name `Immersive EGO`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.54.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.53.jar`.
- Final source and installed SHA-256: `6cb61c1130cad8a40dbdcac4be3525317a00cf82ddb015309ecd39bb08796a5e`.
- Source and installed jar size: `462102` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.54 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Full live death/respawn beyond clone-event attachment policy, full real world restart save/load persistence, live dimension transfer, live MariesLib food-consumption memory, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Attachment NBT Persistence GameTest

Implemented:

- Added runtime GameTest `state_origin_and_injury_attachments_survive_player_nbt_round_trip` to assert the serialized NeoForge attachment keys for `EgoState`, `EgoOriginState`, and `EgoInjuryMemory`, then reload a fresh mock player from saved player NBT.
- Bumped the verified LAB test build to `0.1.0-alpha.53`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 20 required tests passed`, mod metadata version `0.1.0-alpha.53`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.52` to `0.1.0-alpha.53`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.53.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoAttachments.class`, `EgoState.class`, `EgoOriginState.class`, `EgoInjuryMemory.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Built and installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.53"`, and display name `Immersive EGO`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.53.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.52.jar`.
- Final source and installed SHA-256: `1dadc8cba984f5b793b5e0253413f5a822ea66fe3de5a40ba10322e5fa7c939a`.
- Source and installed jar size: `461553` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.53 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Full real world restart save/load persistence, death/respawn, live dimension transfer, live MariesLib food-consumption memory, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Malformed Dynamic Config Fallback

Implemented:

- Hardened `EgoDynamicServerConfigLoader` so malformed dynamic `simulation.toml` parser failures reset only that Immersive EGO dynamic file to safe scheduler and display defaults.
- Hardened `EgoDynamicSynergyConfigLoader` so malformed dynamic `synergies.toml` parser failures reset only that Immersive EGO dynamic file to an empty edge graph.
- Added JUnit regressions `repairsMalformedSimulationTomlToDefaults` and `repairsMalformedSynergyTomlToEmpty`.
- Added runtime GameTest `malformed_dynamic_configs_repair_to_safe_defaults` to prove both malformed dynamic config repairs under the Minecraft/NeoForge classpath.
- Bumped the verified LAB test build to `0.1.0-alpha.52`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- Initial `.\gradlew.bat --no-daemon test` failed because the parser-fallback catch path used `ImmersiveEgo.LOGGER`, which loaded NeoForge bootstrap classes on the pure JUnit classpath and triggered `NoClassDefFoundError: net/neoforged/fml/config/IConfigSpec`. Removed that pure-loader dependency.
- `.\gradlew.bat --no-daemon compileJava` passed after the loader adjustment.
- `.\gradlew.bat --no-daemon test` passed after the loader adjustment.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 19 required tests passed`, mod metadata version `0.1.0-alpha.52`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.51` to `0.1.0-alpha.52`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.52.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoDynamicServerConfigLoader.class`, `EgoDynamicSynergyConfigLoader.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.52"`, and display name `Immersive EGO`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.52.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.51.jar`.
- Final source and installed SHA-256: `78a6e1edb727b6588cfd61d1d1cd461022c3c4fd7d3e3266b1343dae34ec1d6f`.
- Source and installed jar size: `460832` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.52 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Real save/load attachment persistence, death/respawn, live dimension transfer, live MariesLib food-consumption memory, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Attachment/Dimension Runtime GameTests

Implemented:

- Added runtime GameTest `state_origin_and_injury_attachments_round_trip_on_mock_player` to prove mutated `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` attachments read back under the Minecraft runtime classpath.
- Added runtime GameTest `respiratory_dimension_mapping_updates_server_state` to guard Overworld/Nether/End/other dimension-key classification and prove dimension pressure writes server-authoritative `respiratory_strain`.
- Exposed `EgoRespiratorySampler.dimensionFrom` with a null guard so runtime tests can verify the same dimension classifier used by live server sampling.
- Strengthened `scripts/smoke-dedicated-server.ps1` failure scanning with explicit `net.minecraft.client` class-load signatures.
- Bumped the verified LAB test build to `0.1.0-alpha.51`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 18 required tests passed`, mod metadata version `0.1.0-alpha.51`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.50` to `0.1.0-alpha.51`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.51.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoRespiratorySampler.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.51"`, and display name `Immersive EGO`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.51.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.50.jar`.
- Final source and installed SHA-256: `ccd193ef35842d29196ea4ce48adc3e5935f1ae8d323d55ebe8b3dfbcb4baab5`.
- Source and installed jar size: `458586` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.51 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Real save/load attachment persistence, death/respawn, live dimension transfer, live MariesLib food-consumption memory, malformed config fallback, optional Apotheosis presence, packaged dedicated-server no-client-class-load proof, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Runtime Catalog/Stamina/Sleep Debt GameTests

Implemented:

- Added runtime GameTest `attribute_catalog_paths_are_unique_bounded_and_sentimented` to guard EGO attribute path uniqueness, finite bounds, defaults inside bounds, and display sentiment.
- Added runtime GameTest `stamina_progression_consumes_and_recovers_server_state` to prove sprinting consumes and resting recovers persisted server-authoritative stamina.
- Added runtime GameTest `sleep_debt_progression_increases_server_fatigue` to prove awake-night sleep pressure increases persisted server-authoritative fatigue.
- Added runtime GameTest `nap_progression_recovers_less_fatigue_than_night_sleep` to prove a day nap recovers fatigue but less than full night sleep.
- Bumped the verified LAB test build to `0.1.0-alpha.50`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 16 required tests passed`, mod metadata version `0.1.0-alpha.50`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.49` to `0.1.0-alpha.50`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.50.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoAttributeCatalog.class`, `EgoStaminaService.class`, `EgoSleepService.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.50"`, display name `Immersive EGO`, required Apothic Attributes and MariesLib dependencies, and optional Apotheosis metadata.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.50.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.49.jar`.
- Final source and installed SHA-256: `67d46d84e9c31605f435a02b1b0a88da56ddbdece229f8af154b5e36b3cfbc0c`.
- Source and installed jar size: `457159` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.50 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Death/respawn, dimension change, live MariesLib food-consumption memory, malformed config fallback, optional Apotheosis presence, no-client-class-load runtime smoke, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Runtime Progression GameTests

Implemented:

- Added runtime GameTest `hydration_progression_reduces_server_authoritative_state` to prove hydration demand reduces persisted server-authoritative `EGO_STATE` hydration under the Minecraft classpath.
- Added runtime GameTest `sleep_progression_recovers_server_authoritative_state` to prove night sleep recovery reduces fatigue and improves recovery capacity, focus, and comfort on persisted `EGO_STATE`.
- Added runtime GameTest `nutrition_progression_from_vanilla_food_updates_server_state` to prove vanilla-food nutrition memory can raise persisted nutrition balance through the runtime nutrition service.
- Bumped the verified LAB test build to `0.1.0-alpha.49`.

Validation:

- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 12 required tests passed`, mod metadata version `0.1.0-alpha.49`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.48` to `0.1.0-alpha.49`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.49.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoHydrationService.class`, `EgoSleepService.class`, `EgoNutritionService.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.49"`, display name `Immersive EGO`, required Apothic Attributes and MariesLib dependencies, and optional Apotheosis metadata.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.49.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.48.jar`.
- Final source and installed SHA-256: `ee92f1a4f486e534bb41b1d5b45a7795acc4c671411d6f8d637c93b49e474599`.
- Source and installed jar size: `455830` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.
- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed for alpha.49 with `success=true`, all required markers seen, and no failure matches.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Death/respawn, dimension change, stamina progression, sleep debt/nap progression, live MariesLib food-consumption memory, malformed config fallback, optional Apotheosis presence, no-client-class-load runtime smoke, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 11 Dedicated Server Smoke

Implemented:

- Added `scripts/smoke-dedicated-server.ps1` for a bounded, repeatable Gradle `runServer` startup smoke.
- The smoke requires explicit `-AcceptEula` when the local `run/eula.txt` is missing, writes `build/dedicated-server-smoke-report.json`, and preserves stdout/stderr logs under `build/`.
- The smoke waits for Immersive EGO bootstrap, dynamic simulation config, dynamic synergy config, MariesLib scanner config, and vanilla dedicated-server completion markers, then terminates the dev server after success.
- Bumped the verified LAB test build to `0.1.0-alpha.48`.

Validation:

- `.\scripts\smoke-dedicated-server.ps1 -AcceptEula -TimeoutSeconds 180` passed and wrote `build/dedicated-server-smoke-report.json` with `success=true`, `timedOut=false`, `terminatedAfterSuccess=true`, all required markers seen, and no failure matches.
- The preserved smoke stdout log identifies `Immersive EGO 0.1.0-alpha.48 (immersive_ego)`, loaded the MariesLib scanner spec from `run/config/immersive_ego/scanner_spec.json`, and completed `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 9 required tests passed`, mod metadata version `0.1.0-alpha.48`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.48.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoInjuryMemory.class`, `EgoOriginState.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.48"`, display name `Immersive EGO`, required Apothic Attributes and MariesLib dependencies, and optional Apotheosis metadata.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.48.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.47.jar`.
- Final source and installed SHA-256: `40b921336470a3ec33891521a6c17f43c6a23efdeed2138825ecf62a11f557b1`.
- Source and installed jar size: `454508` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Optional Apotheosis presence, broader runtime progression GameTests, no-client-class-load runtime smoke, live pain/recovery tuning, committed/pushed release evidence, and release metadata completion remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

## 2026-06-20

### Phase 10/11 Command and Server Boundary Hardening

Implemented:

- Added runtime GameTest `debug_and_synergy_commands_require_permission_level_two` to prove `/ego debug` and `/ego synergy` reject permission level `0` and accept permission level `2`.
- Added `EgoProjectBoundaryTest.mainSourcesDoNotReferenceClientOnlyClasses` to guard against accidental client-only class references in main server-loaded sources.
- Added `EgoProjectBoundaryTest.apotheosisMetadataStaysOptionalAndSourcesDoNotRequireIt` to keep Apotheosis optional in metadata and prevent hard source references to optional Apotheosis APIs.
- Bumped the verified LAB test build to `0.1.0-alpha.47`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 9 required tests passed`, mod metadata version `0.1.0-alpha.47`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.46` to `0.1.0-alpha.47`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.47.jar`.
- Built and installed jar inspection confirmed `EgoGameTests.class`, `EgoInjuryMemory.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- Installed jar metadata confirms `modId="immersive_ego"`, `version="0.1.0-alpha.47"`, display name `Immersive EGO`, required Apothic Attributes and MariesLib dependencies, and optional Apotheosis metadata.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.47.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.46.jar`.
- Final source and installed SHA-256: `7376ae06ebf45a6aee76aabdc5263f342f9443ec97d7bcf85de24195577caa25`.
- Source and installed jar size: `454509` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Dedicated server smoke, optional Apotheosis presence, broader runtime progression GameTests, no-client-class-load runtime smoke, and live pain/recovery tuning remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 8 Persistent Injury Memory

Implemented:

- Added persisted `EgoInjuryMemory` for recent injury, lingering pain, and recovery debt.
- Added `EgoInjuryMemoryCodecs` and registered `immersive_ego:ego_injury_memory` as a copied-on-death NeoForge attachment.
- Added `EgoInjuryMemoryContext` and `EgoInjuryMemoryService` for pure injury recording, lingering pain pressure, and recovery decay.
- Wired `EgoPsychologyRuntime` to update injury memory and feed persistent pain pressure into psychology only when the core simulation and default-off psychology module gates are enabled.
- Added `EgoInjuryMemoryServiceTest` and runtime GameTest `injury_memory_attachment_defaults_to_clear`.
- Bumped the verified LAB test build to `0.1.0-alpha.46`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.psychology.EgoInjuryMemoryServiceTest --tests com.oblixorprime.immersiveego.psychology.EgoPsychologyServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 8 required tests passed`, mod metadata version `0.1.0-alpha.46`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.45` to `0.1.0-alpha.46`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.46.jar`.
- Built and installed jar inspection confirmed `EgoInjuryMemory.class`, `EgoInjuryMemoryCodecs.class`, `EgoInjuryMemoryContext.class`, `EgoInjuryMemoryService.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.46.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.45.jar`.
- Final source and installed SHA-256: `88b68433589f549034d4dc7de88a7f01641c84e4122ea46942247401d1502c9e`.
- Source and installed jar size: `453726` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Dedicated server smoke, optional Apotheosis absence, no client class-load-on-server GameTest, broader runtime progression GameTests, and live pain/recovery tuning remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Phase 8/9 Psychology and Origins Foundation

Implemented:

- Added default-off psychology/recovery budget foundation for fear pressure, pain pressure, stability support, comfort support, stress resilience, stress, focus, comfort, and recovery capacity.
- Added persistent `EgoOriginState` attachment with origin id plus heat, cold, altitude, and humidity acclimation axes.
- Added origin profile assignment from birth-biome-style biome ids and deterministic acclimation progression toward exposure and profile baselines.
- Added `/ego debug origin` for read-only origin/acclimation inspection.
- Wired optional psychology and origins contexts into scheduler frame calculation before derived EGO Load/Readiness.
- Added `EgoPsychologyServiceTest`, `EgoOriginsServiceTest`, `EgoSimulationFrameTest` coverage, and the `origin_state_attachment_defaults_to_unassigned` GameTest.
- Bumped the verified LAB test build to `0.1.0-alpha.45`.

Validation:

- First targeted test command failed because `EgoOriginsServiceTest` incorrectly expected arid cold acclimation not to move toward the origin baseline; corrected the test expectation.
- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.psychology.EgoPsychologyServiceTest --tests com.oblixorprime.immersiveego.origins.EgoOriginsServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 7 required tests passed`, mod metadata version `0.1.0-alpha.45`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.44` to `0.1.0-alpha.45`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.45.jar`.
- Built and installed jar inspection confirmed `EgoPsychologyService.class`, `EgoOriginState.class`, `EgoOriginStateCodecs.class`, `EgoOriginsRuntime.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.45.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.44.jar`.
- Final source and installed SHA-256: `a2f3051a3e542d5a507781bf4daa0164deb307ca114bdfe38bf249be10513011`.
- Source and installed jar size: `445271` bytes.
- `build/install-report.json` confirms source/installed hashes match and exactly one declared `immersive_ego` jar remains.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run.
- Dedicated server smoke, optional Apotheosis absence, no client class-load-on-server GameTest, and broader runtime progression GameTests remain.
- Gradle emitted deprecation warnings about Gradle 10 incompatibility; no build failure is tied to them yet.

### Bug Hunt: Display Mirror Epsilon

Implemented:

- Fixed `EgoAttributeMirrorService` so configured mirror update epsilon values are honored before reapplying transient display mirror modifiers.
- Wired scheduler-driven mirror refreshes through per-world `display.mirrorUpdateEpsilon`.
- Added `EgoAttributeMirrorServiceTest.mirrorUpdatesRespectConfiguredDeltaThreshold`.
- Bumped the verified LAB test build to `0.1.0-alpha.44`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.simulation.EgoAttributeMirrorServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 6 required tests passed`, mod metadata version `0.1.0-alpha.44`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.43` to `0.1.0-alpha.44`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.44.jar`.
- Built and installed jar inspection confirmed `EgoAttributeMirrorService.class`, `EgoSimulationScheduler.class`, and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.44.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.43.jar`.
- Final source and installed SHA-256: `f2da0c66e11a64b4d61cc98a56c3283ae0bfadf9d1561bd977345c4c4ad43b2d`.
- Source and installed jar size: `416734` bytes.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Independent metadata scan of the Prism LAB mods directory found `remainingInstalledJarCount=1` for declared mod id `immersive_ego`.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with the simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Gradle emitted deprecation warnings during validation commands about future Gradle 10 incompatibility; no specific build failure is tied to them yet.

### Bug Hunt: Dynamic Config Integer Repair

Implemented:

- Fixed `EgoDynamicServerConfigLoader` so huge numeric TOML values are clamped before narrowing to `int`.
- Added `EgoDynamicServerConfigLoaderTest.clampsHugeIntegerValuesBeforeNarrowing` to cover `Long.MAX_VALUE`-sized `scheduler.intervalTicks` values.
- Bumped the verified LAB test build to `0.1.0-alpha.43`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 6 required tests passed`, mod metadata version `0.1.0-alpha.43`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.42` to `0.1.0-alpha.43`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.43.jar`.
- Built and installed jar inspection confirmed `EgoDynamicServerConfigLoader.class` and `META-INF/neoforge.mods.toml` are present, while scanner resource entries are absent.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.43.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.42.jar`.
- Final source and installed SHA-256: `f7a21a9f3efa2dae415504131d0541f81d6eefb9dba84a4fe9b3ad05933e0210`.
- Source and installed jar size: `416251` bytes.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Independent metadata scan of the Prism LAB mods directory found `remainingInstalledJarCount=1` for declared mod id `immersive_ego`.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with the simulation modules enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Gradle emitted deprecation warnings during validation commands about future Gradle 10 incompatibility; no specific build failure is tied to them yet.

### Bug Hunt: Scanner Resource Drift and Nutrition Rate Limit

Implemented:

- Removed the reintroduced bundled MariesLib scanner resource path under `src/main/resources/data/immersive_ego/immersive_ego/scanner/`.
- Added an `AGENTS.md` rule and `EgoProjectBoundaryTest.bundledMariesLibScannerResourcesAreNotPackaged` guard so the bundled scanner directory and `scanner_spec.json` are not packaged again.
- Fixed `EgoNutritionService` so the nutrition balance change limit scales by actual elapsed ticks for sub-second scheduler intervals.
- Added `EgoNutritionServiceTest.nutritionBalanceRateLimitScalesBelowOneSecond`.
- Bumped the verified LAB test build to `0.1.0-alpha.42`.

Validation:

- `.\gradlew.bat --no-daemon test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest --tests com.oblixorprime.immersiveego.nutrition.EgoNutritionServiceTest` passed.
- `.\gradlew.bat --no-daemon test` passed.
- `.\gradlew.bat --no-daemon compileJava` passed.
- `.\gradlew.bat --no-daemon runGameTestServer` passed with `All 6 required tests passed`, mod metadata version `0.1.0-alpha.42`, MariesLib scanner spec loaded from `run/config/immersive_ego/scanner_spec.json`, and `scanAndApply complete: 1333 confident, 0 uncertain`.
- `.\gradlew.bat --no-daemon clean build` passed and produced `build/libs/immersive_ego-0.1.0-alpha.42.jar`.
- Built and installed jar inspection confirmed `EgoMariesLibFacade.class`, `EgoNutritionService.class`, and `META-INF/neoforge.mods.toml` are present, while `scanner_spec.json` and `data/immersive_ego/immersive_ego/scanner` are absent.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.42.jar` into Prism LAB.
- Final install deleted old same-mod jar `immersive_ego-0.1.0-alpha.41.jar`.
- Final source and installed SHA-256: `fab98697b56a019422af940b95a60bcddba766fe887f5fbfacffa93178a27477`.
- Source and installed jar size: `416137` bytes.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Independent metadata scan of the Prism LAB mods directory found `remainingInstalledJarCount=1` for declared mod id `immersive_ego`.
- `build/runtime-deps-report.json` confirms Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config hashes match and exactly one installed jar remains for each dependency.

Remaining:

- No live client/server smoke pass was run with the nutrition module enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Gradle emitted deprecation warnings during some validation commands about future Gradle 10 incompatibility; no specific build failure is tied to them yet.

### Nutrition and MariesLib Foundation

Implemented:

- Added `EgoMariesLibFacade` as the only direct MariesLib integration point.
- Registered six stable MariesLib nutrition values: grains, protein, vegetables, fruit, fats, and minerals.
- Added the vanilla food nutrition catalog, dietary memory, meal quality, gut health, and nutrition service layers without custom items or copied assets.
- Wired server-side food completion triggers through MariesLib and scheduler nutrition readback into `nutrition_balance` behind the default-off nutrition module.
- Added deterministic tests for nutrition group keys, vanilla food catalog coverage, meal quality, nutrition balance movement, and frame ordering.
- Added runtime GameTest `vanilla_food_catalog_exposes_six_marieslib_nutrition_groups`.
- Created MariesLib's expected `config/immersive_ego/scanner_spec.json` before context registration when missing, preventing the scanner-spec reload path from falling back to missing bundled defaults.
- Bumped the final LAB test build to `0.1.0-alpha.41`.

Validation:

- Intermediate `runGameTestServer` attempts exposed real MariesLib startup failures: first `MarieLibContext not registered`, then `MarieLib registration delegate not configured`; both were fixed by registering a delegate-backed `MarieLibContext`.
- A later `runGameTestServer` pass exposed a MariesLib scanner-spec fallback error for `/data/immersive_ego/immersive_ego/scanner/scanner_spec.json`; this was fixed by deterministic config-file creation before MariesLib reload plus an owned bundled fallback resource at that path.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.nutrition.* --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.41`.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.41`.
- `.\gradlew.bat runGameTestServer` passed with `All 6 required tests passed`, verified mod metadata version `0.1.0-alpha.41`, loaded the `immersive_ego` MariesLib scanner spec from config, and logged `scanAndApply complete: 1333 confident, 0 uncertain`.
- The final GameTest log scan found no `Failed to load scanner_spec`, bundled scanner-spec, mod loading failure, or failed mod-instance markers.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.41.jar` into Prism LAB.
- Final install report deleted a pre-existing same-version jar before replacing it: `immersive_ego-0.1.0-alpha.41.jar`.
- Final source and installed SHA-256: `7b8945bb238de2dbea1be2312674bc3755c4895ff2b375b4be1e0f317cfe195a`.
- Source and installed jar size: `416655` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Installed LAB jar inspection confirms `EgoMariesLibFacade`, nutrition classes, `META-INF/neoforge.mods.toml`, and `data/immersive_ego/immersive_ego/scanner/scanner_spec.json` are present.
- Installed LAB jar bytecode inspection confirms `EgoMariesLibFacade.attach` registers a delegate-backed `MarieLibContext` and does not call `MariesLibBootstrap.attach`.
- Installed LAB jar metadata confirms declared mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.41`.
- A targeted installed-mod listing confirmed `immersive_ego-0.1.0-alpha.41.jar` is installed and the similarly named `immersive_ego_civitas-0.1.0-alpha.25.jar` remains a distinct companion jar.

Remaining:

- Nutrition remains gated behind disabled-by-default simulation and nutrition module switches.
- No live client/server smoke pass was run with the nutrition module enabled.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Gradle emitted deprecation warnings about future Gradle 10 incompatibility; no specific build failure is tied to them yet.

### Vanilla Phantom Rest Stat Verification

Implemented:

- Added `timeSinceRestTicks` to `EgoSleepContext`.
- Added `PHANTOM_SPAWN_RESTLESS_TICKS = 72000` and `phantomSpawnEligible()` for vanilla phantom spawn eligibility modeling.
- Updated `EgoSleepSampler` to read `Stats.CUSTOM.get(Stats.TIME_SINCE_REST)` from `ServerPlayer`.
- Added pure test coverage for the restless threshold.
- Added runtime GameTest `minecraft_sleep_api_matches_phantom_rest_model` to guard the Minecraft 1.21.1 public sleep/stat assumptions.
- Bumped the final LAB test build to `0.1.0-alpha.38`.

Validation:

- Local Mojmap bytecode inspection confirmed `Player.isSleepingLongEnough()` checks `sleepCounter >= 100`, `ServerPlayer.startSleeping` resets `Stats.TIME_SINCE_REST`, and `PhantomSpawner` uses `Stats.TIME_SINCE_REST` with a `72000` restless-tick threshold.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.sleep.EgoSleepCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.38`.
- `.\gradlew.bat runGameTestServer` passed with `All 5 required tests passed`, verified mod metadata version `0.1.0-alpha.38`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.37` to `0.1.0-alpha.38`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.38`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.38.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.37.jar`.
- Final source and installed SHA-256: `2e37d092018f86e7f92ba7bf30f6d9cbaca0d2e93c5b3385b8ad1b88fae5a37e`.
- Source and installed jar size: `389052` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Installed LAB jar inspection confirms `EgoGameTests`, sleep classes, `EgoSimulationFrameCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms declared mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.38`.
- A targeted declared-mod metadata check confirmed `immersive_ego-0.1.0-alpha.38.jar` declares `immersive_ego` and `immersive_ego_civitas-0.1.0-alpha.23.jar` declares `immersive_ego_civitas`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Sleep pressure/recovery is still gated behind disabled-by-default simulation and sleep module switches.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Sleep Pressure, Quality, and Nap Foundation

Implemented:

- Added `EgoCircadianPhase` for dawn, day, dusk, and night from Minecraft day time.
- Added `EgoSleepContext`, `EgoSleepInput`, `EgoSleepDelta`, `EgoSleepCalculator`, and `EgoSleepService`.
- Added awake sleep-pressure fatigue gain, night sleep fatigue recovery, recovery capacity/focus/comfort recovery, lower-quality day naps, and transient phantom-rest threshold modeling.
- Added `EgoSleepModule`, `EgoSleepSampler`, and `EgoSleepRuntime` behind the default-off sleep module gate.
- Wired optional sleep contexts into scheduler frame calculation after encumbrance load and before EGO Load/Readiness derivation.
- Added `EgoSleepCalculatorTest` and extended `EgoSimulationFrameTest`.
- Bumped the final LAB test build to `0.1.0-alpha.37`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.sleep.EgoSleepCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.37`.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.37`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.36` to `0.1.0-alpha.37`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.37`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.37.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.36.jar`.
- Final source and installed SHA-256: `259e4af9e524087d5d24cad9ec389edabb6087af637790aa3a0f0ea3e42c01d0`.
- Source and installed jar size: `388048` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Installed LAB jar inspection confirms `EgoCircadianPhase`, `EgoSleepCalculator`, `EgoSleepContext`, `EgoSleepDelta`, `EgoSleepInput`, `EgoSleepModule`, `EgoSleepRuntime`, `EgoSleepSampler`, `EgoSleepService`, `EgoSimulationFrameCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms declared mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.37`.
- A targeted declared-mod metadata check confirmed `immersive_ego-0.1.0-alpha.37.jar` declares `immersive_ego` and `immersive_ego_civitas-0.1.0-alpha.22.jar` declares `immersive_ego_civitas`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Runtime/GameTest proof for Minecraft 1.21.1 phantom rest qualification is still open.
- Sleep pressure/recovery is still gated behind disabled-by-default simulation and sleep module switches.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Encumbrance Hysteresis and Load Synergies

Implemented:

- Added `EgoEncumbranceTier`, `EgoEncumbranceHysteresis`, `EgoEncumbranceContext`, `EgoEncumbranceLoadDelta`, `EgoEncumbranceLoadCalculator`, `EgoEncumbranceService`, and `EgoEncumbranceRuntime`.
- Converted cached inventory load snapshots into stable normal, loaded, strained, and overburdened tiers with hysteresis.
- Added stamina drain and fatigue pressure from effective encumbrance load.
- Wired optional encumbrance contexts into scheduler frame calculation after stamina activity and before EGO Load/Readiness derivation.
- Added `EgoEncumbranceLoadCalculatorTest` and extended `EgoSimulationFrameTest`.
- Hardened `scripts/install-mod.ps1` so installed-jar matching reads declared `[[mods]]` entries instead of dependency `modId` entries.
- Bumped the final LAB test build to `0.1.0-alpha.36`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoEncumbranceLoadCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.36`.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.36`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.35` to `0.1.0-alpha.36`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.36`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.36.jar` into Prism LAB.
- First alpha.36 install deleted old installed jar: `immersive_ego-0.1.0-alpha.35.jar`.
- Final install after installer parser hardening replaced `immersive_ego-0.1.0-alpha.36.jar` in place and left `immersive_ego_civitas-0.1.0-alpha.21.jar` installed as a distinct companion mod.
- Final source and installed SHA-256: `9dc6af852ec2fc62d57deb0080727cff20e8bdedd85c0665bfc7029083248920`.
- Source and installed jar size: `375774` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one declared `immersive_ego` jar remains.
- Installed LAB jar inspection confirms `EgoEncumbranceContext`, `EgoEncumbranceHysteresis`, `EgoEncumbranceLoadCalculator`, `EgoEncumbranceLoadDelta`, `EgoEncumbranceModule`, `EgoEncumbranceRuntime`, `EgoEncumbranceService`, `EgoEncumbranceTier`, inventory weight classes, `EgoSimulationFrameCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms declared mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.36`.
- A targeted declared-mod metadata check confirmed `immersive_ego-0.1.0-alpha.36.jar` declares `immersive_ego` and `immersive_ego_civitas-0.1.0-alpha.21.jar` declares `immersive_ego_civitas`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Encumbrance load synergies are still gated behind disabled-by-default simulation and encumbrance module switches.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Inventory Weight Resolver and Cache

Implemented:

- Added `EgoEncumbranceModule` for the default-off encumbrance gate.
- Added `EgoWeightedStack` and `EgoItemWeightResolver` for deterministic item-stack weight heuristics.
- Added `EgoInventoryWeightCalculator` and `EgoInventoryWeightSnapshot` for total weight, load-tolerance-scaled capacity, normalized load ratio, and overburden pressure.
- Added `EgoInventoryWeightCache`, `EgoInventoryWeightDebugCounters`, `EgoInventoryWeightSampler`, and `EgoInventoryWeightService`.
- Wired scheduler passes to refresh the inventory weight cache when `enableSimulation`, `scheduler.enabled`, and the encumbrance module are enabled.
- Added `EgoInventoryWeightCalculatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.35`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoInventoryWeightCalculatorTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.35`.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.35`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.34` to `0.1.0-alpha.35`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.35`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.35.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.34.jar`.
- Final source and installed SHA-256: `af1f7a6f0662d080d33cfbff13de67c813127c7fc81c709bbdcf54567a0ce3a9`.
- Source and installed jar size: `365625` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoEncumbranceModule`, `EgoInventoryWeightCache`, `EgoInventoryWeightCalculator`, `EgoInventoryWeightDebugCounters`, `EgoInventoryWeightSampler`, `EgoInventoryWeightService`, `EgoInventoryWeightSnapshot`, `EgoItemWeightResolver`, `EgoWeightedStack`, stamina classes, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.35`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.19.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.35 jar has primary mod id `immersive_ego`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Inventory weight resolving/caching is still gated behind disabled-by-default simulation and encumbrance module switches.
- Load hysteresis and stamina load synergies remain future Phase 5 work.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Stamina Activity Cost and Regeneration

Implemented:

- Added `EgoStaminaActivityContext`, `EgoStaminaActivityDelta`, and `EgoStaminaActivityCalculator`.
- Added stamina cost inputs for movement intensity, sprinting, swimming, airborne state, item use, scheduler interval, and stamina capacity.
- Added `EgoStaminaActivitySampler` and `EgoStaminaRuntime` to sample server-side movement state and gate it behind the shared physiology module.
- Added `EgoStaminaService` to update only `stamina` on server-authoritative `EgoState`.
- Wired optional stamina activity contexts into `EgoSimulationFrameCalculator` before EGO Load/Readiness derivation.
- Added `EgoStaminaActivityCalculatorTest` and extended `EgoSimulationFrameTest`.
- Bumped the final LAB test build to `0.1.0-alpha.34`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoStaminaActivityCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.34`.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.34`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.33` to `0.1.0-alpha.34`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.34`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.34.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.33.jar`.
- Final source and installed SHA-256: `0c30ea12ca2c69ddeb9a858e5706d91f3505c283221975b87362ff47a69bf0f0`.
- Source and installed jar size: `352472` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoStaminaActivityCalculator`, `EgoStaminaActivityContext`, `EgoStaminaActivityDelta`, `EgoStaminaActivitySampler`, `EgoStaminaRuntime`, `EgoStaminaService`, `EgoPhysiologyModule`, respiratory classes, `EgoSimulationFrameCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.34`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.19.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.34 jar has primary mod id `immersive_ego`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Stamina activity cost/regeneration is still gated behind disabled-by-default simulation and physiology module switches.
- Weight resolver, inventory cache, encumbrance hysteresis, and stamina load synergies remain future Phase 5 work.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Respiratory Altitude, Depth, and Dimension Strain

Implemented:

- Added `EgoPhysiologyModule` to centralize the shared `enableSimulation` plus default-off physiology module gate.
- Added `EgoRespiratoryDimension`, `EgoRespiratoryPressureCalculator`, `EgoRespiratorySample`, and `EgoRespiratoryStrainCalculator`.
- Added altitude, depth, submersion, and dimension pressure inputs with respiration-efficiency scaling and normalized clamping.
- Added `EgoRespiratorySampler` and `EgoRespirationRuntime` to sample server-side player position, water state, dimension, sea level, and `RESPIRATION_EFFICIENCY`.
- Added `EgoRespirationService` to update only `respiratory_strain` on server-authoritative `EgoState`.
- Wired optional respiratory samples into `EgoSimulationFrameCalculator` before hydration demand and EGO Load/Readiness derivation.
- Added `EgoRespiratoryStrainCalculatorTest` and extended `EgoSimulationFrameTest`.
- Bumped the final LAB test build to `0.1.0-alpha.33`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoRespiratoryStrainCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed for version `0.1.0-alpha.33`.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.33`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.32` to `0.1.0-alpha.33`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.33`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.33.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.32.jar`.
- Final source and installed SHA-256: `1acdea17171e4b401362a6e3d98173fd103032f7508a6abb15e38b80747a8905`.
- Source and installed jar size: `344484` bytes.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoPhysiologyModule`, `EgoRespirationRuntime`, `EgoRespirationService`, `EgoRespiratoryDimension`, `EgoRespiratoryPressureCalculator`, `EgoRespiratorySample`, `EgoRespiratorySampler`, `EgoRespiratoryStrainCalculator`, `EgoSimulationFrameCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.33`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.18.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.33 jar has primary mod id `immersive_ego`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- Respiratory strain is still gated behind disabled-by-default simulation and physiology module switches.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Water Quality and Existing-Item Purification

Implemented:

- Added `EgoWaterQuality` tiers for contaminated, questionable, potable, and purified drinks.
- Added quality-aware hydration recovery and stress penalties for risky drink profiles.
- Added `EgoWaterPurificationMethod` and `EgoWaterPurificationService` for charcoal/coal filtering and campfire/furnace/smoker boiling.
- Added `EgoWaterPurificationItemResolver` to recognize vanilla purification items from Minecraft item stacks.
- Extended `EgoDrinkProfile` with water quality.
- Added `EgoWaterPurificationServiceTest`; extended hydration service and runtime GameTest coverage.
- Bumped the final LAB test build to `0.1.0-alpha.32`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoHydrationDemandCalculatorTest --tests com.oblixorprime.immersiveego.physiology.EgoHydrationServiceTest --tests com.oblixorprime.immersiveego.physiology.EgoWaterPurificationServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.32`, loaded both dynamic config files, logged all required SERVER config files as loaded/watched, and proved vanilla drink and purification item recognition under the Minecraft runtime classpath.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.31` to `0.1.0-alpha.32`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.32`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.32.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.31.jar`.
- Final source and installed SHA-256: `c0a6075d387447820e7a21c06fe8417165c233fa003a4be475150551b1c7b693`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoWaterQuality`, `EgoWaterPurificationMethod`, `EgoWaterPurificationService`, `EgoWaterPurificationItemResolver`, `EgoDrinkProfile`, `EgoHydrationService`, `EgoGameTests`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.32`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.16.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.32 jar has primary mod id `immersive_ego`.

Remaining:

- Purification is currently a recognized model/service foundation; no custom recipe, item, fluid, or asset has been added.
- Respiratory strain remains future work.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Hydration Demand and Drink Recognition

Implemented:

- Added `EgoHydrationDemand`, `EgoHydrationDemandInput`, `EgoHydrationDemandContext`, and `EgoHydrationDemandCalculator`.
- Added demand inputs from thermal strain, respiratory strain, fatigue, scheduler interval, and hydration efficiency.
- Added `EgoDrinkProfile` and `EgoDrinkProfileCatalog` for direct vanilla water potions, milk buckets, and honey bottles.
- Added `EgoDrinkItemResolver` using Minecraft item stacks and potion contents to recognize direct drinks while excluding non-water and splash potion forms.
- Added `EgoHydrationService` for server-authoritative hydration demand and drink recovery updates.
- Added `EgoHydrationEvents` on `LivingEntityUseItemEvent.Finish`, gated behind `enableSimulation` plus the default-off physiology module.
- Wired optional scheduler hydration demand into `EgoSimulationFrameCalculator` before EGO Load and Readiness derivation.
- Added `EgoHydrationDemandCalculatorTest` and `EgoHydrationServiceTest`; extended `EgoSimulationFrameTest`.
- Added GameTest coverage for runtime vanilla drink item recognition.
- Bumped the final LAB test build to `0.1.0-alpha.31`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.physiology.EgoHydrationDemandCalculatorTest --tests com.oblixorprime.immersiveego.physiology.EgoHydrationServiceTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest` passed.
- `.\gradlew.bat test` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 4 required tests passed`, verified mod metadata version `0.1.0-alpha.31`, loaded both dynamic config files, logged all required SERVER config files as loaded/watched, and proved vanilla drink item recognition under the Minecraft runtime classpath.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.30` to `0.1.0-alpha.31`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.31`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.31.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.30.jar`.
- Final source and installed SHA-256: `c4616b66b8fb33b020f7f671ec8725279f322ad0c844b8063dd1b01bbaef0cc0`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoHydrationDemand`, `EgoHydrationDemandCalculator`, `EgoHydrationDemandContext`, `EgoHydrationDemandInput`, `EgoDrinkProfile`, `EgoDrinkProfileCatalog`, `EgoDrinkItemResolver`, `EgoHydrationService`, `EgoHydrationEvents`, `EgoSimulationFrameCalculator`, `EgoGameTests`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.31`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.14.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.31 jar has primary mod id `immersive_ego`.

Remaining:

- Hydration demand and drink recovery are still gated behind disabled-by-default simulation and physiology module switches.
- Water quality and purification are not implemented yet.
- Respiratory strain remains future work.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Environment Cache and Thermal Mirror Slice

Implemented:

- Added `EgoEnvironmentCache`, `EgoEnvironmentDebugCounters`, and `EgoEnvironmentSnapshot` for player-scoped server environment snapshot caching.
- Added `EgoEnvironmentSampler` to derive climate profile, season phase, sky exposure, shelter, and wetness from live server biome/weather/player context.
- Added `EgoEnvironmentService` as the scheduler/debug command bridge for cached snapshots.
- Added `/ego debug environment` with cache counters, exposure sample details, and thermal snapshot output.
- Added `EgoSimulationFrameCalculator` and wired environment snapshots into scheduler frames so enabled environment passes update `thermal_strain` before deriving EGO Load and Readiness.
- Added `EgoEnvironmentCacheTest` and `EgoEnvironmentSamplerTest`; extended frame and GameTest command registration coverage.
- Bumped the final LAB test build to `0.1.0-alpha.30`.

Validation:

- Initial focused scheduler/environment test failed because the plain JUnit runtime loaded `EgoSimulationScheduler`, which references Minecraft `Player`; fixed by moving pure frame calculation into `EgoSimulationFrameCalculator`.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.environment.EgoEnvironmentCacheTest --tests com.oblixorprime.immersiveego.environment.EgoEnvironmentSamplerTest --tests com.oblixorprime.immersiveego.environment.EgoThermalSnapshotCalculatorTest` passed after the split.
- `.\gradlew.bat test` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified mod metadata version `0.1.0-alpha.30`, command registration including `/ego debug environment`, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.29` to `0.1.0-alpha.30`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.30`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.30.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.29.jar`.
- Final source and installed SHA-256: `7dec3ae707ed5f0516011d4d49758b4c67ef206abae06a7b33b3e972e1a905be`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoEnvironmentCache`, `EgoEnvironmentDebugCounters`, `EgoEnvironmentSampler`, `EgoEnvironmentService`, `EgoEnvironmentSnapshot`, `EgoSimulationFrameCalculator`, `EgoCommands`, both language files, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.30`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.14.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.30 jar has primary mod id `immersive_ego`.

Remaining:

- Environment-driven thermal strain is still gated behind disabled-by-default simulation and environment module switches.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Hydration, respiration, stamina, encumbrance, sleep, nutrition, psychology, origins, acclimation, and release metadata remain future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.

### Thermal Snapshot Foundation

Implemented:

- Added `EgoExposureSample`, `EgoSeasonPhase`, `EgoThermalMode`, `EgoThermalSnapshot`, and `EgoThermalSnapshotCalculator`.
- Added deterministic normalized heat, cold, wetness, exposure, and thermal strain calculation from climate profile, season, sky exposure, shelter, and wetness inputs.
- Kept the layer pure and server-side with no live biome scanning, environment cache, or `EgoState` mutation yet.
- Added `EgoThermalSnapshotCalculatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.29`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.environment.EgoClimateProfileTest --tests com.oblixorprime.immersiveego.environment.EgoThermalSnapshotCalculatorTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.28` to `0.1.0-alpha.29`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.29`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.29.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.28.jar`.
- Final source and installed SHA-256: `a1063f2b29e5a9f5fda1380cc8effa84ca87323078cb966b81c47099bf26c0d5`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoExposureSample`, `EgoSeasonPhase`, `EgoThermalMode`, `EgoThermalSnapshot`, `EgoThermalSnapshotCalculator`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.29`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.12.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.29 jar has primary mod id `immersive_ego`.

Remaining:

- Thermal snapshots are not wired to live biome scanning, environment caching, debug counters, or player state mutation yet.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Climate Profile Foundation

Implemented:

- Added `EgoClimateProfile` for immutable normalized climate profile pressures.
- Added `EgoClimateProfileCatalog` with temperate, hot dry, hot humid, cold dry, and cold wet defaults.
- Added deterministic helpers for dominant thermal load and wetness pressure.
- Added `EgoClimateProfileTest`.
- Bumped the final LAB test build to `0.1.0-alpha.28`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.environment.EgoClimateProfileTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.27` to `0.1.0-alpha.28`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.28`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.28.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.27.jar`.
- Final source and installed SHA-256: `4dd3be0d02a9ef70571037ed39db603b01ecc34a40b08f701873e70f68b9ac17`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoClimateProfile`, `EgoClimateProfileCatalog`, and `META-INF/neoforge.mods.toml` are present.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.28`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.12.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.28 jar has primary mod id `immersive_ego`.

Remaining:

- Climate profiles are not wired to biome scanning, seasons, shelter, wetness, thermal snapshots, or player state mutation yet.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Forbidden Registration and Resource Boundary Guard

Implemented:

- Added `EgoProjectBoundaryTest`.
- Guarded against forbidden custom block, item, entity, fluid, block entity, and menu registration snippets in `src/main/java`.
- Guarded against forbidden block/item/entity model, texture, recipe, and loot-table resource directories under the Immersive EGO namespace.
- Guarded `src/main` text sources/resources against Cold Sweat and Tough As Nails references.
- Bumped the final LAB test build to `0.1.0-alpha.27`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.EgoProjectBoundaryTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.26` to `0.1.0-alpha.27`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.27`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.27.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.26.jar`.
- Final source and installed SHA-256: `f76dd02414a960a23bb627d59307b9e54f05da1f725adebd2ae3e0ebd8c11329`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.27`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.27 jar has primary mod id `immersive_ego`.

Remaining:

- Boundary coverage is source/resource-level static coverage, not a full legal provenance audit.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Required Server Module Config Registration

Implemented:

- Added `EgoServerModuleConfigCatalog` and `EgoServerModuleConfigs`.
- Registered all required roadmap SERVER config files: environment, physiology, sleep, nutrition, psychology, origins, encumbrance, synergies, Apothic, and performance.
- Added default-off `enabled` and `debugLogging` values for each registered module config without activating unfinished gameplay.
- Added English and French translations for the new config entries.
- Bumped the final LAB test build to `0.1.0-alpha.26`.

Validation:

- Initial `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest` failed because the plain JUnit test touched NeoForge `ModConfigSpec` accessors. The test was corrected by splitting pure metadata into `EgoServerModuleConfigCatalog`.
- Final `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoServerModuleConfigsTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, loaded both dynamic config files, and logged all required SERVER config files as loaded/watched.
- Filesystem readback under `run/config` confirmed no required `immersive_ego*.toml` SERVER config files were missing.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.25` to `0.1.0-alpha.26`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.26`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.26.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.25.jar`.
- Final source and installed SHA-256: `332a4a297b57ebae66f963eeda442cecca1d792d64e27267ab558ce61c70bcd4`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoServerModuleConfigCatalog`, `EgoServerModuleConfigs`, both language files, `ImmersiveEgo`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.26 jar has primary mod id `immersive_ego`.

Remaining:

- The new roadmap SERVER config files are safe default-off gates only; future gameplay slices still need to consume them.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Dynamic Synergy Config Foundation

Implemented:

- Added `EgoDynamicSynergyConfig` and `EgoDynamicSynergyConfigLoader` for `serverconfig/immersive_ego/synergies.toml`.
- Added `synergies.edges` support for declarative `source -> target : weight` entries.
- Repaired missing, malformed, invalid, or cyclic configured synergy lists to an empty graph.
- Updated `/ego synergy trace` to use configured edges when present and fall back to the built-in sample graph when no edges are configured.
- Bumped the final LAB test build to `0.1.0-alpha.25`.

Validation:

- Initial `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoaderTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` failed because the default-file assertion depended on exact TOML formatting. The test was corrected to verify behavior by reloading the config.
- Final `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicSynergyConfigLoaderTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.25`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, and loaded both dynamic config files.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.24` to `0.1.0-alpha.25`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.25.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.24.jar`.
- Final source and installed SHA-256: `7bbf93a190cc3ecb9361864ea1259e3a590e14198c1080a7c0491840862762d1`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoDynamicSynergyConfig`, `EgoDynamicSynergyConfigLoader`, `EgoCommands`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.25 jar has primary mod id `immersive_ego`.

Remaining:

- Configured synergy edges are available to `/ego synergy trace`; full gameplay systems still need to consume configured synergies as those systems are implemented.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Trace Command

Implemented:

- Added `/ego synergy trace` as a permission-level-2 read-only command.
- Added a built-in sample graph using the current synergy evaluator until TOML-backed synergy loading exists.
- Added English and French command translations.
- Extended GameTest command registration coverage for `/ego synergy trace`.
- Bumped the final LAB test build to `0.1.0-alpha.24`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.24`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`, verified `/ego synergy trace` command registration, and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.23` to `0.1.0-alpha.24`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.24.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.23.jar`.
- Final source and installed SHA-256: `ef63db9bab9807e26af0cd30f70c14bda3f9056d19cd4a92799493fcf568a2ee`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoCommands`, `EgoSynergyEvaluator`, both language files, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.24 jar has primary mod id `immersive_ego`.

Remaining:

- TOML-backed synergy loading remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Edge Evaluation

Implemented:

- Added `EgoSynergyEvaluator` to apply validated acyclic synergy edges in topological order.
- Added `EgoSynergyEvaluationResult` and `EgoSynergyEvaluationStep` for traceable source value, weight, delta, before value, and after value records.
- Added normalized external source support for non-state sources such as capability attributes.
- Added `EgoSynergyEvaluatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.23`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEvaluatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.23`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.22` to `0.1.0-alpha.23`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.23.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.22.jar`.
- Final source and installed SHA-256: `462cdac06f952e45918cd7bc4406da9573b0c30f68cc8f728e136f9f14ebe2ed`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyEvaluator`, `EgoSynergyEvaluationResult`, `EgoSynergyEvaluationStep`, `EgoSynergyCycleDetector`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.23 jar has primary mod id `immersive_ego`.

Remaining:

- `/ego synergy trace` and TOML-backed synergy loading remain future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Cycle Detection

Implemented:

- Added `EgoSynergyCycleDetector` for deterministic cycle detection over synergy source-target graphs.
- Wired cycle rejection into `EgoSynergyValidator`.
- Added `EgoSynergyCycleDetectorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.22`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyCycleDetectorTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.22`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.21` to `0.1.0-alpha.22`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.22.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.21.jar`.
- Final source and installed SHA-256: `86f75d3c07a5645f53ecbadc20ad27a3c1735ec64613c3d484256509cc7d07d6`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyCycleDetector`, `EgoSynergyValidator`, `EgoSynergyEdge`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.22 jar has primary mod id `immersive_ego`.

Remaining:

- Synergy edge evaluation remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Synergy Edge Parser and Validator

Implemented:

- Added `EgoSynergyEdge` for source path, target path, and finite non-zero bounded weight.
- Added `EgoSynergyEdgeParser` for the first declarative `source -> target : weight` format.
- Added `EgoSynergyValidator` for catalog endpoint validation, display-only targets, duplicate source-target pair rejection, and defensive list copying.
- Added `EgoSynergyEdgeParserTest` and `EgoSynergyValidatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.21`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSynergyEdgeParserTest --tests com.oblixorprime.immersiveego.simulation.EgoSynergyValidatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.21`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.20` to `0.1.0-alpha.21`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.21.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.20.jar`.
- Final source and installed SHA-256: `b4f041b5f33b51d6e3412a7a0b1a598c73cd4ce2ae02e6cc5690432391326c50`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoSynergyEdge`, `EgoSynergyEdgeParser`, `EgoSynergyValidator`, `EgoCurve`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.21 jar has primary mod id `immersive_ego`.

Remaining:

- Cycle detection and edge evaluation remain future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Configurable Curve Foundation

Implemented:

- Added `EgoCurvePoint` for finite normalized curve control points.
- Added `EgoCurve` for immutable 0..1 response curves with full-domain validation, strictly increasing inputs, linear interpolation, and clamped runtime inputs.
- Wired identity response curves into the derived EGO Load and Readiness bridge without changing current output behavior.
- Added `EgoCurveTest`.
- Bumped the final LAB test build to `0.1.0-alpha.20`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoCurveTest --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest` passed.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.20`.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.19` to `0.1.0-alpha.20`; this is expected after a version bump in the reused `run` world.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.20.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.19.jar`.
- Final source and installed SHA-256: `62140d80b5d9755323347106c9a6d9218aa1e85afd1be03057c6b6775807c99d`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoCurve`, `EgoCurvePoint`, `EgoDerivedStateCalculator`, `EgoSimulationScheduler`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.20 jar has primary mod id `immersive_ego`.

Remaining:

- Curves are implemented as deterministic primitives and identity hooks; TOML-backed curve preset loading remains future work.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Derived State Bridge

Implemented:

- Added `EgoDerivedStateCalculator` as the first scheduler-driven derived-state bridge.
- Derived EGO Load from fatigue, thermal strain, respiratory strain, stress, and prior load persistence.
- Derived Readiness from hydration, stamina, nutrition balance, focus, comfort, recovery capacity, and inverse EGO Load.
- Updated `EgoSimulationScheduler` so enabled scheduler frames persist changed derived state and refresh mirrors.
- Added `EgoDerivedStateCalculatorTest`.
- Bumped the final LAB test build to `0.1.0-alpha.19`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.derived.EgoDerivedStateCalculatorTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.18` to `0.1.0-alpha.19`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.19`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.19.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.18.jar`.
- Final source and installed SHA-256: `fb3626510d13573b2ed841b1af17b04183af050b9b84683b9f0de427dbdd47ef`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed LAB jar inspection confirms `EgoDerivedStateCalculator`, `EgoSimulationFrame`, `EgoSimulationSchedule`, `EgoSimulationScheduler`, `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoCommands`, `EgoAttributeMirrorService`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.19 jar has primary mod id `immersive_ego`.

Remaining:

- The scheduler remains disabled by default and requires both `enableSimulation` and `scheduler.enabled` to persist derived values.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Simulation Frame and Scheduler Foundation

Implemented:

- Added immutable `EgoSimulationFrame` records with game time, interval, input state, and output state.
- Added `EgoSimulationSchedule` for deterministic interval-boundary checks and interval clamping.
- Added `EgoSimulationScheduler` on server player post-tick events.
- Kept scheduler execution double-gated behind `EgoCoreConfig.ENABLE_SIMULATION` and the dynamic `scheduler.enabled` flag.
- Current scheduler frames are no-op frames that can refresh mirrors but do not mutate gameplay state.
- Added `EgoSimulationFrameTest` and `EgoSimulationScheduleTest`.
- Bumped the final LAB test build to `0.1.0-alpha.18`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoSimulationFrameTest --tests com.oblixorprime.immersiveego.simulation.EgoSimulationScheduleTest` passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and loaded the dynamic config.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.17` to `0.1.0-alpha.18`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.18`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.18.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.17.jar`.
- Final source and installed SHA-256: `be101f52b4d57dd5cbd6af5b7dd2327002b88c9f6423db8d1306ff87fc68fab6`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.18.jar` and `jar tf` on the installed LAB jar confirm `EgoSimulationFrame`, `EgoSimulationSchedule`, `EgoSimulationScheduler`, `EgoDynamicServerConfig`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.3.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.18 jar has primary mod id `immersive_ego`.

Remaining:

- The scheduler intentionally advances no-op frames until the first real derived-state calculation is implemented.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Dynamic Server Config Foundation

Implemented:

- Added `EgoDynamicServerConfig` for the first per-world dynamic server config values.
- Added `EgoDynamicServerConfigLoader` for `serverconfig/immersive_ego/simulation.toml`.
- The loader creates the missing directory/file, writes defaults, clamps out-of-range numeric values, persists repaired values, and loads during `ServerStartingEvent`.
- Added `EgoDynamicServerConfigLoaderTest` for missing-file default creation and out-of-range value repair.
- Added explicit NightConfig `core` and `toml` `testRuntimeOnly` dependencies pinned to `3.8.3`, matching the NeoForge runtime jars.
- Bumped the final LAB test build to `0.1.0-alpha.17`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest` first failed because the plain JUnit runtime lacked NightConfig; fixed by adding explicit NightConfig test runtime dependencies.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.config.EgoDynamicServerConfigLoaderTest` then passed.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed` and logged that it loaded `serverconfig/immersive_ego/simulation.toml`.
- The GameTest dev world warned about the local world moving from `0.1.0-alpha.16` to `0.1.0-alpha.17`; this is expected after a version bump in the reused `run` world.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.17`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.17.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.16.jar`.
- Final source and installed SHA-256: `0e4af22f671f775626c46993fea82594723003f1c1a159604c597b5b66cd1ee4`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.17.jar` and `jar tf` on the installed LAB jar confirm `EgoDynamicServerConfig`, `EgoDynamicServerConfigLoader`, `EgoCommands`, `EgoAttributeMirrorService`, and `META-INF/neoforge.mods.toml` are present.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.2.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.17 jar has primary mod id `immersive_ego`.

Remaining:

- `serverconfig/immersive_ego/simulation.toml` currently stores scheduler placeholder values only; the active scheduler and simulation frames remain next.
- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.

### Runtime Mirror and Command Vertical Slice

Implemented:

- Fixed the GameTest template lookup by disabling NeoForge's class-name prefix for the local `empty` structure.
- Added `EgoAttributeMirrorService`, which mirrors server-side `EgoState` display values into player display attributes through stable transient modifier IDs under `immersive_ego:mirror/*`.
- Added duplicate-modifier protection by removing the previous mirror modifier before applying the current value.
- Exposed display-only attribute holders through `EgoAttributes.displayAttributes()`.
- Registered the mirror service on player login and respawn.
- Added `/ego status` for a player-facing state summary.
- Added `/ego debug state` for permission-level-2 state inspection.
- Added English and French command translations.
- Added runtime GameTests for player attribute attachment, mirror update behavior, duplicate-modifier prevention, and command registration.
- Added the local `data/immersive_ego/structure/empty.nbt` GameTest fixture.
- Bumped the final LAB test build to `0.1.0-alpha.16`. The intermediate `0.1.0-alpha.15` pass was superseded after the first GameTest run exposed the prefixed template path issue.

Validation:

- `.\gradlew.bat compileJava` passed after the GameTest template fix and alpha.16 version bump.
- `.\gradlew.bat runGameTestServer` passed with `All 1 required tests passed` after the template fix.
- `.\gradlew.bat compileJava` passed after adding the mirror service and duplicate-modifier GameTest.
- `.\gradlew.bat runGameTestServer` passed with `All 2 required tests passed`.
- `.\gradlew.bat compileJava` passed after adding `/ego` commands and command translations.
- `.\gradlew.bat runGameTestServer` passed with `All 3 required tests passed`.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.16`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.16.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.14.jar`.
- Final source and installed SHA-256: `be1d549867091a735bb48cb70a95c33c5a547ecc3c8d1b3b14573cd8b4b07b4e`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.16.jar` and `jar tf` on the installed LAB jar confirm `EgoCommands`, `EgoAttributeMirrorService`, `EgoGameTests`, `data/immersive_ego/structure/empty.nbt`, language files, and `META-INF/neoforge.mods.toml` are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.16`.
- A targeted metadata check confirmed the similarly named `immersive_ego_civitas-0.1.0-alpha.2.jar` has primary mod id `immersive_ego_civitas`, while the installed alpha.16 jar has primary mod id `immersive_ego`.

Remaining:

- GameTest logs still show a MariesLib `ItemScanner` async warning (`ArrayIndexOutOfBoundsException`) in the dev runtime; the GameTest task exits successfully and this has not been pinned to Immersive EGO code.
- No live client Apothic Attributes GUI screenshot/smoke test was run in this pass.
- Dynamic TOML loading, active simulation frames, and scheduler remain next.

### State Attachment Foundation

Implemented:

- Added pure `EgoState` with normalized display mirror values, schema version `1`, clamping, display-only catalog access, and schema read guards.
- Added `EgoStateCodecs` with defaulted codec fields, legacy schema `0` migration into the current state shape, and rejection for negative or future schemas.
- Registered `immersive_ego:ego_state` through `EgoAttachments` as a serialized NeoForge attachment copied on player death.
- Added `EgoStateTest` for defaults, clamping, schema read guards, and display-only catalog access.
- Hardened `scripts/install-mod.ps1` filename matching so `immersive_ego_civitas-*.jar` no longer matches as an `immersive_ego` jar by filename.
- Bumped the final LAB test build to `0.1.0-alpha.14`. An intermediate `0.1.0-alpha.13` pass was superseded after the installer matcher was tightened.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.data.EgoStateTest` first failed because plain JUnit tests cannot see DFU/Gson codec classes; fixed by moving the codec to `EgoStateCodecs` and keeping `EgoState` pure.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.data.EgoStateTest` passed.
- Installer matcher check confirmed `immersive_ego-0.1.0-alpha.13.jar`, `immersive_ego.jar`, and `immersive-ego-0.1.0.jar` match, while `immersive_ego_civitas-0.1.0-alpha.1.jar` and `other.jar` do not.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.14`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.14.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.13.jar`.
- Final installed SHA-256: `cb7abe3310fd105ae7dd7771efebd27904b3298eda04ab6da4da45cdc30b3232`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.14.jar` and `jar tf` on the installed LAB jar confirm `EgoState`, `EgoStateCodecs`, `EgoAttachments`, `ImmersiveEgo`, and `META-INF/neoforge.mods.toml` are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.14`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- No live client/server smoke test or GameTest was run for persisted attachment behavior.
- Mirror update service and `/ego` commands remain next.
- During the intermediate `0.1.0-alpha.13` installer pass, the previous broad filename matcher also removed `immersive_ego_civitas-0.1.0-alpha.1.jar` by filename only. The final `0.1.0-alpha.14` pass used the narrowed matcher and removed only the intended prior `immersive_ego` jar.

### Attribute Foundation

Implemented:

- Inspected Apothic Attributes `1.21` source at commit `666900510edd2c5289d5e29312182e934b70a381` and confirmed the native attributes GUI displays registered player attributes with translation descriptions.
- Inspected Apotheosis `1.21` source at commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370` and found current affix/gem attribute use is explicit data/codecs rather than an automatic all-attributes scan.
- Added `EgoAttributeCatalog` with 8 capability multiplier attributes and 12 normalized display-only mirror attributes.
- Registered the attributes as syncable NeoForge `PercentageAttribute` values through `EgoAttributes`.
- Attached all EGO attributes to players with `EntityAttributeModificationEvent`.
- Added local `immersive_ego:capability` and `immersive_ego:display_only` attribute tags for future compat/datagen guards.
- Added English and French names/descriptions for Apothic GUI display.
- Added pure Java unit coverage for unique attribute paths, ranges, roles, and positive/negative display sentiment.
- Bumped the final LAB test build to `0.1.0-alpha.12` after the installer revealed Prism LAB already had `0.1.0-alpha.11`; the temporary `0.1.0-alpha.8` pass was not kept as final.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.registry.EgoAttributesTest` first failed because the test imported Minecraft `Attribute` on the plain JUnit classpath; fixed by separating the pure catalog/sentiment model from the Minecraft-backed registry.
- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.registry.EgoAttributesTest` passed after the catalog split.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.12`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.12.jar` into Prism LAB.
- Final install deleted old installed jar: `immersive_ego-0.1.0-alpha.8.jar`.
- Final installed SHA-256: `e41f9d1bdc7c07bbb4b935b8b228f0267ea09f923cda32cb94637e0084ba91b0`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.12.jar` and `jar tf` on the installed LAB jar confirm mod metadata, registry classes, language files, logo, and both attribute tag files are present.
- Installed jar metadata confirms mod id `immersive_ego`, display name `Immersive EGO`, and version `0.1.0-alpha.12`.
- `git diff --check` exited successfully; Git emitted CRLF conversion warnings only.

Remaining:

- The Apothic GUI path is source-verified and the attributes are attached to players, but no live client GUI screenshot/smoke test was run in this pass.
- Mirror update service, server-authoritative `EgoState`, and runtime/GameTest attribute supplier proof remain next.

## 2026-06-19

### Bug Hunt 6

Implemented:

- Fixed `EgoMath.weightedMean` overflow behavior for very large finite weights by scaling all weights against the maximum input weight before accumulation.
- Added regression coverage for large finite weights that previously could overflow both weighted sum and total weight.
- Bumped the test build from `0.1.0-alpha.6` to `0.1.0-alpha.7`.

Validation:

- `.\gradlew.bat test --tests com.oblixorprime.immersiveego.simulation.EgoMathTest` passed, including the new large finite weight regression.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.7`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.7.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.6.jar`.
- Final installed SHA-256: `9e75a6b31b671ec9063d7cde54cc486dff46437bf9b62077411e248882cd43eb`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.7.jar` confirms mod metadata, compiled classes, and the project logo are present.
- `git diff --check` passed.

### Bug Hunt 5

Implemented:

- Fixed installer path safety: `scripts/install-mod.ps1` and `scripts/install-runtime-deps.ps1` no longer create an entire missing target tree from a mistyped `-ModsDir`.
- Added a shared guard pattern that accepts an existing mods directory, or creates only a missing final `mods` folder when its parent already exists.
- Bumped the test build from `0.1.0-alpha.5` to `0.1.0-alpha.6`.

Validation:

- `.\scripts\install-runtime-deps.ps1 -ModsDir <missing-parent-test-path>` refused to create a full missing target tree before download/install work.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.6`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.6.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.5.jar`.
- Final installed SHA-256: `f0e86aadafc965da20fc22effeadda9caae06ae1dc40756bd85f4fd131eb3959`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- `jar tf build\libs\immersive_ego-0.1.0-alpha.6.jar` confirms mod metadata, compiled classes, and the project logo are present.
- `git diff --check` passed.
- GitHub Actions `Build` passed on run `27846334653`.

### Bug Hunt 2

Implemented:

- Fixed GitHub Actions wrapper execution risk by tracking `gradlew` with executable mode `100755`.
- Added `.gitattributes` rules for wrapper/script line endings.
- Added explicit workflow `chmod +x ./gradlew` guard before the Linux build step.
- Hardened `EgoMath.weightedMean` against NaN and infinite weights.
- Added tests for non-finite weight rejection.
- Bumped the test build from `0.1.0-alpha.2` to `0.1.0-alpha.3`.

Validation:

- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.3`.
- `.\scripts\install-mod.ps1` installed `immersive_ego-0.1.0-alpha.3.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.2.jar`.
- Final installed SHA-256: `b790c3f21838f6afa4233d97c11db2819577bf3fdeb8cf5cb31ebc58cee895fc`.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.

### Bug Hunt 3

Implemented:

- Fixed the reproducibility gap where required LAB runtime dependencies were installed manually outside the repo workflow.
- Added `scripts/install-runtime-deps.ps1` with pinned URLs and SHA-256 checks for Apothic Attributes, Placebo, MariesLib, Curios, and Cloth Config API.
- Updated `scripts/install-mod.ps1` to run the runtime dependency installer by default, with `-SkipRuntimeDependencies` for deliberate jar-only installs.
- Bumped the test build from `0.1.0-alpha.3` to `0.1.0-alpha.4`.

Validation:

- `.\scripts\install-runtime-deps.ps1` passed and wrote `build/runtime-deps-report.json` with hash matches for all dependencies.
- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.4`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.4.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.3.jar`.
- Final installed SHA-256: `ab913e19eefbf065dba1e175c5e094c0844fb2b4c60ce13b58882bdd9275bdcc`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.

### Bug Hunt 4

Implemented:

- Fixed the GitHub Actions Node 20 deprecation warning path by updating action pins to current Node 24 based majors.
- Bumped the test build from `0.1.0-alpha.4` to `0.1.0-alpha.5`.

Validation:

- `.\gradlew.bat clean build` passed for version `0.1.0-alpha.5`.
- `.\scripts\install-mod.ps1` installed pinned runtime dependencies and `immersive_ego-0.1.0-alpha.5.jar` into Prism LAB.
- Deleted old installed jar: `immersive_ego-0.1.0-alpha.4.jar`.
- Final installed SHA-256: `f8ca4ff12b6bc1ae7cffab21503a9eaeff55e60ffa31516d3c14925f6aa2b399`.
- `build/runtime-deps-report.json` confirms all pinned dependency hashes match and exactly one jar remains for each dependency.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- GitHub Actions `Build` passed on run `27845439246`.

### Bootstrap

Implemented:

- Cloned empty GitHub repository into `<USER_HOME>\AI Depot\Codex Documents\Immersive EGO`.
- Bootstrapped from NeoForge MDK 1.21.1 ModDevGradle.
- Replaced MDK example mod with `immersive_ego` package and metadata.
- Added required dependency repositories and pinned versions.
- Added project logo resource.
- Added MIT license, third-party notices, audit docs, roadmap, and handoff docs.
- Added bootstrap server/client config specs.
- Added pure Java `EgoMath` and `EgoLoadCalculator`.
- Added JUnit 5 tests.
- Bumped the installed test build from `0.1.0-alpha.1` to `0.1.0-alpha.2` after the first install pass.

Validation:

- `.\gradlew.bat clean build` failed once because Curios' repository was missing.
- Added `https://maven.theillusivec4.top/`.
- `.\gradlew.bat clean build` failed once due exact floating-point assertion.
- Added tolerance to the assertion.
- `.\gradlew.bat clean build` passed on 2026-06-19.
- Final `.\gradlew.bat clean build` passed for version `0.1.0-alpha.2`.
- `.\scripts\install-mod.ps1` installed `immersive_ego-0.1.0-alpha.2.jar` into Prism LAB.
- Final installed SHA-256: `1d85636bd8bc0bfc04e85d04c8eb7ce96c9b6e0bfb050f317e716bedb8f9c654`.
- `build/install-report.json` confirms source and installed hashes match and exactly one installed jar remains for `immersive_ego`.
- Installed required LAB dependencies after metadata inspection: Apothic Attributes `2.9.1`, Placebo `9.9.1`, MariesLib `0.1.0-beta.5`, Curios `9.5.1+1.21.1`, and Cloth Config API `15.0.140+neoforge`.

Next exact task:

- Inspect Apothic Attributes 2.9.1 source and implement the first capability/display attribute registration set without copied assets.

## 2026-10-13 preservation snapshot

The local alpha.61 worktree was preserved on a separate draft-PR branch. See [snapshot provenance and validation](LOCAL_ALPHA61_SNAPSHOT.md) for included files, privacy adaptations and the fresh build limitation. This entry records preservation only; it does not close runtime proof gates.
