# Implementation Log

## 2026-06-19

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
