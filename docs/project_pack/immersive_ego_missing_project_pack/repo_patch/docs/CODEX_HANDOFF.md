# Codex Handoff

## Current status

A documentation/config/prompt pack exists, but the target repository still needs repository inspection and actual implementation.

## Next exact task

Run `codex_prompts/01_phase0_bootstrap_and_audit.txt` in the target repository.

## Blockers to resolve first

- Confirm whether the target repository already has a NeoForge project.
- Resolve exact Gradle coordinates and artifact repositories.
- Verify MariesLib published coordinates and stable API surface.
- Verify Apothic Attributes API and attribute GUI behavior for the pinned version.
- Verify optional Apotheosis schema/API before adding examples.

## Required evidence in next Codex report

- `java -version`
- `./gradlew --version` or `gradlew.bat --version`
- exact dependency versions resolved
- exact build/test commands executed
- pass/fail output summary
- files changed
- next unchecked task
