# Debug Remote Config Refresh

Round 16 improves Firebase learning ergonomics.

## Why This Exists

Remote Config has fetch caching so production apps do not ask Firebase for new values too often.

That is sensible for release builds, but it slows down a learning loop where you change a value in Firebase Console and want to immediately repeat the funnel.

## Fetch Interval Rules

Debug builds use:

```text
minimum_fetch_interval=0 seconds
```

Release builds use:

```text
minimum_fetch_interval=3600 seconds
```

This keeps Firebase experimentation quick during development without changing production-style behavior.

## Verification

Unit tests cover both debug and release settings.
