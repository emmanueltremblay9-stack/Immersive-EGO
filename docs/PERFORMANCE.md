# Performance

Performance target: dedicated servers with multiple players.

## Rules

- No full-world scans.
- No every-tick full inventory recomputation.
- No expensive pathfinding in ordinary simulation.
- Cache environment snapshots, validated config snapshots, item classifications, and weights.
- Bound all memory windows.
- Use intervals, dirty flags, and delta sync.
- Add debug counters before enabling high-frequency systems.

## Current Status

Only bootstrap code and pure Java calculations exist. No tick simulation is registered yet.

## Planned Counters

- players simulated
- average module time
- environment cache hit rate
- mirror updates
- network bytes
- config reload duration
