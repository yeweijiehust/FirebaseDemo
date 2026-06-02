# Full UI Flow

Round 7 turns the placeholders into a working product flow.

## Screens

```text
Onboarding
Habit Setup
Home
Progress
Analytics Lab
```

## Onboarding

The user chooses one goal.

The screen records:

```text
onboarding_started
onboarding_goal_selected
onboarding_completed
```

The selected goal is persisted and also set as the `onboarding_goal` user property.

## Habit Setup

The user chooses one suggested habit.

Suggestions are controlled by `suggested_habit_variant`.

The screen records:

```text
habit_suggested_selected
habit_created
```

The selected habit is persisted as the active habit.

## Home

The user sees the daily loop and can log today's habit.

The Home headline is controlled by `home_headline_variant`.

The screen records:

```text
habit_logged
```

Duplicate same-day logs are blocked by persistence and do not emit `habit_logged` again.

## Progress

The user sees growth metrics:

```text
total completions
current streak
longest streak
activation status
last logged date
```

The screen records:

```text
progress_viewed
```

## Analytics Lab

The Analytics Lab explains the funnel, experiment surfaces, and DebugView checklist inside the app.

## Learning Takeaway

This round connects four layers:

```text
Compose screen
ViewModel state
Domain use case
Analytics event
```

The important growth analytics idea is that events should map to meaningful product behavior, not implementation details.

