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

The app also resumes from saved local journey state:

```text
no selected goal -> Onboarding
selected goal without active habit -> Habit Setup
active habit -> Home
```

## Analytics Attachment Points

`Onboarding` emits onboarding and experiment exposure events.

`Habit Setup` emits habit suggestion and habit creation events.

`Home` emits screen, experiment exposure, and habit logging events.

`Progress` emits progress viewing events.

`Analytics Lab` explains the event taxonomy and experiment setup from inside the app, and can reset local journey state for repeated funnel practice.

## Round Context

During Round 2, the screens were intentionally placeholders so the shell could be reviewed before domain, persistence, analytics, and Remote Config behavior arrived.

Later rounds replaced those placeholders with working feature screens.

