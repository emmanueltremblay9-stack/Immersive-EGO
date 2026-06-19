# Changelog

## 0.1.0-alpha.6

- Hardened `scripts/install-mod.ps1` and `scripts/install-runtime-deps.ps1` so mistyped Prism paths cannot silently create a full fake target tree.
- Kept the normal LAB install path supported while allowing only a missing final `mods` folder under an existing parent to be created.

## 0.1.0-alpha.5

- Updated GitHub Actions to Node 24 based action versions: `actions/checkout@v7`, `actions/setup-java@v5`, and `gradle/actions/setup-gradle@v6`.
- Removed the CI Node 20 deprecation warning path while keeping the same Java 21 Gradle build.

## 0.1.0-alpha.4

- Added `scripts/install-runtime-deps.ps1` to reproduce Prism LAB runtime dependency installation with pinned URLs and SHA-256 checks.
- Updated `scripts/install-mod.ps1` to install and verify runtime dependencies by default before installing Immersive EGO.
- Added `build/runtime-deps-report.json` generation for dependency hash/count proof.

## 0.1.0-alpha.3

- Fixed GitHub Actions bootstrap risk by tracking `gradlew` as executable, preserving LF line endings for shell scripts, and adding a workflow `chmod` guard.
- Fixed `EgoMath.weightedMean` so NaN and infinite weights fail fast instead of collapsing calculations to `0.0`.
- Added unit coverage for non-finite weight rejection.

## 0.1.0-alpha.2

- Initialized NeoForge 1.21.1 / Java 21 project from the official NeoForge MDK.
- Added MIT license, dependency audit, provenance, architecture, and handoff docs.
- Added required mod metadata for Apothic Attributes and MariesLib, plus optional Apotheosis metadata.
- Added project logo resource.
- Added bootstrap server/client config specs.
- Added pure Java normalized math and EGO Load aggregation tests.
- Added Windows Prism LAB install script and install report generation.
- Bumped from `0.1.0-alpha.1` after the first install pass to keep Prism test installs version-unique.
