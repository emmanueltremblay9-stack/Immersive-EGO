# Command Tree

## Player-safe

```text
/ego status
/ego report
/ego origin get [player]
/ego synergy trace
```

## Admin

```text
/ego stat get <player> <stat>
/ego stat set <player> <stat> <value>
/ego stat add <player> <stat> <delta>
/ego stat reset <player> [stat|all]
/ego config validate
/ego config reload
/ego config preset <casual|immersive|hardcore>
/ego config dump
/ego config explain
/ego debug environment [player]
/ego debug state [player]
/ego debug apothic [player]
/ego debug marieslib [player]
/ego debug performance
/ego origin set <player> <origin>
/ego origin reset <player>
/ego acclimation set <player> <axis> <value>
/ego acclimation reset <player> [axis|all]
/ego repair mirrors [player]
```

## Permissions

- Own stats: no elevated permission.
- Config/state mutation: operator-level.
- Another player's private debug state: configurable permission level.
