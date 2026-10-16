# PR2 — matrice et preuves de qualification cloud

## Identité et périmètre

Branche `feat/persistent-wetness`, base main/PR1 fusionnée `9eb621b684791162862f2262fa586b8ddcc35f4d`. Identité du nouveau build local : `0.1.0-alpha.62`. Aucun ancien résultat alpha.61 n'est utilisé comme preuve PR2. Le JAR historique SHA256 `23a342a141124d2149f2c59f56161718ff5e39c2521ee70600281c164c87ee21` et ses rapports 156 JUnit sont conservés dans `/workspace/cloud-setup/pr2-alpha61-preserved/` ; les preuves des 23 GameTests et des anciens essais Desertborn restent à leurs emplacements d'origine.

Les identifiants I17/I17b, G10/G10b, I07/G12/L11 du dossier sont des **références de nouveaux contrôles**, pas des tests préexistants. La correspondance exécutée apparaît ci-dessous. Les tests unitaires sont dans `src/test/java/com/oblixorprime/immersiveego/wetness/EgoWetnessModelTest.java` ; les intégrations NeoForge dans `src/main/java/com/oblixorprime/immersiveego/gametest/EgoWetnessGameTests.java`.

## Matrice automatisée

Chaque ligne PASS désigne des assertions effectivement exécutées ; le nom précis permet de retrouver entrée/attente/assertion. Les tests historiques restent exécutés pour les régressions d'autres systèmes.

| Contrôle / entrée | Attente | Observation/preuve exécutée | Verdict |
|---|---|---|---|
| Bornes, NaN/infini, dt négatif ou nul | Rejet explicite ; dt=0 conserve même W sous le seuil | `normalizedStateRejectsNanInfinityAndOutOfRange`, `invalidDurationRejected`, `zeroDurationDoesNotEvenSnapSmallWetness` | PASS |
| W=0, eau/pluie ; immersion + pluie | Eau rapide, pluie graduelle, priorité non additive | `rainAndWaterAcquireAtDifferentRatesWithoutAdding`, GameTest `actual_water_rain_roof_and_boat_observations` | PASS |
| Exposition d'un tick, alternance eau/sec | W non perdu avant prochaine frame ; ordre conservé | `oneTickExposuresAreRetainedBelowOuterSchedulerInterval`, `orderOfBriefAndAlternatingExposureIsPreserved` | PASS |
| **Événement PlayerTick réel**, 200 observations avec eau un tick sur dix, scheduler 1/20/1200 | Même W à 1e-13 ; exactement 200 ticks consommés | GameTest `ordered_brief_water_ticks_are_independent_of_actual_outer_scheduler` poste les événements sur le bus, déplace le corps dans/hors de l'eau et fait varier le gameTime du monde de test ; restaure le contexte | PASS |
| dt fractionné, exposition constante | Équivalence hors seuil à 1e-13 | `constantExposurePartitionIsNumericallyStable` (200 × 0,05 s contre 10 s) | PASS |
| Séchage/réexposition, climat humide sur corps sec | Continuité ; zéro atteignable ; climat ne mouille pas | `wetClimateNeverCreatesBodyWetness`, `dryClimateIsFasterButWetClimateStillReachesExactZero`, `leavingExposureAndReenteringIsContinuous`, `zeroThresholdIsGameplayTruncationNotDetectionTolerance` | PASS |
| Pluie réelle puis toit bloquant la requête native | Arrêt apport, W conservé puis décroissant | GameTest `actual_water_rain_roof_and_boat_observations` vérifie d'abord les préconditions ciel/pluie/contact natifs | PASS |
| Bateau monté, corps sec | Statut passager seul ≠ immersion | Même GameTest, connexion Netty **EmbeddedChannel** en mémoire uniquement pour les appels Minecraft de montage, sans socket | PASS ; flottaison physique prolongée non simulée |
| W=1 sous pluie/eau et notices oscillantes | Aucune assertion de séchage, pas de répétition, hystérésis/cooldown | `saturatedExposureNeverClaimsDrying`, `noticesHaveHysteresisAndCooldownAndNoRepeatedSaturation` | PASS |
| Lectures répétées, cache, sauvegarde | Aucune progression W ; thermique projeté sur W actuel | `repeatedThermalPreviewsDoNotMutateStateOrInstantExposure`, GameTest `wetness_reads_cache_dimension_and_multiplayer_do_not_replay` | PASS |
| Un seul raccord thermique, instantané=1 mais W=.8 | Maximum avec climat, aucune addition ; autres instantanés inchangés | `thermalUsesExactlyOneMaxBranchInsteadOfAddingLiquidOrClimate`, anciens tests thermiques/sampler/acclimatation | PASS |
| Chaleur dominante inchangée mais W varie ; sauvegarde sans autre écriture EGO | W reste sauvegardé | `wetnessCanChangeWhileDominantHeatStrainIsUnchanged` + `wetness_save_after_real_step_when_ego_unchanged_and_logout_no_catchup` | PASS |
| NBT, ancienne sauvegarde sans W, état EGO différent | Round-trip W ; ancien W=0 ; autres données intactes | `wetness_persistence_preserves_other_state_and_legacy_default` | PASS |
| Schéma courant invalide et schéma 99 | Quarantaine persistée/diagnostiquée ; version future suspendue ; autre attachement préservé | `wetness_invalid_and_future_data_are_localized_and_diagnosed`, lecture réelle par NeoForge et avertissements dans les logs | PASS |
| **I17 / G10** : mort avec W=.9, nouvelle entité | Zéro, aucun ancien pas restauré | `g10_death_resets_without_restore_and_g10b_reacquires` | PASS |
| **I17b / G10b** : respawn réellement dans l'eau | Remise à zéro puis acquisition normale du tick courant | `actual_water_rain_roof_and_boat_observations` poste Clone(death), vérifie W=0 puis applique le contact natif eau et vérifie 0.06<W<0.1 | PASS |
| **I07 / G12** : Créatif/Spectateur, puis Aventure ; core off/on | W conservé sans effet, reprise 0,05 s sans catch-up | `g12_mode_and_disabled_pause_preserve_without_catchup` ; fixture vérifie le mode natif demandé avant l'assertion | PASS |
| Environnement off/on, scheduler off/on, saut heure monde | Pause W sans effet ; aucun grand intervalle rejoué | `module_pause_world_time_jump_and_equipment_are_neutral`, `scheduler_disable_preserves_w_and_resume_consumes_only_present_tick`, `tickClockCannotReplayDuplicatesGapsOrPausedIntervals` | PASS |
| Dimension / doublon même tick / autre UUID | Conserver W, invalider cache, pas de double durée ; isolation | `wetness_reads_cache_dimension_and_multiplayer_do_not_replay`, `respawnAndOtherPlayersHaveIndependentClocks` | PASS ; événement et état, pas voyage physique complet |
| Équiper/retirer cuir | Ni reset ni taux spécial | `module_pause_world_time_jump_and_equipment_are_neutral` | PASS |
| Commandes diagnostic et opt-out non opérateur | Trois lignes sérialisables, aucun pas supplémentaire, préférence self-only | `wetness_debug_command_executes_without_mutation_or_invalid_translation_args` | PASS |

