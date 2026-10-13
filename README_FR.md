# Immersive EGO

Immersive EGO est un mod NeoForge pour Minecraft 1.21.1 et Java 21.

Le but est une simulation survival-RPG configurable qui relie environnement, hydratation, respiration, stamina, sommeil, nutrition, douleur, psychologie, origine biome, acclimatation, EGO Load, Readiness, Focus, Comfort et Recovery Capacity.

Etat actuel: durcissement alpha Phase 11. Le projet compile, les dependances initiales sont epinglees, les fichiers de configuration SERVER de base et de feuille de route sont enregistres, les attributs EGO, les attachements persistants `EgoState`, `EgoOriginState` et `EgoInjuryMemory`, le service de miroirs d'attributs, les chargeurs dynamiques `serverconfig/immersive_ego/simulation.toml` et `serverconfig/immersive_ego/synergies.toml`, les commandes `/ego status`, `/ego debug state`, `/ego debug environment`, `/ego debug origin` et `/ego synergy trace`, ainsi que les fondations environnement, hydratation, respiration, stamina, encombrement, sommeil, nutrition MariesLib, psychologie, budget de recuperation, memoire persistante de blessure, origine biome, acclimatation, permissions de commandes, absence optionnelle Apotheosis, garde-fous serveur et smoke serveur dedie repetable sont en place. La simulation de gameplay reste desactivee par defaut; si les garde-fous pertinents sont actives manuellement, les tranches actives peuvent mettre a jour les miroirs serveur, la pression de douleur persistante, puis deriver EGO Load et Readiness depuis l'etat serveur.

## Compiler

```powershell
.\gradlew.bat clean build
```

Le jar produit est `build/libs/immersive_ego-0.1.0-alpha.56.jar`.

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

## Smoke serveur dedie

```powershell
.\scripts\smoke-dedicated-server.ps1 -AcceptEula
```

Le smoke demarre le profil Gradle `runServer`, attend les marqueurs de demarrage requis, arrete le serveur de developpement apres succes, puis ecrit `build/dedicated-server-smoke-report.json`.

## Commandes et config

- `/ego status` affiche le resume EGO serveur du joueur.
- `/ego debug state` affiche toutes les valeurs de miroir normalisees et exige le niveau de permission 2.
- `/ego debug environment` affiche l'echantillon environnemental et exige le niveau de permission 2.
- `/ego debug origin` affiche l'origine persistante et les axes d'acclimatation, et exige le niveau de permission 2.
- `/ego synergy trace` affiche une trace de synergie en lecture seule depuis les aretes configurees quand elles existent, sinon depuis le graphe d'exemple integre, et exige le niveau de permission 2.

Les premiers fichiers de configuration NeoForge sont `immersive_ego-core.toml` et `immersive_ego-client.toml`. Les fichiers SERVER de feuille de route `immersive_ego-environment.toml`, `immersive_ego-physiology.toml`, `immersive_ego-sleep.toml`, `immersive_ego-nutrition.toml`, `immersive_ego-psychology.toml`, `immersive_ego-origins.toml`, `immersive_ego-encumbrance.toml`, `immersive_ego-synergies.toml`, `immersive_ego-apothic.toml` et `immersive_ego-performance.toml` sont enregistres avec des modules desactives par defaut. Les fichiers dynamiques par monde sont `serverconfig/immersive_ego/simulation.toml` et `serverconfig/immersive_ego/synergies.toml`. Les listes de synergies manquantes, mal formees, invalides ou cycliques sont reparees vers un graphe vide. Les tests client live, la validation GUI Apothic, les GameTests de progression runtime restants et le tuning beta restent requis avant beta/release.
