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


## PR2 alpha.62

- `/ego debug wetness` — permission level 2; persisted W/activity, fresh thermal preview and separately identified last applied W step. Does not advance W.
- `/ego wetness notices <true|false>` — self-only, no operator permission required; session-scoped opt-out, still subject to server `showWetnessNotices`.

See [WETNESS.md](WETNESS.md) for units, pause semantics and freshness limits.
