# Thermal tolerance option B — qualification 2026-10-17

## Result and exact candidate

**PASS for the bounded option B correction.** The previously failed effective `thermal_tolerance` scenario now passes in a real production client using the packaged JAR. The overall Desertborn qualification still has the explicitly listed limits below; this is not a release or a claim of full balance/multiplayer qualification.

- Source candidate executed in the client: `0b8b49c0d0a43424a73997e294d8fc3a2a9f4dc0`, tree `2b7627001e2ac653e9aec857f2affb1da330648f`.
- Base: merged main/PR2 `3f7734cc4bbd6802c12c09b5990544b86faac47a`; branch `fix/thermal-tolerance-snapshot`.
- JAR: `immersive_ego-0.1.0-alpha.62.jar`, **511707 bytes**.
- SHA256: **`bbe4be56ea2e9169cd35dbad10ea91d82baccb7111e77d80da4a04054cf6a163`**.
- Same version as the original qualification, different binary; the original JAR SHA256 remains `064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6` and is unchanged.

The subsequent documentation/evidence commit changes no runtime inputs. Its exact head, clean-build binary comparison and CI result are recorded in the draft PR, to avoid confusing the tested source candidate with a later documentation commit. No binary or release asset is published by this work.

See [approved B contract](THERMAL_TOLERANCE.md) and [reviewable proof directory](validation/thermal-tolerance-20261017/README.md). The owner's explicit B selection at 10:44 UTC supersedes the undecided A/B/C status in the preserved historical reports.

## Fresh automated validation and independent review

| Execution | Exact result | Evidence |
|---|---|---|
| Targeted JUnit | 14 passed, exit 0 | New golden heat/cold T=0.25/0.5/1/2/4 and consumer tests; full results also included in the clean build |
| Candidate clean build | 189 JUnit, 0 failures/errors/skips; exit 0; 8 tasks executed | [build log](validation/thermal-tolerance-20261017/clean-build.log), [identity/count receipt](validation/thermal-tolerance-20261017/candidate-identity.json) |
| Final server GameTests | 37 required passed, exit 0, including the actual AttributeModifier case | [full log](validation/thermal-tolerance-20261017/gametest.log) |
| Independent review | No corrective finding; modifier-coverage suggestion implemented and independently rechecked | [review receipt](validation/thermal-tolerance-20261017/independent-review.md) |

Linux equivalent of the AGENTS.md clean-build requirement: `python /workspace/cloud-setup/gradle-cloud.py --offline --no-build-cache clean build`. The wrapper invokes the repository Gradle wrapper with Java 21 and existing cloud settings. Minecraft/NeoForm preparation caches were reused; EGO compilation, resources, JAR and JUnit tasks executed after clean.

GameTest command: `python /workspace/cloud-setup/gradle-cloud.py --offline --no-configuration-cache -I /workspace/ego-thermal-b-20261017/gametest.init.gradle runGameTestServer`. The external init file selects a new test game directory and four processors, without changing repository configuration.

The initial targeted run had six incorrect test expectations for climatic wetness: the existing profile uses a humidity/precipitation weighted mean. Corrected only those expectations, preserving the failed run evidence. The initial 37-test run preceded the added modifier assertion; the final 37-test run includes it. Counts are not added together as distinct tests.

## Packaged client proof

Minecraft 1.21.1; NeoForge 21.1.233; FML 4.0.42; Temurin 21.0.8+9; Apothic Attributes 2.9.1; Placebo 9.9.1; separate MariesLib 0.1.0-beta.5. Optional Apotheosis remains absent. Mesa llvmpipe runs on a private cloud display. Offline EGOCloud is a synthetic local fixture; no real account or user-PC installation is used.

The production `forgeclient` launch loads the single verified JAR from `mods`; it is not a development `runClient`. [Class-loading proof](validation/thermal-tolerance-20261017/packaged-class-loading.log) identifies the packaged thermal classes. All official assets verified in the previous follow-up are reused read-only. The sound engine again starts on the process-local OpenAL Wave File Writer; physical-speaker listening remains outside this cloud proof.

The copied world inherited an immersion fixture. Preparation encountered two drownings before acceptance, and a fill command rejected unloaded chunks. These events remain in the logs and are not acceptance passes. Preparation then force-loaded four test chunks, constructed a solid open platform at `(64.5,200,64.5)`, verified position/sky/exposure and switched to Survival. No EGO attachment was directly edited.

Accepted cases use the persisted arid origin, open sky, fixed spring daytime/clear weather, dry W=0 and normal server ticks. Vanilla `fillbiome` changes only the copied fixture between desert (`hot_dry`) and snowy plains (`cold_wet`). Core, scheduler, environment, physiology and origins are enabled; other modules stay disabled for this controlled comparison. Other capabilities are neutral. Respiratory pressure from the fixed altitude and initial fatigue are explicitly accounted for, not mistaken for thermal demand.

