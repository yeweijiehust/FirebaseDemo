# Growth Funnel Analysis

This guide explains how to read the app's analytics events as a product growth funnel.

## Funnel

```text
app_opened
onboarding_started
onboarding_completed
habit_created
habit_logged
progress_viewed
```

## Step Meanings

`app_opened` means the app successfully reached the user.

`onboarding_started` means the user saw the first activation surface.

`onboarding_completed` means the user expressed intent by selecting a goal.

`habit_created` means the user converted intent into a trackable behavior.

`habit_logged` means the user performed the core daily action.

`progress_viewed` means the user reflected on progress and retention metrics.

## Core Questions

Activation:

```text
Of users who open the app, how many complete onboarding and create a habit?
```

Engagement:

```text
Of users who create a habit, how many log it?
```

Reflection:

```text
Of users who log a habit, how many view progress?
```

Experiment impact:

```text
Do exposed variants improve the next meaningful step without hurting later steps?
```

## Useful Ratios

Onboarding completion rate:

```text
onboarding_completed / onboarding_started
```

Habit creation rate:

```text
habit_created / onboarding_completed
```

First-log activation rate:

```text
habit_logged / habit_created
```

Progress reflection rate:

```text
progress_viewed / habit_logged
```

## What To Watch For

High onboarding completion but low habit creation:

```text
The goal step is easy, but the habit step may not feel actionable.
```

High habit creation but low habit logging:

```text
Users choose habits but do not feel enough motivation or clarity to return.
```

High habit logging but low progress viewing:

```text
Users understand the action but may not see value in reflection metrics yet.
```

Variant improves one event but hurts the next:

```text
The experiment may optimize a shallow conversion instead of durable growth.
```

## Learning Exercise

Run through the app twice:

1. Use the default Remote Config values.
2. Change one Remote Config variant in Firebase.
3. Repeat the flow.
4. Compare `experiment_exposed` parameters with the next funnel event.

The goal is not to prove a real business result with a tiny sample. The goal is to practice the mechanics of event design, exposure tracking, and funnel interpretation.

