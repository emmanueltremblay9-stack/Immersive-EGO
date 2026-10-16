# User Guide

Immersive EGO is not gameplay-complete yet. The current build is an alpha Phase 9 foundation.

## Installing the Current Jar

Install the jar with its required runtime dependencies:

- NeoForge for Minecraft 1.21.1
- Apothic Attributes
- Placebo
- MariesLib
- Cloth Config API on clients

The current jar loads bootstrap code, config, the roadmap SERVER config files, the first Apothic-visible EGO attribute set, persisted server-side `EgoState`, `EgoOriginState`, and `EgoInjuryMemory` attachments, a mirror update service that reflects state into display-only attributes, the dynamic per-world `serverconfig/immersive_ego/simulation.toml` and `serverconfig/immersive_ego/synergies.toml` loaders, a disabled-by-default scheduler foundation, EGO Load/Readiness derivation, climate profiles, live server-side environment sampling, an environment cache with debug counters, thermal snapshot calculation, hydration demand, vanilla drink recognition, water quality, existing-item purification recognition, respiratory strain sampling, stamina activity cost/regeneration, inventory weight resolving/caching, encumbrance hysteresis, stamina/fatigue load synergies, circadian phase modeling, sleep pressure, sleep quality, nap recovery, vanilla phantom-rest stat modeling, MariesLib-backed nutrition groups and vanilla food classifications, dietary memory, meal quality, gut health, psychology/recovery budget calculation, persistent injury memory, default origin profiles, and persistent acclimation axes.

Available commands:

- `/ego status` shows the player's current EGO summary.
- `/ego debug state` shows every normalized display value and requires permission level 2.
- `/ego debug environment` shows the executing player's cached environment sample, thermal snapshot, and cache counters; it requires permission level 2.
- `/ego debug origin` shows the executing player's persisted origin id and acclimation axes; it requires permission level 2.
- `/ego synergy trace` shows a read-only synergy trace from configured edges when present, otherwise from the built-in sample graph, and requires permission level 2.

The survival simulation remains disabled by default. Psychology, persistent injury memory, origins, and acclimation foundations are implemented, and a repeatable dedicated-server startup smoke exists, but live client/server module smoke and beta tuning are still required before release.

## Current Config

`enableSimulation` defaults to `false`. Leave it disabled for normal playtesting unless you explicitly want to test the current scheduler slice.

Roadmap SERVER module configs for environment, physiology, sleep, nutrition, psychology, origins, encumbrance, synergies, Apothic, and performance are registered with `enabled=false` and `debugLogging=false`. These files are placeholders for future slices and do not activate gameplay by themselves.

The dynamic per-world file `serverconfig/immersive_ego/simulation.toml` is created on server startup. Its scheduler values are range-repaired on load. If `enableSimulation`, `scheduler.enabled`, and the relevant module gates are turned on, the current scheduler samples live server environment data, updates thermal strain from the cached thermal snapshot, updates respiratory strain from altitude, depth, submersion, and dimension context through the physiology module, applies hydration demand through the same physiology module, applies stamina activity cost/regeneration through the same physiology module, refreshes the inventory weight cache through the encumbrance module, applies encumbrance load pressure to stamina and fatigue through hysteresis, applies sleep pressure/recovery through the sleep module, reads MariesLib dietary memory through the nutrition module, updates nutrition balance, updates injury memory and psychology/recovery budget pressure through the psychology module, assigns and advances origin acclimation through the origins module, and derives EGO Load and Readiness from the resulting server state.

The dynamic per-world file `serverconfig/immersive_ego/synergies.toml` is also created on server startup. It stores optional `synergies.edges` entries using `source -> target : weight`. Missing, malformed, invalid, or cyclic lists are repaired to an empty graph.

## Expected Gameplay Impact

No complete gameplay simulation yet. This is intentional; unfinished systems must not silently alter gameplay. Current display attributes mirror the persisted server state, which defaults to neutral values until enabled server-side systems update it.

Environment sampling currently covers climate profile selection, season phase, sky exposure, shelter, wetness, and thermal strain. Hydration currently recognizes direct vanilla water potions, milk buckets, and honey bottles and can apply scheduler demand only when the default-off physiology module is enabled. Water quality supports contaminated, questionable, potable, and purified tiers, with charcoal/coal filtering and campfire/furnace/smoker boiling recognized as existing-item purification methods. Respiration currently samples altitude, depth, submersion, and dimension pressure, scales strain by respiration efficiency, and writes `respiratory_strain` only when the same default-off physiology module is enabled. Stamina currently samples movement intensity, sprinting, swimming, airborne state, item use, scheduler interval, and stamina capacity, then applies costs or recovery through the same default-off physiology module. Encumbrance currently resolves and caches inventory weight snapshots behind the default-off encumbrance module, applies load hysteresis, and converts load pressure into stamina cost and fatigue gain. Sleep currently samples server day time, player sleep state, sleep ticks, the vanilla `time_since_rest` stat, and the sleep recovery capability behind the default-off sleep module; it models awake pressure, night sleep recovery, lower-quality day naps, the 100-tick vanilla sleep qualification, and the 72000-tick phantom restless threshold. Nutrition currently registers six MariesLib values, classifies vanilla foods, fires server-side food-consumption triggers, reads dietary memory, and converts meal quality plus gut health into `nutrition_balance` behind the default-off nutrition module. Psychology currently maps fear, persistent injury memory, and pain pressure into stress/focus/comfort/recovery budget changes behind the default-off psychology module. Origins currently persist an origin id plus heat, cold, altitude, and humidity acclimation axes behind the default-off origins module.
