# Immersive EGO

Immersive EGO est un mod NeoForge pour Minecraft 1.21.1 et Java 21.

Le but est une simulation survival-RPG configurable qui relie environnement, hydratation, respiration, stamina, sommeil, nutrition, douleur, psychologie, origine biome, acclimatation, EGO Load, Readiness, Focus, Comfort et Recovery Capacity.

Etat actuel: bootstrap Phase 0. Le projet compile, les dependances initiales sont epinglees, le logo est integre, et des tests Java purs couvrent les calculs normalises de depart. La simulation de gameplay reste desactivee par defaut jusqu'a la tranche verticale.

## Compiler

```powershell
.\gradlew.bat clean build
```

Le jar produit est `build/libs/immersive_ego-0.1.0-alpha.2.jar`.

## Installation

Installer aussi:

- NeoForge 21.1.228 ou plus recent pour Minecraft 1.21.1
- Apothic Attributes 2.9.1 ou plus recent
- MariesLib 0.1.0-beta.5 ou plus recent, separement
- Cloth Config 15.0.140 ou plus recent cote client, car MariesLib le declare
- Placebo, requis par Apothic Attributes

Apotheosis est optionnel.

## Commandes et config

Les commandes `/ego` et les systemes de gameplay ne sont pas encore implementes. Les premiers fichiers de configuration sont `immersive_ego-core.toml` et `immersive_ego-client.toml`.
