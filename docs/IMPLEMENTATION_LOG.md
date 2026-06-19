# Implementation Log

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

- Cloned empty GitHub repository into `C:\Users\Emmanuel Tremblay\AI Depot\Codex Documents\Immersive EGO`.
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
