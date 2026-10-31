# Decisions

## 2026-06-21 — Initial project constraints

Decision: Immersive EGO targets Minecraft 1.21.1, NeoForge 21.1.x, Java 21, mod id `immersive_ego`, package `com.oblixorprime.immersiveego`, license MIT.

Reason: Matches the project specification and keeps scope fixed.

## 2026-06-21 — No custom blocks/items/entities/fluids

Decision: The initial mod uses attributes, attachments, data components, commands, payloads, configs, codecs, and optional integration only.

Reason: The product is a survival-RPG simulation layer, not a content block/item mod.

## 2026-06-21 — Apothic Attributes as primary stat display

Decision: Important stats are exposed through Apothic Attributes. EGO mirror attributes are display-only.

Reason: Keeps HUD clutter low and uses the existing character-stat UI.

## 2026-06-21 — MariesLib as separate dependency

Decision: MariesLib is required and must not be JarJar'd.

Reason: Its observed license is LGPL-3.0-only; keeping it separate avoids contaminating the MIT repository and respects the dependency model.

## 2026-06-21 — Cold Sweat excluded

Decision: No Cold Sweat dependency, code, assets, or integration.

Reason: Explicit project rule.
