# PR2 — humidité de surface persistante (alpha.62)

## Contrat et provenance

Le mandat utilisateur PR2, complété le 2026-10-16, constitue la spécification autonome. Les deux conversations privées fournies ne sont pas accessibles ici (403 du proxy) ; aucune tentative de contournement. Les six décisions acquises priment : mort → zéro ; Survie/Aventure seulement ; pause conservatrice ; immersion prioritaire ; un seul raccord thermique ; équipement neutre et indications facultatives. Les formules et le stockage ci-dessous sont des choix d'implémentation révisables, pas des décisions attribuées à l'utilisateur.

W représente uniquement l'eau mémorisée sur la surface du personnage. Ce n'est ni l'hydratation corporelle, ni l'humidité climatique, ni une température corporelle. Aucun nouveau contenu, dépendance, poids, effet caméra ou malus direct de mouvement/stamina/hydratation/sommeil/récupération.

## Propriétaire, temps et observation

`immersive_ego:wetness` est le seul propriétaire persistant : schéma 1, W normalisé, marqueur de quarantaine. Ni le cache, ni la session transitoire ne possèdent une seconde valeur canonique. Le choix d'un attachement distinct évite de migrer les états EGO/origine/blessure et permet sa sauvegarde même si la frame thermique ne change pas.

`EgoWetnessRuntime` observe sur chaque `PlayerTickEvent.Post` serveur, puis appelle le calcul pur et écrit l'attachement **avant** la sélection de frame thermique dans `EgoSimulationScheduler`. Une garde par identité d'entité et `tickCount` consomme exactement 0,05 seconde par callback distinct actif. Pas de durée accumulée, ni de file d'expositions à rejouer. Le scheduler thermique conserve son intervalle configurable (1–1200 ticks) ; W est intentionnellement sous-échantillonné au tick serveur, indépendamment de cet intervalle. Une exposition d'un tick est conservée dans son ordre ; une exposition entièrement entre deux observations (<1 tick) ne peut pas être détectée. Les ralentissements TPS ralentissent la simulation ; aucune compensation par horloge réelle.

La durée ne dépend pas de l'heure du monde, du temps hors ligne ou d'un écart de `tickCount`. Les lectures/debug, sauvegardes et rafraîchissements de cache n'appellent jamais l'avancement. Les transitions de mode/système consomment une garde inactive mais aucune durée ; la reprise n'absorbe que son tick présent. La mort efface la session, remet W à zéro sans `copyOnDeath`, puis une nouvelle exposition humide est acquise normalement. Déconnexion : session éliminée, attachement conservé. Dimension : attachement conservé, observation/cache et diagnostic appliqué invalidés ; garde du tick conservée pour éviter un doublon de transition.

L'eau vient de `ServerPlayer.isInWater()` (état de contact natif calculé par Minecraft avant l'événement Post). Un bateau flottant ne suffit pas à mouiller le corps. La pluie exige `ServerLevel.isRainingAt(position)` et une précipitation de biome `RAIN`. Neige/lave ne deviennent pas de la pluie/de l'eau. Aucune intégration arbitraire de fluides tiers : le contrat natif Minecraft des fluides marqués eau reste la frontière. La détection de pluie est au bloc des pieds ; pas de simulation textile, de gouttes latérales ni de pluie sous un toit réellement bloquant la requête native. Le cache est invalidé dès qu'un échantillon change et à chaque transition ; il ne mémorise jamais W persistant, et le thermique de ses instantanés bruts est recalculé avec W courant à chaque lecture de service.

## Formules qualifiées, unités et limites d'équilibrage

Toutes les charges/humidité/précipitation et W sont sans dimension, normalisées [0,1]. `dt` est en secondes de simulation, les taux en s⁻¹.

- Immersion : `W' = W + (1-W) × (1-exp(-1.4×dt))`.
- Pluie reçue sans immersion : même formule avec taux `0.08`.
- Sans exposition : `W' = W × exp(-k×dt)` ; `k = clamp(0.035 × (1 + 0.8×heatLoad - 0.5×humidity - 0.35×climateWetnessPressure), 0.02, 0.08)`.
- Après séchage seulement, W ≤ 0,001 devient exactement zéro. Une acquisition brève n'est pas supprimée par ce seuil. `dt=0` ne modifie même pas une valeur sous le seuil.

