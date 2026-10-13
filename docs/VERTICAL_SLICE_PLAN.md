# First Vertical Slice Plan

> Pack-origin note: imported on 2026-06-21 from `immersive_ego_missing_project_pack.zip` as a future playtest scenario. This file is not a completed validation record.

## Scenario

Desertborn player enters a hot dry biome with heavy inventory, sprints, becomes hot and dehydrated, rests in shelter, eats a balanced meal, and sleeps.

## Required systems

| Step | Required implementation |
|---|---|
| Origin | birth-biome/origin assignment and persistent profile |
| Environment | climate profile, biome snapshot, hot/dry detection |
| Encumbrance | item weight resolver and load ratio |
| Stamina | sprint cost and regeneration |
| Hydration | heat/exertion demand and consumable recognition |
| Fatigue | activity and sleep debt progression |
| Shelter | safe shelter score |
| Nutrition | at least six groups or MariesLib-backed equivalents |
| Sleep | sleep pressure and quality |
| Recovery | recovery budget allocation |
| Derived | EGO Load and Readiness |
| Display | Apothic mirror attributes |
| Debug | `/ego synergy trace` |

## Completion evidence

- Save/reload persistence.
- Build/test commands executed.
- Screenshots/manual notes for Apothic GUI if automated GUI test is unavailable.
