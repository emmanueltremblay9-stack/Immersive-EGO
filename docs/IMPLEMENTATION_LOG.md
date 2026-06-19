# Implementation Log

## 2026-06-19

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
