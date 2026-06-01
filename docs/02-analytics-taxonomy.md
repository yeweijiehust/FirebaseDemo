# Analytics Taxonomy

## Principles

Analytics events should describe meaningful user behavior, not implementation details.

Events must not include personally identifiable information.

Event and parameter names should remain stable so dashboards and experiments do not break during refactoring.

## Funnel

```text
App Opened
  -> Onboarding Started
  -> Goal Selected
  -> Onboarding Completed
  -> Habit Created
  -> Habit Logged
  -> Progress Viewed
```

## Events

### app_opened

When: app content is first shown.

Parameters:

```text
source
```

### screen_viewed

When: a top-level screen becomes visible.

Parameters:

```text
screen_name
```

### onboarding_started

When: onboarding is first shown.

Parameters:

```text
variant
```

### onboarding_goal_selected

When: the user selects a goal.

Parameters:

```text
goal_id
variant
```

### onboarding_completed

When: the user finishes onboarding and moves to Habit Setup.

Parameters:

```text
goal_id
variant
```

### habit_suggested_selected

When: the user selects one suggested habit.

Parameters:

```text
habit_id
habit_category
source
variant
```

### habit_created

When: the active habit is saved.

Parameters:

```text
habit_id
habit_category
source
```

### habit_logged

When: the user logs a habit completion.

Parameters:

```text
habit_id
habit_category
streak_count
completion_count
```

### progress_viewed

When: the user opens the Progress page.

Parameters:

```text
streak_count
completion_count
activation_status
```

### experiment_exposed

When: the user sees a UI surface controlled by an experiment.

Parameters:

```text
experiment_key
variant
screen_name
```

### remote_config_activated

When: fetched Remote Config values become active.

Parameters:

```text
source
```

### remote_config_fetch_failed

When: Remote Config fetch fails.

Parameters:

```text
error_type
```

## User Properties

```text
onboarding_goal
activation_status
onboarding_variant
home_headline_variant
suggested_habit_variant
```

## Remote Config Keys

```text
onboarding_variant
suggested_habit_variant
home_headline_variant
analytics_lab_enabled
```

## Experiment Exposure Rule

Each experiment should emit `experiment_exposed` when the user reaches the screen where the variant can influence behavior.

Exposure should not be emitted merely because a config value was fetched.

