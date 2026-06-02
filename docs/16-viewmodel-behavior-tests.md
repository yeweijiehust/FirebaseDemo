# ViewModel Behavior Tests

Round 13 adds focused unit tests for feature ViewModels.

## Why This Exists

The app's growth-learning value depends on state changes and analytics staying aligned.

Domain tests already cover calculation and repository behavior. This round adds coverage where feature behavior is coordinated:

```text
ViewModel state
repository writes
analytics events
user properties
navigation completion callbacks
```

## Covered Behaviors

Onboarding tests verify:

```text
goal selection updates state
goal selection tracks analytics
goal completion persists selected goal
goal completion tracks onboarding_completed
```

Home tests verify:

```text
saved journey state appears in Home
activation_status user property is set
new logs track habit_logged
duplicate logs do not track habit_logged again
```

Analytics Lab tests verify:

```text
reset clears local journey state
reset tracks learning_journey_reset
reset clears journey user properties
reset invokes completion
```

## Test Support

The tests use small local fakes for repositories, analytics tracking, Remote Config, and Main dispatcher control.

This keeps tests fast while still checking externally observable behavior.
