# Third Party Notices

Immersive EGO is MIT-licensed original work unless a file explicitly states otherwise.

## Build Template

The initial Gradle and NeoForge project scaffold is adapted from the NeoForged MDK template:

- Source: https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle
- Commit inspected: `ff9c5e847cb470ee2fb63d4309f7a25a61864ba5`
- Template license: MIT, copyright 2023 NeoForged project

The original example Java and resource content was removed.

## Runtime Dependencies

- NeoForge: LGPL-2.1-only. Runtime mod loader and API for Minecraft 1.21.1.
- Apothic Attributes: MIT code, All Rights Reserved assets. Required separate dependency. No assets copied.
- Placebo: MIT code. Transitive runtime dependency of Apothic Attributes.
- MariesLib: LGPL-3.0-only. Required separate dependency. No code copied.
- Cloth Config API: LGPL-3.0-only. Client-side runtime dependency declared by MariesLib.
- Apotheosis: MIT code, All Rights Reserved assets. Optional integration target. No assets copied.
- Curios API: LGPL-3.0. Transitive development/runtime dependency pulled by Apothic Attributes; Immersive EGO does not call it directly.

## Project Assets

`src/main/resources/immersive_ego_logo_1024.png` is the user-supplied Immersive EGO logo provided for this repository.

No Apothic Attributes, Apotheosis, Cold Sweat, Tough As Nails, or other third-party textures, sounds, models, language files, GUI atlases, or logos are copied into this repository.
