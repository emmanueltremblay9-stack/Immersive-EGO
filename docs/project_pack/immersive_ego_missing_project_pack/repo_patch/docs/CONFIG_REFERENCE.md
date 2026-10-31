# Config Reference

## Server config files

- `immersive_ego-core.toml`
- `immersive_ego-environment.toml`
- `immersive_ego-physiology.toml`
- `immersive_ego-sleep.toml`
- `immersive_ego-nutrition.toml`
- `immersive_ego-psychology.toml`
- `immersive_ego-origins.toml`
- `immersive_ego-encumbrance.toml`
- `immersive_ego-synergies.toml`
- `immersive_ego-apothic.toml`
- `immersive_ego-performance.toml`

## Client config file

- `immersive_ego-client.toml`

## Dynamic world profile files

Stored under world `serverconfig/immersive_ego/`:

- `climate_profiles.toml`
- `origin_profiles.toml`
- `food_overrides.toml`
- `item_weights.toml`
- `synergy_edges.toml`
- `dimension_profiles.toml`

## Required validation features

Every config system must provide:

- `schemaVersion`
- comments
- bounds
- enum validation
- duplicate ID detection
- cycle detection for synergy graphs
- atomic reload
- last-known-good fallback
- backup before migration
- clear error path: file, section, key, reason
- lifecycle marker: `LIVE`, `WORLD_RELOAD`, or `GAME_RESTART`

## Commands

- `/ego config validate`
- `/ego config reload`
- `/ego config preset casual|immersive|hardcore`
- `/ego config dump`
- `/ego config explain`
