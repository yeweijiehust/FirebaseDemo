# Firebase DebugView Checklist

Use this checklist to verify that the app is sending the expected analytics events while you interact with it.

## 1. Enable DebugView For The Emulator

Run this command before opening the app:

```powershell
adb shell setprop debug.firebase.analytics.app com.example.firebasedemo
```

To disable DebugView later:

```powershell
adb shell setprop debug.firebase.analytics.app .none.
```

## 2. Open Firebase DebugView

In Firebase Console:

```text
Analytics -> DebugView
```

Keep the emulator running while watching the event stream.

## 3. Fresh Install Test

Use this flow when you want to verify a clean first-user journey:

1. Uninstall the app from the emulator.
2. Install or run the debug build.
3. Open the app.
4. Select an onboarding goal.
5. Continue to Habit Setup.
6. Select a suggested habit.
7. Continue to Home.
8. Tap Log today.
9. Open Progress.
10. Open Analytics Lab.

## 4. Expected Event Order

The exact order can vary slightly around screen events, but the journey should include:

```text
app_opened
screen_viewed
experiment_exposed
onboarding_started
onboarding_goal_selected
onboarding_completed
screen_viewed
experiment_exposed
habit_suggested_selected
habit_created
screen_viewed
experiment_exposed
habit_logged
screen_viewed
progress_viewed
screen_viewed
```

## 5. Event Parameter Checks

For `screen_viewed`, verify:

```text
screen_name
```

For `experiment_exposed`, verify:

```text
experiment_key
variant
screen_name
```

For `onboarding_goal_selected`, verify:

```text
goal_id
variant
```

For `habit_suggested_selected`, verify:

```text
habit_id
habit_category
source
variant
```

For `habit_logged`, verify:

```text
habit_id
habit_category
streak_count
completion_count
```

For `progress_viewed`, verify:

```text
streak_count
completion_count
activation_status
```

## 6. Duplicate Log Test

After logging today once:

1. Return to Home.
2. Try to log today again.
3. Confirm the UI says today is already logged or the button remains disabled.
4. Confirm no second `habit_logged` event is emitted for the same habit and date.

This confirms that analytics reflects real product behavior instead of button taps.

## 7. User Property Checks

Expected user properties:

```text
onboarding_goal
activation_status
onboarding_variant
home_headline_variant
suggested_habit_variant
```

The values may appear after the relevant screen action occurs.

## 8. Common DebugView Problems

If events do not appear:

1. Confirm the debug property command used the app id `com.example.firebasedemo`.
2. Fully close and reopen the app.
3. Confirm the emulator has network access.
4. Confirm `google-services.json` is present under `app`.
5. Wait a minute; DebugView is fast but not always instant.

