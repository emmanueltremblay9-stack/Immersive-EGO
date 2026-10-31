# MariesLib Integration

## Role

MariesLib handles compatible long-term source-driven values, especially nutrition memory and source classification, when its stable API supports the design.

## Required facade

All MariesLib imports must remain behind:

```text
compat/marieslib/MarieLibFacade
```

## Use MariesLib for

- grains
- proteins
- vegetables
- fruits
- dairy
- sugars
- dietary variety, if stable API supports it
- gut health, if stable API supports it
- diagnostics/report providers, if stable API supports it

## Do not use MariesLib for high-frequency tick state

Keep these in EGO attachments/simulation:

- stamina
- thermal state
- wetness
- respiratory strain
- fatigue
- sleep debt
- current encumbrance
- EGO Load
- Readiness

## Dependency rule

MariesLib is a required separate runtime mod. Do not JarJar or copy its source.
