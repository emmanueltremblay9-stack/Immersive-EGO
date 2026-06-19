# Apothic Attributes Integration

Status: planned.

Apothic Attributes is required and pinned to `2.9.1` for the initial baseline. The dependency resolves in Gradle and is declared in mod metadata.

## Rules

- Do not copy Apothic textures, icons, GUI assets, language files, or logos.
- First make EGO attributes visible through normal attribute registration.
- Display mirrors must never drive gameplay.
- Use stable modifier IDs to avoid duplicate modifiers.
- Do not add client mixins until the pinned Apothic source has been inspected and the native path is insufficient.

## Next Work

- Inspect Apothic Attributes branch `1.21`, commit `666900510edd2c5289d5e29312182e934b70a381`.
- Implement capability attributes.
- Implement display-only mirror attributes.
- Document any public API surface used.
