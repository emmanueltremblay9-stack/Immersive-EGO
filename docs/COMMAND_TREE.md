# Command Tree

Current runtime-registered command tree (v0.1.0-alpha.60):

## Player-safe

```text
/ego status
```

## Admin (permission level 2)

```text
/ego debug environment
/ego debug state
/ego debug origin
/ego synergy trace
```

## Permissions

- `/ego status`: no elevated permission.
- `/ego debug` and `/ego synergy trace`: operator-level / permission level 2.
