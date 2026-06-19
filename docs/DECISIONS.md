# Decisions

## 2026-06-19 - Bootstrap from current NeoForge MDK

Use the official `MDK-1.21.1-ModDevGradle` template at commit `ff9c5e847cb470ee2fb63d4309f7a25a61864ba5` instead of hand-writing the Gradle userdev setup.

Reason: the master prompt requires current official NeoForge 1.21.1 conventions and a working wrapper.

## 2026-06-19 - Pin NeoForge 21.1.233 with metadata range [21.1.228,)

Use `neo_version=21.1.233`, the current MDK value, while requiring `[21.1.228,)` in metadata because MariesLib currently declares NeoForge 21.1.228 or newer.

Reason: this is the newest mutually compatible baseline found during the audit.

## 2026-06-19 - Keep gameplay simulation disabled in bootstrap config

Register a server config master switch defaulted to `false`.

Reason: the vertical slice is not implemented yet, and the prompt forbids fake placeholders that silently affect gameplay.

## 2026-06-19 - No direct Apothic/MariesLib API use in Phase 0

Add and resolve dependencies, but do not call their APIs until source inspection is performed for the specific integration surface.

Reason: the master prompt requires not inventing external APIs.
