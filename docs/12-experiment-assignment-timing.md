# Experiment Assignment Timing

Round 9 improves experiment fidelity by making Remote Config readiness explicit.

## Problem

Before this round, feature ViewModels could read Remote Config defaults before Firebase finished refreshing.

That meant the app could show and track a default variant, then activate a different variant shortly afterward.

For a learning app, that is a useful bug to notice. For a real experiment, it can muddy results because assignment, exposure, and behavior may not describe the same variant.

## Updated Flow

The app now starts with a loading state:

```text
Preparing experiment settings
```

During that state:

```text
Remote Config refresh starts
No experiment-controlled screen is rendered
No experiment exposure is emitted
```

After refresh completes:

```text
The app stores one config snapshot
The navigation graph is rendered
Feature screens read the refreshed or fallback config
Experiment exposure uses the same config snapshot
```

## Failure Behavior

If Remote Config refresh fails, the app continues with documented defaults:

```text
onboarding_variant=control
suggested_habit_variant=popular
home_headline_variant=progress
analytics_lab_enabled=true
```

The failure still emits:

```text
remote_config_fetch_failed
```

This keeps the product usable while making the failure visible in analytics.

## Growth Analytics Lesson

An A/B test needs three things to line up:

```text
assignment
exposure
behavior
```

Assignment is the variant selected for the user.

Exposure is when the user actually sees a surface controlled by that variant.

Behavior is the user action measured after exposure.

If those three do not use the same variant snapshot, experiment interpretation becomes less reliable.

## What Changed In Code

`RefreshExperimentConfigUseCase` refreshes Firebase Remote Config and then returns the current app config.

`AppExperimentViewModel` exposes an `AppExperimentUiState` with readiness.

`GrowthHabitApp` waits until the experiment state is ready before rendering the navigation graph.

`ExperimentExposureTracker` emits exposure events from the ready config snapshot and deduplicates repeated exposures.

