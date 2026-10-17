# Desertborn packaged-client qualification — 2026-10-17

This is the **initial qualification record**. The later authorized [follow-up](THERMAL_TOLERANCE_FOLLOWUP.md) preserves its evidence, supplies fresh GameTests and additional client scenarios, resolves the missing assets/digital-audio limitation and details the remaining thermal decision. The initial counts and NOT_RUN entries below describe that earlier run, not the final follow-up status.

The production JAR was actually executed in an isolated cloud Minecraft client. The bounded Desertborn and PR2 scenarios passed. **The complete gate remains open:** `thermal_tolerance` has no gameplay consumer, and the cloud client lacks external assets/audio. No gameplay source changed and no PR2 regression was demonstrated.

## Exact candidate

- DEV commit: `3f7734cc4bbd6802c12c09b5990544b86faac47a`; tree `7119c861b3677043f05e9b2325c4089913c83d94`.
- Artifact: `immersive_ego-0.1.0-alpha.62.jar`, 500,323 bytes.
- SHA-256: `064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6`.
- GOV: `emmanueltremblay9-stack/Immersve-EGO-GPT`, commit `8004452ee3eb831a8840a99fa0ad0b487e0986d7`.
- Existing merged-main CI [38920721166](https://github.com/emmanueltremblay9-stack/Immersive-EGO/actions/runs/38920721166): exact SHA, attempt 1, completed/success, metadata freshly read. No new CI run claimed.

The initial clean checkout at `50d9e4b9baca046f00277f61ba17728be9215d2b` was fast-forwarded to verified live main. PR2 was already merged and was not recreated. Historical alpha.61 evidence was preserved before the build.

## Fresh verification

Linux equivalent of the AGENTS clean-build requirement:

```text
python /workspace/cloud-setup/gradle-cloud.py --offline --no-build-cache clean build
```

Exit 0; BUILD SUCCESSFUL; eight tasks executed; **175 newly executed JUnit, zero failures/errors/skips**. Minecraft/NeoForm preparation caches were reused, while EGO classes/JAR/tests were regenerated after clean. **No new GameTests:** the earlier 34 remain historical.

Runtime: Minecraft 1.21.1, NeoForge 21.1.233/FML 4.0.42, Temurin 21.0.8+9; separate Apothic Attributes 2.9.1, Placebo 9.9.1 and MariesLib 0.1.0-beta.5. Optional Apotheosis absent. The official production `forgeclient` target loaded EGO classes from the single matching JAR in `mods`; no Gradle `runClient` or EGO development class directory was used. Official installer processors were run locally using verified cached mappings after its offline mapping-download step failed DNS. Three real client processes exited 0.

## Results

| Scope | Result |
|---|---|
| Desertborn | PASS: real desert/hot_dry/arid assignment; T0/T1 hydration, heavy sprint, stamina/fatigue/readiness; water potion; six foods/MariesLib groups; actual bed sleep; bounded psychology/recovery observation; Apothic GUI; existing shelter response and synergy diagnostic |
| PR2 | PASS: separate W in [0,1], immersion/drying, Survival/Adventure, Creative/Spectator pause, core-off pause and normal-step resume, death W=0, physical Nether round-trip, full-process restart, max thermal projection, neutral leather equipment |
| Attribute effects | PASS: hydration efficiency, stamina capacity, respiration efficiency, load tolerance, sleep recovery, acclimation gain; stress resilience observed qualitatively with concurrent nutrition changes |
| Thermal tolerance | **FAIL of expected effect, preexisting**: base 1→2 leaves raw thermal strain .870; only registration/catalog references exist in both candidate and pre-PR2 commit |
| NOT_RUN | New GameTests; isolated environment/scheduler-off switches; rain/snow/boat/extended submersion; naps/phantom threshold; all dimensions; multiplayer/load/balance; optional Apotheosis present/affixes; active synergy integration; complete graphics/audio |

Selected exact evidence: sprint capacity 1→2 changes cost .24→.12; sleep recovery 1→2 changes fatigue recovery .03990130705439998→.07980261410879996 over three frames. W stays exactly .9578190577818768 over process restart with core paused, and both other attachments are unchanged. Readiness matches its formula in all 76 samples.

There are 28 PASS, one FAIL and nine NOT_RUN rows in the delivered matrix. These are qualification rows, not automated test counts. The 27 numerical observation checks include confirmation that raw thermal strain remains constant; those two checks are not capability-effect PASS results. A new thermal-tolerance formula would invent gameplay without an identified contract, so no arbitrary fix or draft PR was created.

## Evidence and limits

Full [French report](/workspace/ego-qualification-20261017/REPORT.md), [scenario matrix](/workspace/ego-qualification-20261017/SCENARIO_RESULTS.md), [samples](/workspace/ego-qualification-20261017/samples.json), [numeric checks](/workspace/ego-qualification-20261017/observation-checks.json), [runtime identity](/workspace/ego-qualification-20261017/final-artifact-check.json) and [cleanup receipt](/workspace/ego-qualification-20261017/cleanup.json). Evidence root: `/workspace/ego-qualification-20261017`; the delivery archive includes the EGO JAR, logs, real screenshots, measurement scripts/configs and isolated save, excluding third-party runtime libraries and historical tests.

The measurements read server NBT without writing canonical EGO/W state. Game-time/sample boundaries are accounted for: a client-command start and scheduled end can enclose 99 player steps over 100 clock ticks. The world is new single-biome desert, seed 20261017; no user save was used.

External asset objects are absent after a previously observed asset-host 403, respected without bypass. Terrain and required interfaces worked, but panorama/icons/fonts and audio are incomplete. Network public-key/Placebo Patreon errors and audio-device errors remain in the logs; no EGO ERROR header or process crash was observed. Terminal GOV API Forbidden was respected; the existing authorized GitHub connector provided normal repository reads. No credentials or network policy changed.

All three clients and the private display are stopped. Thirteen TOML files were restored byte-for-byte, including disabled core/scheduler; test attribute bases and temporary gamerules were restored. The world/diagnostic datapack remain as evidence; the temporary roof was removed. No merge, release, publication upload, user-PC install or payment. Documentation edits are subsequent local evidence notes and are not part of the JAR commit.

Next: resolve the existing thermal-tolerance contract and complete remaining proof where authorized assets are available. Preserve the approved order: qualification, then shelters without active recovery, then equipment. No later-phase mechanics were implemented here.
