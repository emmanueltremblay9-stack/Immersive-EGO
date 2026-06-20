# Apothic Attributes Integration

Status: first attribute registration and display mirror foundation implemented.

Apothic Attributes is required and pinned to `2.9.1` for the initial baseline. The dependency resolves in Gradle and is declared in mod metadata.

## Rules

- Do not copy Apothic textures, icons, GUI assets, language files, or logos.
- First make EGO attributes visible through normal attribute registration.
- Display mirrors must never drive gameplay.
- Use stable modifier IDs to avoid duplicate modifiers.
- Do not add client mixins unless the native Apothic attribute GUI path is proven insufficient.

## Source Inspection

- Inspected Apothic Attributes branch `1.21`, commit `666900510edd2c5289d5e29312182e934b70a381`.
- Apothic registers attributes as syncable holders and attaches them with `EntityAttributeModificationEvent`.
- The Apothic GUI lists registered player attributes from `BuiltInRegistries.ATTRIBUTE`, excludes only configured hidden attributes, and shows `.desc` translation keys when present.
- `apothic_attributes:dynamic_base` exists for attributes whose bases cannot be computed without context; Immersive EGO display mirrors are normal bounded player attributes, not dynamic-base attributes.
- Inspected Apotheosis branch `1.21`, commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370` for affix/gem attribute conventions. Attribute affixes and gem bonuses reference explicit data/codecs; no upstream attribute-exclusion tag was found in this baseline.

## Implemented Foundation

- `EgoAttributeCatalog` defines 8 capability multiplier attributes and 12 normalized display-only mirror attributes.
- `EgoAttributes` registers them as syncable `PercentageAttribute` values and attaches them to players.
- `data/immersive_ego/tags/attribute/capability.json` and `display_only.json` mark local attribute intent for future compat and datagen guards.
- Display-only attributes are metadata and UI mirrors; gameplay state must remain server-authoritative.
- `EgoAttributeMirrorService` applies server-side display mirror values through stable transient modifier IDs under `immersive_ego:mirror/*`.
- GameTests prove all EGO attributes are present on player suppliers and repeated mirror updates do not duplicate modifiers.

## Next Work

- Verify the attributes in the Apothic GUI during a client smoke pass.
- Inspect modifier source integration before adding richer Apothic tooltip/source metadata.
