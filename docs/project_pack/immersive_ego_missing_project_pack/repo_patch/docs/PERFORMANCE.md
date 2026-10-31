# Performance

## Hard limits

- No full-world scans.
- No scanning every entity each tick.
- No expensive pathfinding during ordinary simulation.
- No full inventory recomputation every tick.
- No full-state network packet every tick.
- Bound every memory window.

## Required caches

- environment snapshots
- shelter/exposure checks
- item weights
- food classifications
- validated config snapshots
- inventory fingerprints
- synergy graph compiled form

## Update bands

| Band | Default target | Examples |
|---|---:|---|
| fast | 5–10 ticks | movement/stamina |
| normal | ~20 ticks | physiology |
| slow | 100–200 ticks | nutrition/psychology/acclimation |
| cache refresh | configurable | environment/shelter |
| sync heartbeat | configurable | network state deltas |

## Debug counters

- players simulated
- average module time
- environment cache hit rate
- mirror updates
- network bytes
- config reload duration
