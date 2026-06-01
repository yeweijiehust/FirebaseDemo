# App Shell And Navigation

Round 2 introduces the first app structure without adding business behavior yet.

## Why This Round Exists

Navigation is the frame that future analytics work will attach to. Before we track events, persist state, or fetch experiments, the app needs stable destinations with clear product responsibilities.

## Destinations

```text
onboarding
habit_setup
home
progress
analytics_lab
```

## Current Flow

```text
Onboarding -> Habit Setup -> Home
Home -> Progress -> Home
Home -> Analytics Lab -> Home
```

## Future Analytics Attachment Points

`Onboarding` will emit onboarding and experiment exposure events.

`Habit Setup` will emit habit suggestion and habit creation events.

`Home` will emit screen, experiment exposure, and habit logging events.

`Progress` will emit progress viewing events.

`Analytics Lab` will explain the event taxonomy and experiment setup from inside the app.

## Current Intentional Limitations

The screens are placeholders. They do not yet load domain state, persist data, or call Firebase.

This keeps the shell easy to review before the domain and data layers arrive.