Justification : montée rapide dans l'eau (95 % depuis zéro en ≈2,14 s), progressive sous pluie (≈37,45 s), continuité lors de réexposition, séchage borné praticable en continuant à jouer. À saturation, pas de séchage simultané sous pluie/immersion. Le climat modifie k, mais ne crée jamais W à partir d'un corps sec. Avec des profils normalisés, k effectivement atteint 0,020–0,063 : depuis W=1 vers le seuil sec, ≈110–346 s de simulation. Le plafond défensif 0,08 n'est pas atteint par les entrées normalisées actuelles. Le froid n'a pas de coefficient autonome : ses profils agissent ici par leur faible chaleur et leur humidité. Le toit coupe l'apport ; aucun bonus instantané de chauffage/séchage.

Ce sont des coefficients provisoires de gameplay, non physiques, non présentés comme déjà équilibrés. Pas de vent/vitesse/armure/chauffage local dans k ; pas de refroidissement évaporatif. Les exponentielles donnent l'équivalence des pas fractionnés à exposition/climat constants (tolérance testée 1e-13 au-dessus du seuil). Le seuil zéro est une troncature de gameplay de 0,001, **pas** une tolérance numérique ni une fidélité de détection de pluie. À travers un changement d'exposition/climat il faut conserver l'ordre, pas remplacer par une moyenne d'intervalle.

## Un seul raccord thermique

Inventaire initial des consommateurs de `wetness()` : diagnostic `EgoCommands`, acclimatation `EgoOriginsService`, thermique `EgoThermalSnapshotCalculator`. Les deux premiers gardent l'exposition instantanée historique. Le service thermique remplace uniquement l'entrée liquide de sa branche existante par W actif : `max(W, climateWetnessPressure × unshelteredExposure)`. Ce maximum préserve le plancher climatique existant sans additionner deux fois l'humidité. La contribution existante au froid reste `0.30 × pressure × exposure` ; aucune deuxième pénalité n'est ajoutée. L'ancien overload pur est conservé pour les consommateurs/tests historiques.

Conséquence à rendre visible : augmenter W ne garantit pas une hausse du thermique si le climat domine déjà le maximum, si l'abri réduit l'exposition ou si la chaleur domine la contrainte. Une variation W est néanmoins sauvegardée. En pause, W contribue zéro au raccord ; le climat historique reste calculé. Les autres systèmes EGO ne sont pas globalement désactivés par cette mécanique.

## Persistance et données endommagées

Absence d'attachement / schéma 0 absent : initialisation explicite sèche. Schéma 1 valide : round-trip. Valeur NaN/non finie/hors bornes ou champ de mauvais type/manquant en schéma courant : normalisation défensive locale, marqueur de quarantaine persistant, avertissement serveur et suspension sans effet W. Le marqueur empêche une réparation silencieuse lors de la sauvegarde suivante. Schéma inconnu : version et W connus conservés, avancement/effet suspendus et diagnostic explicite. Aucun autre attachement n'est décodé/réinitialisé par ce sérialiseur. Les champs futurs inconnus ne sont pas garantis préservés ; aucune compatibilité de rétrogradation vers un ancien JAR n'est promise. Pas de commande de réparation automatique.

## Retour et inspection

`/ego debug wetness` (permission 2) : W, schéma/quarantaine, activité ; exposition/climat/thermique d'une **prévisualisation fraîche** ; dernier pas W **appliqué**, dimension/tick/fraîcheur, avant/après, dt, acquis/séché, taux et ticks consommés. Il ne présente pas la prévisualisation comme une frame EGO déjà appliquée. Les codes techniques d'activité restent des valeurs de debug ; textes et notices sont FR/EN.

Les messages actionbar décrivent une sensation, sans inventer sa cause : humide (W≥0,15), trempé (W≥0,65), de nouveau sec (W≤0,02). Le seuil perceptif sec n'est pas le zéro de simulation. Hystérésis trempé→humide à ≤0,50 et humide→sec à ≤0,02 ; au moins 200 ticks actifs entre messages. Pas de message répété à saturation ni affirmation de séchage sous pluie stable. Opt-out serveur `showWetnessNotices=false` ou individuel `/ego wetness notices false` ; `true` réactive. Le choix individuel dure la session (déconnexion le réinitialise), sans nouvelle donnée persistante ni jauge.

## Risques et qualification

Voir [matrice et preuves](WETNESS_VALIDATION.md). Risques explicitement testés : contexte périmé, W non sauvegardé quand EGO stable, double branche thermique, migration perturbant un autre attachement, replay d'intervalle après pause/mort, fréquence du scheduler, saturation/oscillations de notices, régression des consommateurs instantanés. Échantillonnage complet à 20 Hz par joueur : pas de benchmark multijoueur massif réalisé ; coût et impossibilité d'observer le sous-tick sont des limites réelles, pas un motif pour prétendre une précision continue. Aucun équilibrage général validé.