There are **10 bounded scenarios, 20 exact T0/T1 samples**, each pair separated by 100 ticks/five scheduler frames. All **170 numerical assertions** pass to 1e-12; these are assertions on client observations, not additional JUnit/GameTest counts. [Samples](validation/thermal-tolerance-20261017/client-samples.json), [calculations](validation/thermal-tolerance-20261017/client-numeric-checks.json), [reproducer](validation/thermal-tolerance-20261017/verify-client.py), [timestamped log excerpts](validation/thermal-tolerance-20261017/client-log-excerpts.txt).

| T | Hot adjusted strain before origin | Cold adjusted strain before origin | Hot hydration loss / 100 ticks | Cold hydration loss / 100 ticks |
|---:|---:|---:|---:|---:|
| 0.25 | 1.0000 | 1.0000 | 0.005562093029 | 0.005562093029 |
| 0.5 | 1.0000 | 1.0000 | 0.005562093029 | 0.005562093029 |
| 1 | 0.8700 | 1.0000 | 0.005074593029 | 0.005562093029 |
| 2 | 0.4350 | 0.5000 | 0.003443343029 | 0.003687093029 |
| 4 | 0.2175 | 0.2500 | 0.002627718029 | 0.002749593029 |

Raw hot heat/cold are `0.870/0.195`; raw cold heat/cold are `0.074/1.000`. All values in this table are normalized units, not percentages. Adjusted strain is also reconstructed from the exact persisted state plus the existing origin relief; direct diagnostics confirm it at their displayed three-decimal precision. No raw pressure changes with T.

At hot T=1, heat acclimation moves `0.642771402416 → 0.653973033742`; at T=2 it moves `0.652479357337 → 0.645275573808`, matching the lower exposure target. Both thermal axes and the unchanged altitude/humidity axes follow their existing five-step recurrence. The initial states differ because this is a continuous play sequence, so comparisons use each case's own T0 and exact formula rather than claiming identical resets.

W remains exactly zero throughout all 20 acceptance samples. The full 37-test suite separately preserves PR2 acquisition/drying, death, mode/disable pauses, dimensions/persistence, no catch-up, equipment neutrality and the single max branch. Previously completed long-form client lifecycle tests are reused as historical evidence rather than relabelled as new runs.

Screenshots: [hot T=2](validation/thermal-tolerance-20261017/07-hot-t2-adjusted.png), [cold T=2](validation/thermal-tolerance-20261017/08-current-scaled-thermal.png), [cold T=0.5 saturation](validation/thermal-tolerance-20261017/09-cold-scaled-thermal.png), [actual Apothic 200% row and new description](validation/thermal-tolerance-20261017/14-apothic-thermal-description.png).

No EGO error or crash occurred. Existing DNS failures for external Minecraft/Placebo services, two vanilla goat sound-event warnings and the MariesLib missing optional compatibility resource warning remain visible. There are no missing external asset file exceptions in this run.

## Historical results and remaining limits

| Stage | Attribution and current interpretation |
|---|---|
| Original qualification on main | **28 PASS, 1 FAIL, 9 NOT_RUN**, 175 fresh JUnit at that time, original JAR hash above. This matrix is preserved unchanged in [original report](DESERTBORN_PACKAGED_QUALIFICATION.md). Its thermal failure is not erased. |
| Same-JAR follow-up | 34 newly executed GameTests at that time; separate environment/scheduler pauses, rain/snow/boat/drowning/End and daytime nap observed. All 3888 official asset objects/3911 names verified, digital audio recorded. See [follow-up](THERMAL_TOLERANCE_FOLLOWUP.md). These results are not recounted as new option-B tests. |
| This option-B correction | 189 fresh JUnit; 37 fresh GameTests; ten actual packaged-client scenarios PASS. The old thermal failure is resolved for the new binary. |

Remaining **BLOCKED**: optional Apotheosis presence, pinned 8.5.4 not cached and official Maven metadata returned HTTP 403 in the follow-up; access was not bypassed or retried here.

Remaining **NOT_RUN**: actual multiplayer session/load, prolonged balance, a full 72000-tick elapsed client sleep/rest scenario and physical-speaker listening. The GameTest model checks do not replace those live-duration/session proofs. Stress-resilience evidence in the original qualification remains qualitative rather than a newly isolated comparison.

Remaining **not implemented / outside scope**: active gameplay synergy integration and planned affixes; shelters without active recovery, then equipment remain later work. No new such mechanics are introduced.

## Cleanup and preservation

The client exited normally with code 0, T was restored to 1, temporary gamerule/forceload changes were restored, the 13 configuration TOMLs were restored byte-for-byte, and the private display stopped with its temporary cookie removed. The copied qualification world/datapack and new proof files remain for inspection.

All **4821 previously inventoried files** across both earlier proof directories and the original archive are unchanged by SHA256/size comparison. The original JAR and previous archives remain intact. No credentials/security settings, user PC, Notion, GOV or other project repository were changed. GitHub documentation and the targeted draft PR are the authorized external destination; no merge/release is authorized.
