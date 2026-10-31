# Architecture

## Principle

One central simulation engine owns deterministic state transitions. Independent event handlers may collect inputs, mark dirty flags, or enqueue facts, but they must not directly cascade gameplay effects.

## High-level modules

```text
com.oblixorprime.immersiveego
├── api
├── registry
├── config
├── data
├── simulation
├── environment
├── physiology
├── sleep
├── nutrition
├── psychology
├── adaptation
├── derived
├── command
├── network
├── compat
└── client
```

## State authority

| State category | Owner |
|---|---|
| High-frequency gameplay state | `EgoState` data attachment |
| Long-term nutrition/source memory | MariesLib facade, when stable API supports it |
| Derived values | Simulation engine |
| UI display values | Display-only Apothic mirror attributes |

## Simulation frame

1. Capture previous immutable state.
2. Collect environment snapshot.
3. Calculate exposure/shelter/wetness.
4. Calculate physiological demands.
5. Calculate hydration, temperature, respiration, stamina, encumbrance, pain.
6. Calculate fatigue and sleep.
7. Update nutrition adapter values.
8. Calculate psychology.
9. Calculate acclimation.
10. Calculate recovery budget.
11. Calculate derived states.
12. Validate/clamp.
13. Commit atomically.
14. Update display mirrors if needed.
15. Send network delta if needed.

## Feedback rule

Current-frame downstream values must not affect current-frame upstream calculations. Feedback loops use the previous frame unless explicitly modeled as a safe delayed edge.
