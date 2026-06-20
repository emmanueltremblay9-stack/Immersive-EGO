# Configuration Reference

Current implemented config files:

## `immersive_ego-core.toml`

Server config.

- `enableSimulation`: master switch for server-authoritative simulation. Default `false`; enabling it can allow opt-in scheduler-driven state updates when the per-world scheduler switch is also enabled.
- `mirrorUpdateEpsilon`: normalized delta required before display mirror updates. Default `0.005`.
- `stateSchemaVersion`: current persisted state schema version. Default `1`.

## `immersive_ego-client.toml`

Client config.

- `showContextWarnings`: show lightweight warnings when server-sent EGO state changes warrant presentation. Default `true`.
- `enableDebugOverlay`: enable client debug overlay for authorized server data. Default `false`.

## Roadmap SERVER Module Configs

The following NeoForge SERVER config files are registered. They are inert module gates for future slices; each currently exposes:

- `enabled`: default `false`.
- `debugLogging`: default `false`.

Files:

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

## `serverconfig/immersive_ego/simulation.toml`

Per-world dynamic server config. Created and range-repaired on server startup.

- `scheduler.enabled`: server-authoritative scheduler switch. Default `false`; when both scheduler gates are enabled, the current scheduler derives EGO Load and Readiness from existing `EgoState` values.
- `scheduler.intervalTicks`: scheduler interval in server ticks. Default `20`, range `1..1200`.
- `display.mirrorUpdateEpsilon`: scheduler-driven mirror update epsilon for changing frames. Default `0.005`, range `0..1`.

## `serverconfig/immersive_ego/synergies.toml`

Per-world dynamic synergy graph config. Created and repaired on server startup.

- `synergies.edges`: list of declarative synergy edges using `source -> target : weight`. Default empty list.
- Targets must be known display-only EGO paths. Capability paths may be sources when the evaluator receives normalized external values.
- Duplicate source-target pairs, unknown endpoints, unsafe weights, and cyclic graphs are repaired to an empty graph.

Additional dynamic TOML maps under `serverconfig/immersive_ego/` are still future work.
