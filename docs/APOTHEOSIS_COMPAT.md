# Apotheosis Compatibility

Status: planned optional compatibility.

Apotheosis is optional in mod metadata. The core mod must load when Apotheosis is absent.

## Rules

- No direct classloading from common startup.
- Keep compatibility in `compat.apotheosis`.
- Inspect current 1.21.1 affix and gem data formats before shipping examples.
- Display-only mirror attributes must never receive default shipped affixes.
- Do not copy Apotheosis assets.

## Next Work

- Inspect Apotheosis branch `1.21`, commit `a4f6a6cdcd8e42013a2c0115caca2c7c2a524370`.
- Add absence/presence tests once compatibility code exists.
