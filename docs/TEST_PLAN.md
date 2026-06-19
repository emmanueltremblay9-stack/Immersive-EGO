# Test Plan

## Current Automated Tests

- `EgoMathTest`
- `EgoLoadCalculatorTest`

Run:

```powershell
.\gradlew.bat clean build
```

## Required Future Unit Tests

- clamp and normalization
- curve types
- synergy edge evaluation
- cycle detection
- config validation and migration
- EGO Load
- Readiness
- Recovery Budget allocation
- origin modifier resolution
- acclimation gain and decay
- weight precedence
- food-memory diminishing returns
- sleep-quality calculation
- serialization round-trip

## Required Future GameTests

- mod loads on dedicated server
- required attributes attach to player
- state attachment persists
- death and respawn policy
- dimension change
- Apothic mirrors update without duplicates
- hydration consumption
- stamina cost and recovery
- fatigue and sleep debt progression
- valid sleep recovery
- nap behavior
- nutrition value registration through MariesLib
- origin assignment
- malformed config fallback
- command permissions
- optional Apotheosis absence
- no client class load on server