## Commandes et résultats

Java 21 et dépendances déjà présentes ; aucune installation ni nouvelle permission réseau. Wrapper exécuté via `/workspace/cloud-setup/gradle-cloud.py` (sélection Java/proxy existant), mode `--offline`.

- `clean build` : première qualification réussie, exit 0, 175 JUnit (156 existants + 19 nouveaux), 0 échec/erreur/ignoré. Log `/workspace/cloud-setup/pr2-clean-build.log`.
- `build runGameTestServer` après correction du diagnostic et ajout des contrôles : exit 0, 175 JUnit et **34 GameTests requis / 0 optionnel / 34 réussis / 0 échoué**. Log `/workspace/cloud-setup/pr2-frequency-verified.log`, code dans `.exit`.
- Qualification finale `clean build runGameTestServer` : exit 0, 34/34 requis réussis (0 optionnel), log `/workspace/cloud-setup/pr2-final.log`. Gradle a réutilisé le résultat JUnit identique depuis son cache ; la nouvelle exécution explicite JUnit au commit final est consignée dans `/workspace/cloud-setup/pr2-head-junit.log`.
- JAR local : `build/libs/immersive_ego-0.1.0-alpha.62.jar`, SHA256 `064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6`. Contrôle packaging : pas de MariesLib jarjar ni scanner_spec embarqué.
- Le workflow existant `.github/workflows/build.yml` exécute désormais aussi `runGameTestServer` après `build`. La réussite locale ne vaut pas preuve CI distante au même commit.

Échecs antérieurs conservés : accès protégé à une méthode de fixture (compilation), fixture de mode sans `loadGameTypes`, point de pluie non exposé (déplacé au-dessus de la structure), montage sans connexion mock (remplacé par EmbeddedChannel). La première exécution client a aussi trouvé un **vrai défaut du nouveau diagnostic**, enum de traduction non sérialisable : conversion `.name()` et test d'exécution réel ajoutés. Aucun échec n'a été masqué par une désactivation d'assertion.

