# Resume Journey Routing

Round 12 makes app startup respect saved local journey state.

## Why This Exists

Growth Habit Lab stores the user's selected goal, active habit, and habit logs locally.

Before this round, every app launch started at Onboarding even when a saved journey already existed.

## Startup Rules

The app now chooses the first screen from local state:

```text
no selected goal -> Onboarding
selected goal without active habit -> Habit Setup
active habit -> Home
```

This keeps reset behavior useful while making normal app relaunches feel persistent.

## Architecture

The domain layer exposes `GetJourneyStartPointUseCase`.

The app layer maps the domain start point to a navigation destination.

The domain layer does not depend on Compose or navigation route names.

## Testing

Unit tests cover the externally visible startup decision for fresh, partially completed, and active journeys.
