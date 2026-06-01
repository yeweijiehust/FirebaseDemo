# Analytics Layer

Round 5 adds the first Firebase Analytics integration.

## Goal

The app should log meaningful product behavior without letting Firebase SDK calls spread through UI, domain, or data code.

## Main Types

```text
AnalyticsEvent
AnalyticsTracker
FirebaseAnalyticsTracker
GrowthAnalyticsEvent
GrowthUserProperty
AppAnalyticsViewModel
```

## Event Builder

`GrowthAnalyticsEvent` centralizes event names and parameters.

This gives us one stable analytics vocabulary for code, tests, documentation, and Firebase DebugView.

## Firebase Adapter

`FirebaseAnalyticsTracker` converts `AnalyticsEvent` into Firebase calls.

The rest of the app depends on `AnalyticsTracker`, not Firebase Analytics directly.

## Current Tracking

The app now tracks:

```text
app_opened
screen_viewed
```

`app_opened` is tracked once per app host ViewModel lifetime.

`screen_viewed` is tracked when the visible navigation destination changes.

## Later Tracking

Future rounds will attach action events to real user flows:

```text
onboarding_started
onboarding_goal_selected
onboarding_completed
habit_suggested_selected
habit_created
habit_logged
progress_viewed
experiment_exposed
remote_config_activated
remote_config_fetch_failed
```

## Testing Approach

Unit tests verify:

```text
stable event names
stable parameter maps
app open deduplication
screen view deduplication
screen tracking order
```

Firebase itself is not unit-tested. The app tests the boundary it owns: which events it asks the tracker to send.

