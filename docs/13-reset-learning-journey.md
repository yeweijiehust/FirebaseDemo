# Reset Learning Journey

Round 10 adds an in-app reset action for repeated funnel practice.

## Why This Exists

Growth analytics learning is repetitive by design.

You often need to run the same funnel several times while checking:

```text
event order
event parameters
user properties
experiment exposure
duplicate event prevention
```

Before this round, repeating the first-user journey required clearing app data manually.

Now Analytics Lab includes:

```text
Reset learning journey
```

## What Reset Clears

The reset action clears local product state:

```text
selected onboarding goal
active habit
habit logs
```

After reset completes, the app navigates back to Onboarding.

## What Reset Tracks

Reset emits:

```text
learning_journey_reset
```

Parameters:

```text
source=analytics_lab
```

Reset also clears user properties that describe journey state:

```text
onboarding_goal
activation_status
```

## What Reset Does Not Clear

Reset does not clear Firebase Remote Config assignment.

That is intentional. It lets you repeat the same local funnel while keeping the current experiment configuration stable.

## DebugView Exercise

1. Complete onboarding.
2. Create a habit.
3. Log today.
4. Open Analytics Lab.
5. Tap Reset learning journey.
6. Confirm `learning_journey_reset` appears.
7. Confirm the app returns to Onboarding.
8. Repeat the funnel.

