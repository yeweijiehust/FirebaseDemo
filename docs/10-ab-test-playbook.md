# A/B Test Playbook

This playbook explains how to use Firebase Remote Config experiments with Growth Habit Lab.

## Experiment 1: Onboarding Copy

Remote Config key:

```text
onboarding_variant
```

Variants:

```text
control
guided
```

Hypothesis:

```text
Guided onboarding copy will increase onboarding completion because users understand why their goal choice matters.
```

Primary success event:

```text
onboarding_completed
```

Guardrail event:

```text
habit_created
```

Interpretation:

If `guided` improves `onboarding_completed` but reduces `habit_created`, the copy may be motivating users to select a goal but not helping them move into a concrete habit.

## Experiment 2: Habit Suggestions

Remote Config key:

```text
suggested_habit_variant
```

Variants:

```text
popular
personalized
```

Hypothesis:

```text
Personalized habit suggestions will increase habit creation because the suggested habits match the selected onboarding goal.
```

Primary success event:

```text
habit_created
```

Guardrail event:

```text
habit_logged
```

Interpretation:

If `personalized` improves `habit_created` but does not improve `habit_logged`, users may be choosing habits that sound relevant but are not easy to complete.

## Experiment 3: Home Headline

Remote Config key:

```text
home_headline_variant
```

Variants:

```text
progress
streak
```

Hypothesis:

```text
The streak headline will increase daily logging because it creates a clearer reason to return.
```

Primary success event:

```text
habit_logged
```

Guardrail event:

```text
progress_viewed
```

Interpretation:

If `streak` increases `habit_logged`, users may be responding to continuity motivation. If it reduces `progress_viewed`, the headline might be narrowing attention to the daily action and away from reflection.

## Experiment Setup Steps

In Firebase Console:

1. Open Remote Config.
2. Add the parameter key.
3. Add the allowed string values.
4. Set the app default value to match the code default.
5. Create an A/B test from the parameter.
6. Choose the primary metric event.
7. Add guardrail events.
8. Start with a small traffic split while learning.

## Exposure Rule

Only analyze users who were exposed to a variant.

In this app, exposure is emitted through:

```text
experiment_exposed
```

Exposure happens when the user reaches the screen where the variant can influence behavior.

## Good Experiment Hygiene

Change one thing at a time.

Write the hypothesis before starting the experiment.

Choose the primary event before looking at results.

Use guardrail events to avoid optimizing one step while damaging the next step.

Avoid stopping the test the moment one variant looks better; early results can be noisy.

Keep variant names stable after launch.

