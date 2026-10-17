# Immersive EGO — qualification empaquetée et suivi des limites

Date : 17 octobre 2026. Résultat autonome destiné au parent ; les conclusions et valeurs utiles sont reproduites ici, sans nécessiter les liens locaux du conteneur.

**Le JAR original reste inchangé. La qualification cloud est réellement exécutée ; le gate complet reste ouvert sur l’effet de `thermal_tolerance`, dont la formule n’est pas définie par les contrats consultés.** Les limites initiales d’assets et de sortie audio numérique ont été résolues pendant ce suivi. Aucun correctif gameplay, aucune PR, aucun merge ni publication.

## 1. Identité et preuves initiales conservées

- DEV : `emmanueltremblay9-stack/Immersive-EGO`.
- Commit exact : `3f7734cc4bbd6802c12c09b5990544b86faac47a` ; arbre `7119c861b3677043f05e9b2325c4089913c83d94`.
- JAR : `immersive_ego-0.1.0-alpha.62.jar`, 500 323 octets.
- SHA-256 : **`064e424232a7ca751ae8594a39573b0d4a426129054bef08dcdbd2a6c46653c6`**.
- Minecraft 1.21.1 ; NeoForge 21.1.233 / FML 4.0.42 ; Temurin Java 21.0.8+9 ; Apothic Attributes 2.9.1, Placebo 9.9.1 et MariesLib 0.1.0-beta.5 séparés ; Apotheosis absent.
- Chargement réel par `BootstrapLauncher`, target **`forgeclient`**, classes EGO issues du JAR unique dans `mods`. Aucun `runClient` de développement comme substitut.
- Clean build initial : **175 JUnit nouvellement exécutés, zéro échec/erreur/ignoré**. La CI existante [38920721166](https://github.com/emmanueltremblay9-stack/Immersive-EGO/actions/runs/38920721166), tentative 1, est `completed/success` sur le même commit par relecture de métadonnées ; aucun nouveau run CI revendiqué.
- Première matrice conservée telle quelle : 28 PASS, 1 FAIL, 9 NOT_RUN, 76 relevés. Ces nombres désignent des lignes de qualification, pas des JUnit.

Le suivi utilise une copie de la sauvegarde de test, dans un nouveau dossier de preuves, avec le même JAR. Il ne crée pas de copie concurrente du dépôt ni de PR2. Les **199 fichiers de preuves initiales inventoriés sont identiques après le suivi**. Le ZIP original conserve le SHA-256 `62a42393decd328ba4f5258413aadb44bee82c5f3b199c7efee03b1791928d4b`. Les sources gameplay du checkout sont inchangées ; seules les notes documentaires évoluent.

## 2. Contrat exact de thermal_tolerance

Le [texte anglais DEV, ligne 35](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/src/main/resources/assets/immersive_ego/lang/en_us.json#L35) dit : « Multiplier for heat and cold tolerance once environment simulation is active. » Le texte français porte la même intention. Cela promet un multiplicateur de tolérance, mais ne définit ni la grandeur à multiplier, ni son inverse, ni l’ordre des opérations.

Le [catalogue DEV](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/src/main/java/com/oblixorprime/immersiveego/registry/EgoAttributeCatalog.java) précise le rôle `CAPABILITY`, la valeur neutre 1, les bornes 0,25–4 et un sentiment positif. Le [contrat d’intégration Apothic](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/docs/APOTHIC_INTEGRATION.md) traite l’enregistrement, la visibilité et les miroirs ; il ne donne pas de formule thermique. Le prompt de phase 1 demande l’enregistrement sans simulation complète ; le scénario vertical exige une contrainte thermique observable, sans définir ce multiplicateur.

GOV live est toujours `8004452ee3eb831a8840a99fa0ad0b487e0986d7`, dépôt **`emmanueltremblay9-stack/Immersve-EGO-GPT`**. Les documents relus sont PROJECT_INSTRUCTIONS_A_COLLER, PROJECT_CHARTER, DECISIONS, ROADMAP, SOURCE_INDEX et [EVIDENCE_GATES](https://github.com/emmanueltremblay9-stack/Immersve-EGO-GPT/blob/8004452ee3eb831a8840a99fa0ad0b487e0986d7/EVIDENCE_GATES.md). Le §3 demande que les critères viennent du code/config du candidat et du scénario accepté, sans seuil gameplay arbitraire. **Aucune formule de thermal_tolerance n’a été trouvée dans ce corpus.** Ce constat ne prétend pas couvrir une spécification privée extérieure non fournie.

### Chemin de code constaté

1. `EgoAttributes` enregistre `THERMAL_TOLERANCE` et l’attache aux joueurs ; Apothic peut le lire et l’afficher.
2. `EgoSimulationScheduler.onPlayerTick` appelle le calcul W serveur, puis collecte environnement et contextes des modules.
3. [EgoThermalSnapshotCalculator](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/src/main/java/com/oblixorprime/immersiveego/environment/EgoThermalSnapshotCalculator.java) calcule les pressions chaleur/froid et `thermalStrain = max(heatPressure, coldPressure)`. Le raccord W reste `max(W actif, pression climatique × exposition)`.
4. [EgoSimulationFrameCalculator, lignes 186–193](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/src/main/java/com/oblixorprime/immersiveego/simulation/EgoSimulationFrameCalculator.java#L186) copie cette contrainte dans l’état, sans lire `thermal_tolerance`.
5. [EgoOriginsService, lignes 48–55](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/3f7734cc4bbd6802c12c09b5990544b86faac47a/src/main/java/com/oblixorprime/immersiveego/origins/EgoOriginsService.java#L48) retire ensuite `0,08 × max(acclimatation chaleur, acclimatation froid)` ; les valeurs dérivées suivent.

Il n’existe pas de consommateur de cette capacité dans le chemin de simulation thermique. Les lectures génériques d’interface/attributs et la prévisualisation des synergies ne prouvent pas un effet gameplay.

### Preuve avant PR2 et observation réelle

- Dans le commit pré-PR2 [50d9e4b9](https://github.com/emmanueltremblay9-stack/Immersive-EGO/tree/50d9e4b9baca046f00277f61ba17728be9215d2b), l’attribut est déjà déclaré, le même texte est présent et le chemin thermique ne le consomme déjà pas.
- L’intention et l’enregistrement existaient même dans [789238c475ecabc19808b9ac7d99df7f457670b8, 20 juin 2026](https://github.com/emmanueltremblay9-stack/Immersive-EGO/blob/789238c475ecabc19808b9ac7d99df7f457670b8/src/main/resources/assets/immersive_ego/lang/en_us.json#L35).
- Le diff thermique PR2 ajoute l’entrée W corporelle et remplace la branche liquide ; il ne supprime aucun calcul de `thermal_tolerance`.
- Dans le client empaqueté, les bases 1 et 2 ont été confirmées. Après retrait analytique du bénéfice d’acclimatation, la contrainte brute reste **0,870 dans les deux cas**. Les écarts observés dans l’état final s’expliquent exactement par l’acclimatation.

La preuve « avant PR2 » est une preuve de source historique identifiée ; aucun ancien client n’est présenté comme réexécuté. L’effet annoncé est absent, mais sa correction numérique demeure une décision de gameplay.

## 3. Décision ciblée proposée — aucune option appliquée

Notations : T = capacité bornée à [0,25 ; 4], R = contrainte brute `max(chaleur, froid)`, A = `max(acclimatation chaleur, acclimatation froid)`.

| Option | Règle à approuver | Conséquences |
|---|---|---|
| **A — recommandée pour un correctif minimal** | Appliquer `clamp01(R / T)` à l’entrée de l’état thermique, puis conserver le bénéfice d’origine existant, soit au final `clamp01(clamp01(R/T) − 0,08A)` | T=1 conserve le comportement. T=2 réduit la contrainte avant acclimatation. Affecte les consommateurs de cet état, dont sommeil et valeurs dérivées. Ne modifie pas W, le climat brut, la demande hydrique fondée sur le snapshot brut ni la cible d’acclimatation. Exemple R=.87, A=.8 : .806 à T=1 ; .371 à T=2. |
| **B — résistance appliquée au snapshot** | Diviser les pressions chaleur/froid par T avant de construire le snapshot, sans modifier la branche `max(W, climat)` | Portée plus large : demande hydrique et cibles d’acclimatation peuvent changer, en plus du sommeil et des dérivées. Demande une décision explicite sur les diagnostics bruts/ajustés et les effets indirects. |
| **C — capacité explicitement différée** | Garder le calcul neutre et annoncer clairement que la capacité est réservée/inactive | Aucun changement de gameplay, mais ne satisfait pas le critère d’effet réel des huit capacités. Le gate reste partiel, sauf décision explicite de périmètre ; ne pas transformer le FAIL initial en PASS. |

**Décision nécessaire : choisir A, B ou le report C.** L’option A est une proposition de faible portée, pas un contrat découvert. Tant que ce choix n’existe pas, aucune formule, aucun test qui imposerait arbitrairement cette formule, aucune PR de correctif et aucune CI de nouveau commit ne sont créés. Après choix A ou B : tests des valeurs 1/.25/2/4, chaleur/froid, clamping et ordre d’acclimatation ; JAR du nouveau commit, comparaison empaquetée, PR brouillon et CI de ce commit, sans merge.

## 4. Complément des neuf NOT_RUN initiaux

La matrice initiale reste immuable. Ce tableau donne le statut de suivi, en séparant les sous-cas des groupes initiaux.

| ID initial | Contenu initial | Travail nouveau et statut exact |
|---|---|---|
| **N01** | Nouvelle exécution GameTest | **PASS : 34 tests requis nouvellement exécutés**, exit 0, `All 34 required tests passed`. Commande : wrapper cloud `--offline --no-configuration-cache -I gametest.init.gradle runGameTestServer`. Répertoire de run isolé, code du même commit. Ce serveur GameTest utilise le chemin de développement ; il est distinct des preuves client empaqueté. Aucun nouveau JUnit n’a été nécessaire dans ce suivi sans modification de code. |
| **N02** | Environment off et scheduler off séparément | **PASS client empaqueté.** Environment off : W=.9525701447895831 inchangé sur 129 ticks. Scheduler off : W=.9286484773389306 et état EGO entier inchangés sur 143 ticks. Reprise observée avec dt=.050 s. Le TOML du scheduler est chargé au démarrage serveur ; la mesure utilise donc un rechargement réel, sans supposer un hot reload inexistant. |
| **N03** | Pluie, neige, bateau, immersion prolongée, dimensions | **PASS borné client.** Pluie : W .8509895584→.8997149750, rate .08/s, formule vérifiée. Neige : exposition DRY, .8606374887→.7795161296, séchage .02/s. Bateau sans corps immergé : .5858995629→.4466268152, DRY. Immersion complète : W→.9999999999949098, contrainte respiratoire .569257148, air 271→−10, santé 20→12 ; sortie avant mort. End : W=.9756269392636486 conservé, origine arid. Avec Overworld/Nether déjà conclus, les trois dimensions vanilla ont une preuve. Aucune dimension ajoutée par d’autres mods revendiquée. |
| **N04** | Sieste, seuil phantom, longue durée | **PASS pour sieste réelle** de jour à 6000 pendant un orage vanilla : fatigue .1606042511→.1389079154 sur 100 ticks, lit réellement occupé. Les nouveaux GameTests passent pour la qualification vanilla à 100 ticks, le seuil 72000 et la récupération de sieste inférieure à la nuit. **NOT_RUN** : observation live d’une heure/72000 ticks et équilibrage longue durée ; le modèle testé ne remplace pas cette durée réelle. |
| **N05** | Apotheosis présent et affixes | **BLOCKED pour présence** : version épinglée 8.5.4 absente du cache ; GET du metadata sur le dépôt Maven déclaré Shadowsoffire reçoit **403 Forbidden**, arrêt sans miroir/proxy/credential alternatif. L’intégration d’affixes EGO est encore planifiée dans APOTHEOSIS_COMPAT.md ; **NOT_RUN/N/A pour affixes non implémentés**, aucune création d’affixe pour faire passer le test. L’absence optionnelle continue à fonctionner. |
| **N06** | Multijoueur dédié, charge élevée, équilibrage | **NOT_RUN** pour clients multijoueurs réels, forte charge et longue durée. Le nouveau serveur GameTest dédié démarre et exécute sa suite ; cela ne prouve pas ces trois cas. Aucun second compte authentifié fourni, aucun profil de charge/critère d’équilibrage fixé. Pas de désactivation d’authentification pour fabriquer une preuve. |
| **N07** | Assets et audio complets | **Résolu pour les assets et le rendu audio numérique cloud.** Les 3888 objets officiels sont présents et vérifiés ; deux nouveaux clients n’ont aucune exception d’asset manquant. Panorama réel visible, OpenAL et SoundEngine démarrés, WAV stéréo avec signal réel. Pas de test acoustique sur un haut-parleur physique, absent du conteneur et hors installation PC autorisée. Deux avertissements d’événements sonores vanilla sans définition dans le sounds.json officiel persistent et sont détaillés plus bas. |
| **N08** | Intégration gameplay complète des synergies | **NOT_RUN : pipeline actif non implémenté.** L’évaluateur est appelé par `/ego synergy trace`, pas par la chaîne de simulation qui persiste l’état. Le calcul de frame existant applique environnement/physiologie/sommeil/nutrition/psychologie/origines puis dérivées. Activer un drapeau ne crée pas une intégration inexistante. Aucun système nouveau ajouté. |
| **N09** | Développement abris puis équipement | **N/A à cette qualification, inchangé.** Ce sont les phases ultérieures autorisées dans cet ordre, pas des tests du candidat existant. L’abri existant et la neutralité de l’équipement pour W ont déjà leurs preuves initiales ; aucun nouveau mécanisme développé. |

Le suivi ajoute **21 relevés client et 9 contrôles numériques réussis**. Les séries déjà conclues n’ont pas été répétées : les nouveaux lancements servent au chargement des assets, à la configuration scheduler et à la sieste.

## 5. Assets et audio : inventaire, sources, disponibilité

L’index officiel Minecraft **17** référence **3911 noms, 3888 objets uniques, 825 818 935 octets uniques**. Avant suivi : zéro objet externe présent. Répartition par nom (les tailles par nom peuvent compter des doublons) :

| Catégorie | Entrées |
|---|---:|
| Sons, musiques et sounds.json | 3728 |
| Langues | 143 |
| Textures | 22 |
| Icônes | 12 |
| Polices | 3 |
| Resource packs intégrés externes | 2 |
| pack.mcmeta | 1 |

Les exceptions initiales identifient précisément : `icons/icon_16x16.png`, `minecraft/resourcepacks/high_contrast.zip`, `minecraft/resourcepacks/programmer_art.zip`, `minecraft/font/include/unifont.json`, les six `panorama_0.png` à `panorama_5.png`, `panorama_overlay.png`, et `realms/lang/en_us.json`. Les trois polices référencées et toutes les autres entrées sont listées avec taille, SHA-1 et URL dans l’inventaire livré. Les textures de terrain déjà présentes dans le JAR expliquent pourquoi le gameplay restait visible avant ce téléchargement.

**Source et voie :** chaque objet vient de `https://resources.download.minecraft.net/<2 premiers caractères SHA1>/<SHA1>`, selon l’index officiel déjà identifié. L’ancien CONNECT 403 du 15 octobre est conservé. La vérification normale demandée le 17 octobre à 10:25 UTC a répondu 200 pour l’icône officielle ; les 3888 objets ont ensuite été téléchargés par la même connexion habituelle, avec contrôle de taille et SHA-1, sans changement de proxy, sécurité ou credentials. Total vérifié : 825 818 935 octets. Aucun échec de téléchargement. Les assets tiers officiels ne sont pas republiés dans l’archive de preuves.

**Audio, problème distinct :** `/dev/snd` et un serveur PulseAudio local sont absents ; le client initial échoue à ouvrir le périphérique OpenAL/ALSA. Aucun paquet système ni périphérique n’a été installé. Le backend documenté [OpenAL Soft Wave File Writer](https://github.com/kcat/openal-soft/blob/master/alsoftrc.sample) a été choisi uniquement pour les nouveaux processus, via un fichier de configuration dans le dossier de test et l’option [ALSOFT_CONF](https://github.com/kcat/openal-soft/blob/master/docs/env-vars.txt). Il rend le son réellement mélangé par le client dans un WAV, sans haut-parleur.

Résultat : `OpenAL initialized on device Wave File Writer`, `Sound engine started`. Une commande vanilla joue `minecraft:block.note_block.pling`. Le WAV réel contient 55,34 s, deux canaux, 48 kHz, 16 bits, pic 15478 et RMS 1067,12 ; un extrait de huit secondes est conservé. Ce n’est ni un son synthétisé par l’assistant ni un simple backend muet.

Les avertissements `minecraft:item.goat_horn.play` et `minecraft:entity.goat.screaming.horn_break` restent présents. Ces deux clés ne figurent pas dans le sounds.json officiel vérifié ; les sons numérotés `item.goat_horn.sound.0` etc. existent. Ce ne sont donc pas des objets manquants du téléchargement ; aucune réparation arbitraire du pack n’a été faite. Les erreurs DNS des clés publiques Minecraft et des données Patreon Placebo restent également consignées ; aucune connexion réseau/multijoueur réussie n’en est déduite.

## 6. Sortie, livrables et limites finales

Les trois clients supplémentaires ont chacun quitté avec exit 0 ; le serveur GameTest aussi. Xorg privé est arrêté, son cookie éphémère supprimé. Les 13 TOML de la fixture supplémentaire ont été restaurés, dont core/scheduler désactivés ; les gamerules de test sont remis à leur valeur normale. La nouvelle sauvegarde et son datapack restent des preuves. La configuration audio n’a concerné que ces processus ; aucun réglage audio global modifié.

Livrables : ce rapport autonome, l’archive initiale inchangée, le JAR original, l’annexe de preuves de suivi (logs complets, captures réelles, 21 relevés, contrôles, source historique et diff PR2, inventaire et reçus d’assets, métriques/extrait audio, restauration et empreintes). Les bibliothèques/assets tiers du client ne sont pas inclus dans la livraison.

**Reste à décider : A, B ou C pour thermal_tolerance.** Restent non qualifiés dans leur portée propre : Apotheosis présent (403), affixes non implémentés, intégration active des synergies, multijoueur/charge/équilibrage longue durée et observation live du seuil phantom sur une heure. Aucune régression PR2 n’a été démontrée dans les essais complémentaires. La qualification partielle est documentée ; aucune validation totale ou release n’est revendiquée.
