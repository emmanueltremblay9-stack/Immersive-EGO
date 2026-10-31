# License Matrix

| Component | Role | License observed/to verify | Allowed in MIT repo? | Handling |
|---|---|---:|---:|---|
| Immersive EGO original code/docs/assets | Project source | MIT | Yes | Own source. |
| Apothic Attributes code | Required dependency | MIT observed in official repo | Dependency/API use OK | Do not copy assets. Verify exact version. |
| Apothic Attributes assets | External assets | All Rights Reserved observed in spec | No copying | Keep dependency external. |
| Placebo | Transitive dependency | Verify exact version/license | Dependency use only | Avoid direct API unless required. |
| MariesLib | Required dependency | LGPL-3.0-only observed | Dynamic dependency OK | Keep separate installed mod. Do not JarJar. |
| Apotheosis | Optional integration | Verify exact version/license | Optional dependency/API use only | No hard classloading. |
| Cold Sweat | Explicitly forbidden | N/A | No | No dependency, no code, no integration. |
| Tough As Nails code/assets | Explicitly excluded | Varies | No | No copied implementation. |
| Nourished/Sanity/etc. | Inspiration/reference only | Verify individually | Only if compatible and documented | Prefer original implementation. |
