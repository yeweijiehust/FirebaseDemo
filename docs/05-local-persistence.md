# Local Persistence

Round 4 adds Room-backed persistence behind the domain repository contract.

## Tables

```text
onboarding_state
habits
habit_logs
```

## Stored Values

`onboarding_state` stores the selected onboarding goal.

`habits` stores saved habits and marks one habit as active.

`habit_logs` stores one completion per habit per date.

## Duplicate Log Rule

`habit_logs` uses the pair of `habitId` and `loggedDate` as the primary key.

That means the same habit cannot be logged twice for the same day. The repository reports this as `HabitLogResult.AlreadyLogged`.

## Architecture Boundary

The domain layer owns `GrowthRepository`.

The data layer owns:

```text
Room entities
DAO queries
database class
entity mappers
repository implementation
```

The UI and domain layers do not know that Room exists.

## Testing Approach

Repository tests use a fake local data source. This keeps those tests focused on observable repository behavior:

```text
selected goal storage
active habit storage
duplicate log handling
date ordering
reset behavior
```

Room query behavior is intentionally kept thin.

Instrumented DAO tests cover key Room-backed behavior:

```text
active habit replacement
reset journey persistence cleanup
```

