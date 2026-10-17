# Option B qualification evidence

Candidate source commit: `0b8b49c0d0a43424a73997e294d8fc3a2a9f4dc0`. The JAR identity and fresh JUnit counts are in `candidate-identity.json`. No historical JUnit/GameTest result is counted as a new execution.

`gametest.log`: the final 37/37 run including effective AttributeModifier coverage. `packaged-class-loading.log`: actual production forgeclient loaded EGO from the single new JAR under mods. `client-samples.json` retains preparation samples separately from the 20 THERMAL_* T0/T1 samples. `client-numeric-checks.json`: all 170 numerical assertions across ten bounded scenarios passed; these are assertions, not 170 automated Minecraft tests. `verify-client.py` can reproduce them with Python's standard library and writes a separate output file.

Client screenshot 07: hot T=2. Screenshot 08: cold T=2. Screenshot 09: cold T=0.5 saturation. Screenshot 14: actual Apothic panel and current tooltip. Screenshots are untouched captures, not generated diagrams.

`client-log-excerpts.txt` retains original line numbers and includes preparation failures: an inherited immersion area, two pre-protocol drownings, then a failed fill of not-yet-loaded chunks. The fixture was moved to a loaded, solid, open platform at (64.5,200,64.5), verified before acceptance. The ten accepted scenarios use Survival, dry W=0, fixed daytime/weather, actual vanilla biome changes and normal server ticks. No EGO attachment was directly edited. The cold biome was a controlled fixture; the persisted arid origin remained unchanged.

Full raw logs, commands, world copy, failed targeted-test results, original reports, binaries and delivery archive remain in the isolated execution environment. Third-party runtime jars, vanilla assets, display authentication and private account data are not committed. Offline EGOCloud is a synthetic local fixture, not a user's account.
