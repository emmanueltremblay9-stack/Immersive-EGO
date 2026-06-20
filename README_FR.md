# Immersive EGO

Immersive EGO est un mod NeoForge pour Minecraft 1.21.1 et Java 21.

Le but est une simulation survival-RPG configurable qui relie environnement, hydratation, respiration, stamina, sommeil, nutrition, douleur, psychologie, origine biome, acclimatation, EGO Load, Readiness, Focus, Comfort et Recovery Capacity.

Etat actuel: fondation de debut Phase 2. Le projet compile, les dependances initiales sont epinglees, les fichiers de configuration SERVER de base et de feuille de route sont enregistres, les attributs EGO, l'attachement persistant `EgoState`, le service de miroirs d'attributs, les chargeurs dynamiques `serverconfig/immersive_ego/simulation.toml` et `serverconfig/immersive_ego/synergies.toml`, les commandes `/ego status` et `/ego debug state`, ainsi que la premiere fondation de trame, de planificateur, de courbes normalisees, de parseur/validateur/evaluateur de synergies, de chargement TOML des synergies, de derivation EGO Load/Readiness et un garde-fou automatise contre les enregistrements/ressources interdits sont enregistres. La simulation de gameplay reste desactivee par defaut; si les deux garde-fous du planificateur sont actives manuellement, la tranche active actuelle derive seulement EGO Load et Readiness depuis l'etat serveur existant.

## Compiler

```powershell
.\gradlew.bat clean build
```

Le jar produit est `build/libs/immersive_ego-0.1.0-alpha.27.jar`.

## Installation

Installer aussi:

- NeoForge 21.1.228 ou plus recent pour Minecraft 1.21.1
- Apothic Attributes 2.9.1 ou plus recent
- MariesLib 0.1.0-beta.5 ou plus recent, separement
- Cloth Config 15.0.140 ou plus recent cote client, car MariesLib le declare
- Placebo, requis par Apothic Attributes

Apotheosis est optionnel.

## Installation Prism LAB

```powershell
.\scripts\install-mod.ps1
```

Le script verifie et installe les dependances runtime epinglees par defaut, puis installe Immersive EGO. Il ecrit `build/runtime-deps-report.json` et `build/install-report.json`. Il refuse de creer toute une arborescence cible manquante; seul un dossier mods existant, ou un dossier final `mods` manquant sous un parent existant, est accepte.

## Commandes et config

- `/ego status` affiche le resume EGO serveur du joueur.
- `/ego debug state` affiche toutes les valeurs de miroir normalisees et exige le niveau de permission 2.
- `/ego synergy trace` affiche une trace de synergie en lecture seule depuis les aretes configurees quand elles existent, sinon depuis le graphe d'exemple integre, et exige le niveau de permission 2.

Les premiers fichiers de configuration NeoForge sont `immersive_ego-core.toml` et `immersive_ego-client.toml`. Les fichiers SERVER de feuille de route `immersive_ego-environment.toml`, `immersive_ego-physiology.toml`, `immersive_ego-sleep.toml`, `immersive_ego-nutrition.toml`, `immersive_ego-psychology.toml`, `immersive_ego-origins.toml`, `immersive_ego-encumbrance.toml`, `immersive_ego-synergies.toml`, `immersive_ego-apothic.toml` et `immersive_ego-performance.toml` sont enregistres avec des modules desactives par defaut. Les fichiers dynamiques par monde sont `serverconfig/immersive_ego/simulation.toml` et `serverconfig/immersive_ego/synergies.toml`. Les listes de synergies manquantes, mal formees, invalides ou cycliques sont reparees vers un graphe vide. Les systemes gameplay complets, origines, sommeil, nutrition et recuperation ne sont pas encore implementes.
