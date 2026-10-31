# Apothic Attributes Integration

## Goal

Use Apothic Attributes as the primary character-stat page for Immersive EGO.

## Rules

- Do not copy Apothic textures, icons, language files, GUI atlas, logos, or other assets.
- Register EGO attributes normally first.
- Display-only mirror attributes must not drive gameplay.
- Use stable modifier IDs to avoid duplicate modifiers.
- Update mirrors only when value changes beyond configurable epsilon.
- Keep mirror attributes visible and clearly labeled.

## Attribute groups

- Vitality
- Capacity
- Environment
- Physiology
- Sleep
- Nutrition
- Psychology
- Adaptation
- Derived

## Optional client mixin

Only implement if normal registration is insufficient.

Requirements:

- Client-only package.
- `require=0` or equivalent safe failure behavior.
- Exact target Apothic version documented.
- No copied assets.
- No server logic.
- No wholesale GUI replacement.
- Native list remains functional if mixin fails.

## Modifier sources

Add source types only after confirming public API in the pinned version:

- Biome Origin
- Acclimation
- Climate
- Nutrition
- Sleep
- Fatigue
- Psychology
- Encumbrance
- Current EGO State
- optional Apotheosis
