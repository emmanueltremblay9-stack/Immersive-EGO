# Immersive EGO 🧬

Immersive EGO est un mod de simulation survie-RPG configurable pour Minecraft 1.21.1 sous NeoForge.

Le but est de connecter l’environnement, l’exposition, l’hydratation, la respiration, l’endurance, la fatigue, le sommeil, la nutrition, la santé digestive, la psychologie, la douleur, la récupération, les origines de biome, l’acclimatation, l’EGO Load, la Readiness, le Focus, le Comfort et la Recovery Capacity dans une seule simulation cohérente.

## Cible technique

- Minecraft : `1.21.1`
- NeoForge : `21.1.x`
- Java : `21`
- Mod ID : `immersive_ego`
- Package : `com.oblixorprime.immersiveego`
- Licence : MIT

## Dépendances

Obligatoires :

- Apothic Attributes
- MariesLib

Transitive/runtime :

- Placebo, via Apothic Attributes sauf si l’audit Gradle impose une déclaration directe.

Optionnelle :

- Apotheosis

Interdit :

- Dépendance ou copie d’implémentation Cold Sweat
- Assets All Rights Reserved copiés
- Code GPL copié dans le projet MIT
- Nouveaux blocs, items, entités, fluides, machines ou modèles Blockbench

## Expérience joueur

Les statistiques importantes doivent apparaître dans Apothic Attributes pour éviter un HUD surchargé. Les valeurs de gameplay restent autoritaires côté serveur. Les attributs d’affichage sont seulement des miroirs et ne doivent jamais piloter la simulation.

## Slice jouable initiale

Un joueur Desertborn entre dans un biome chaud/sec, porte un inventaire lourd, sprinte, devient chaud et déshydraté, se repose en abri, mange un repas équilibré et dort. Le slice est terminé seulement si la persistance, les miroirs Apothic, les coefficients de config et `/ego synergy trace` fonctionnent réellement.
