# Test Plan

## Unit tests

- clamp and normalization
- every curve type
- synergy edge evaluation
- cycle detection
- config validation
- config migration
- EGO Load aggregation
- Readiness calculation
- Recovery Budget allocation
- origin modifier resolution
- acclimation gain/decay
- item weight precedence
- food-memory diminishing returns
- sleep-quality calculation
- serialization round-trip

## Integration/GameTests

- mod loads on dedicated server
- required attributes attach to player
- state attachment persists
- death/respawn policy
- dimension change sync
- Apothic mirrors update without duplicates
- hydration consumption
- stamina cost and recovery
- fatigue and sleep debt progression
- sleep recovery and nap behavior
- nutrition registration through MariesLib
- origin assignment
- acclimation persistence
- malformed config fallback
- command permissions
- optional Apotheosis absence
- no client class load on server

## Manual tests

- Apothic Attributes GUI display
- accessibility-safe warnings
- config screen behavior
- first vertical slice walkthrough