## Runtime graphique effectué

Dossier `/workspace/cloud-setup/pr2-live/`. Client **DE DÉVELOPPEMENT**, sorties Gradle/classes du code PR2 ; aucune prétention de runtime du JAR empaqueté. Xorg dummy non privilégié, autorité éphémère, `-nolisten tcp`, Mesa llvmpipe OpenGL 4.5 Core réellement annoncé dans `client-corrected.log`. Captures réelles et historique de commandes `actions.log`. Monde unique nouvellement créé `PR2Wetness` ; aucun monde utilisateur/historique n'a été modifié. Aire physique construite par commandes Minecraft normales, eau peu profonde sans noyade, toit, biome/météo contrôlés. Aucune commande d'écriture directe de W/EGO.

Seuls core/environment et scheduler de ce monde sont activés pour le test ; autres modules conservés désactivés. Créatif utilisé pour préparation ; Survie/Aventure utilisés pour la mécanique active. Les réglages ont été désactivés après arrêt : core/environment=false, scheduler de ce monde=false ; chunks forcés retirés et météo dynamique rétablie. Les trois lancements client ont terminé avec exit 0, Xorg avec exit 0 ; autorité éphémère supprimée. `cleanup.json` et `display-security.txt` prouvent l'arrêt/nettoyage, UID 1000, refus sans cookie et absence de TCP 6093.

| Phase réelle | Observation loggée | Verdict |
|---|---|---|
| Survie, sec à l'abri | W=0.000, active, DRY, dt=.050 | PASS |
| Eau corporelle | W=.995, IMMERSION, taux=1.400/s | PASS |
| Sortie sous toit | W=.907, DRY, taux=.027/s ; pas d'effacement instantané | PASS |
| **L11** : Créatif puis Spectateur | W=.824 stable sur plusieurs lectures espacées, mode_paused, pression thermique retombe au plancher climatique .012, dernier appliqué frais=false | PASS |
| Retour Aventure | W=.748, active, dt=.050 ; seuls 73 ticks supplémentaires depuis le dernier pas avant pause | PASS |
| Pluie extérieure | W=.450, RAIN, acquisition=.002 au dernier tick, taux=.080/s | PASS |
| Retour sous toit pendant pluie | W=.533, DRY, séchage=.001/tick affiché ; W a encore monté entre première lecture sous pluie et départ, pas une montée sous toit | PASS |
| Biome neigeux sous précipitation, extérieur | W=.386 décroît, DRY, taux=.020/s ; climat cold_wet et pression climatique .733 restent distincts de W | PASS |
| Lave, contrôle sans dégâts en Créatif | Exposition DRY, W=.189 conservé par la pause ; qualification de détection seulement, aucune revendication de séchage actif dans la lave | PASS limité |
| Arrêt complet puis nouveau processus client + rechargement monde | W exact `0.9999683116061429` identique avant/après ; aucun pas appliqué dans la nouvelle session en Créatif ; sauvegarde NBT conservée | PASS |
| Mort réelle contrôlée (`/kill`, monde de test seulement), bouton Respawn | Relecture NBT W=`0.0` et aucun ancien pas appliqué ; pas de restauration du W précédent | PASS |
| Retour dans l'eau en Aventure après respawn | W=.994, IMMERSION, taux=1.4/s, dt=.050 ; acquisition normale sans écriture directe EGO | PASS |

Les décimales du debug sont arrondies à trois chiffres : elles ne permettent pas à elles seules une preuve de tolérance 1e-13, fournie par les tests. Le rendu FR, un test de compréhension par joueurs, la flottaison longue durée, un voyage dimensionnel complet et une campagne d'équilibrage/charge n'ont pas été exécutés : **NOT_RUN**, sans les transformer en nouveaux préalables utilisateur. La stabilité/antispam pure et les traductions statiques sont qualifiées séparément.

## État final, publication et reprise

Le SHA final, le hash du JAR et l'état de la PR/CI sont consignés dans le rapport final de ce cycle ; la preuve de persistance après arrêt complet figure ci-dessus. La qualification runtime a été faite sur les sources de développement devenues ce commit, pas par lancement du JAR empaqueté. Aucune fusion ni publication de mod autorisée. L'API GitHub a renvoyé 403 ; Git HTTPS reste utilisable en lecture. Ne pas créer de jeton, élargir le réseau ou contourner le proxy pour créer une PR. Préparer tout le livrable réversible avant de signaler un éventuel blocage distant précis.
