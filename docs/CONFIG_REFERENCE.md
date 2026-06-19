# Configuration Reference

Current implemented config files:

## `immersive_ego-core.toml`

Server config.

- `enableSimulation`: master switch for server-authoritative simulation. Default `false` until the vertical slice is implemented.
- `mirrorUpdateEpsilon`: normalized delta required before display mirror updates. Default `0.005`.
- `stateSchemaVersion`: current persisted state schema version. Default `1`.

## `immersive_ego-client.toml`

Client config.

- `showContextWarnings`: show lightweight warnings when server-sent EGO state changes warrant presentation. Default `true`.
- `enableDebugOverlay`: enable client debug overlay for authorized server data. Default `false`.

## Required Future Server Configs

The master prompt requires these server config files:

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

Dynamic TOML maps under `serverconfig/immersive_ego/` are not implemented yet.
