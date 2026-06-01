# Domain Layer

Round 3 adds the product language of Growth Habit Lab as pure Kotlin.

## What The Domain Owns

The domain layer defines the concepts that remain stable even if the UI, local database, or Firebase SDK changes.

Current concepts:

```text
ActivationStatus
GrowthMetrics
Habit
HabitCategory
HabitLog
HabitLogResult
HabitSuggestionSource
HabitSuggestionVariant
OnboardingGoal
OnboardingGoalId
SuggestedHabit
```

## Use Cases

`GetOnboardingGoalsUseCase` returns the four activation intents shown in onboarding.

`GetSuggestedHabitsUseCase` returns popular or personalized habit suggestions.

`CalculateGrowthMetricsUseCase` turns habit logs into growth metrics.

## Metric Rules

Duplicate logs on the same day count as one completion.

Current streak remains active when the most recent log was today or yesterday.

Current streak becomes zero when the most recent log is older than yesterday.

Longest streak is calculated across all historical logs, even if the current streak is broken.

Activation requires both onboarding completion and at least one unique logged day.

## Repository Contract

`GrowthRepository` describes the data operations the app needs:

```text
selectedGoalId
saveSelectedGoal
activeHabit
saveActiveHabit
habitLogs
logHabit
```

The interface lives in the domain layer, but the implementation will arrive in the data layer during a later round.

## Why This Matters For Analytics

Analytics should measure domain behavior, not UI implementation details.

Stable domain IDs such as `build_consistency`, `focus_sprint`, and `activated` will become Firebase event parameters and user properties in later rounds.

