# Remote Config Layer

Round 6 adds Remote Config as the experiment source for Growth Habit Lab.

## Experiment Keys

```text
onboarding_variant
suggested_habit_variant
home_headline_variant
analytics_lab_enabled
```

## Default Values

```text
onboarding_variant=control
suggested_habit_variant=popular
home_headline_variant=progress
analytics_lab_enabled=true
```

The app always has defaults, so it can run before Firebase fetch succeeds.

## Variants

`onboarding_variant` controls the onboarding presentation:

```text
control
guided
```

`suggested_habit_variant` controls Habit Setup suggestion behavior:

```text
popular
personalized
```

`home_headline_variant` controls the Home headline:

```text
progress
streak
```

`analytics_lab_enabled` controls whether Home should show an Analytics Lab entry point.

## Architecture

`ExperimentConfigProvider` is the app-facing interface.

`FirebaseExperimentConfigProvider` is the Firebase adapter.

`GrowthExperimentConfig` is the safe app-level config model.

Raw string values are converted into enums. Unknown string values fall back to documented defaults.

## Current Tracking

Remote Config refresh can emit:

```text
remote_config_activated
remote_config_fetch_failed
```

Experiment exposure is emitted when a user reaches a screen where the experiment can influence behavior:

```text
experiment_exposed
```

Current exposure surfaces:

```text
onboarding -> onboarding_variant
habit_setup -> suggested_habit_variant
home -> home_headline_variant
```

## Learning Notes

Fetching a config value is not the same as exposing a user to an experiment.

Exposure should happen only when the user reaches the screen where the variant can affect behavior. This matters because experiment analysis should compare users who actually had a chance to experience the variant.

