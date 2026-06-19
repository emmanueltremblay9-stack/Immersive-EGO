# Changelog

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
