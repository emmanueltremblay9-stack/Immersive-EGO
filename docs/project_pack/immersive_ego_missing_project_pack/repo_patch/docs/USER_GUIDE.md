# User Guide

## What Immersive EGO changes

Immersive EGO adds a deeper survival simulation without adding new blocks or items. Existing food, water, armor, inventory, biomes, shelter, sleep, and movement become part of a linked character state.

## Main concepts

| Concept | Meaning |
|---|---|
| Hydration | Current water reserve and water demand. |
| Thermal strain | Heat/cold stress from climate, wetness, shelter, activity, and equipment. |
| Respiration | Contextual strain from altitude, depth, dimension, exertion, and load. |
| Stamina | Immediate usable energy. |
| Fatigue | Physical exhaustion accumulated over time. |
| Sleep debt | Long-term missed restorative sleep. |
| Dietary variety | Recent food diversity and balance. |
| Gut health | Bounded digestion/absorption state. |
| Stress/Fear/Stability | Gameplay psychology states, not real diagnoses. |
| EGO Load | Aggregate systemic strain. |
| Readiness | How prepared the player is to travel, fight, work, and recover. |

## Where to view stats

Stats should appear in the Apothic Attributes page once Phase 1 is implemented. Display-only mirror attributes are prefixed with `EGO •`.

## Commands

Player-safe:

- `/ego status`
- `/ego report`
- `/ego origin get`
- `/ego synergy trace`

Admin/debug:

- `/ego stat get|set|add|reset`
- `/ego config validate|reload|preset|dump|explain`
- `/ego debug environment|state|apothic|marieslib|performance`
- `/ego origin set|reset`
- `/ego acclimation set|reset`
- `/ego repair mirrors`
