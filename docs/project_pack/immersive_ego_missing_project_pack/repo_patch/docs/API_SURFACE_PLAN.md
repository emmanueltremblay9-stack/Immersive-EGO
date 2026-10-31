# API Surface Plan

## Public API package

`com.oblixorprime.immersiveego.api`

Initial API classes/interfaces:

- `EgoApi`
- `EgoStatId`
- `VitalSnapshot`
- `EnvironmentSnapshot`
- `SimulationContext`
- events package for safe extension points

## Stability

No public API is stable before 0.2.0 unless explicitly marked.

## Rules

- Public API must not expose internal mutable state.
- Events must not allow direct state mutation outside the simulation frame.
- Compat integrations must use facades.
