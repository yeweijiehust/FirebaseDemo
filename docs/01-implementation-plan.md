# Implementation Plan

## App Concept

Growth Habit Lab is a five-page Android app for practicing growth analytics. Users choose a goal, set up a habit, log habit completions, inspect progress, and learn how Firebase Analytics and Remote Config connect to product decisions.

## Page 1: Onboarding

Purpose: capture the user's activation intent.

Functionality:

1. Show four goals.
2. Let the user select one goal.
3. Continue to Habit Setup after selection.

Goals:

```text
build_consistency
improve_focus
learn_daily
move_more
```

Remote Config:

```text
onboarding_variant=control
onboarding_variant=guided
```

Variant behavior:

`control` shows concise goal cards.

`guided` shows goal cards with more explanatory copy and stronger next-step language.

Analytics:

```text
onboarding_started
experiment_exposed
onboarding_goal_selected
onboarding_completed
```

## Page 2: Habit Setup

Purpose: turn intent into a trackable habit.

Functionality:

1. Show suggested habits.
2. Let the user pick one habit.
3. Save the selected habit.
4. Continue to Home.

Example habits:

```text
read_10_minutes
focus_sprint
walk_15_minutes
review_notes
```

Remote Config:

```text
suggested_habit_variant=popular
suggested_habit_variant=personalized
```

Variant behavior:

`popular` shows the same list to every user.

`personalized` orders or filters suggestions based on the selected onboarding goal.

Analytics:

```text
habit_suggested_selected
habit_created
experiment_exposed
```

## Page 3: Home

Purpose: provide the daily engagement loop.

Functionality:

1. Show the selected goal.
2. Show the active habit.
3. Show whether the habit was logged today.
4. Let the user log today's completion.
5. Prevent duplicate same-day logs.
6. Navigate to Progress.
7. Navigate to Analytics Lab.

Remote Config:

```text
home_headline_variant=progress
home_headline_variant=streak
```

Variant behavior:

`progress` emphasizes total completions.

`streak` emphasizes current streak.

Analytics:

```text
screen_viewed
habit_logged
experiment_exposed
```

## Page 4: Progress

Purpose: teach retention and engagement metrics.

Functionality:

1. Show total completions.
2. Show current streak.
3. Show longest streak.
4. Show activation status.
5. Show last logged date.
6. Navigate back to Home.

Activation definition:

```text
onboarding completed and at least one habit log exists
```

Analytics:

```text
screen_viewed
progress_viewed
```

## Page 5: Analytics Lab

Purpose: make the learning explicit inside the app.

Functionality:

1. Explain the funnel.
2. List events emitted by the app.
3. List Remote Config experiments.
4. Explain what to check in Firebase DebugView.
5. Navigate back to Home.

Remote Config:

```text
analytics_lab_enabled=true
analytics_lab_enabled=false
```

Variant behavior:

`true` shows the Analytics Lab entry point.

`false` hides the entry point from Home.

Analytics:

```text
screen_viewed
```

## Development Rounds

### Round 1: Baseline and Learning Docs

Scope:

1. Remove generated comments from source files.
2. Add project documentation.
3. Keep app behavior unchanged.
4. Keep Gradle files unchanged by Codex.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 2: App Shell and Navigation

Scope:

1. Add route definitions.
2. Add Navigation Compose app host.
3. Add placeholder screens for the five pages.
4. Replace the generated greeting.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 3: Domain Layer

Scope:

1. Add domain models.
2. Add repository interfaces.
3. Add use cases for goals, suggestions, and growth metrics.
4. Add unit tests for use cases and metric edge cases.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 4: Local Persistence

Scope:

1. Add Room entities.
2. Add DAO interfaces.
3. Add database class.
4. Add repository implementations.
5. Add persistence tests for externally observable repository behavior.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 5: Analytics Layer

Scope:

1. Add AnalyticsTracker interface.
2. Add Firebase implementation.
3. Add fake tracker for tests.
4. Track onboarding, habit setup, home, and progress actions.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 6: Remote Config Layer

Scope:

1. Add ExperimentConfigProvider interface.
2. Add Firebase implementation.
3. Add defaults for every experiment key.
4. Track experiment exposure.
5. Add tests for variant behavior.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

### Round 7: Full UI Flows

Scope:

1. Implement Onboarding.
2. Implement Habit Setup.
3. Implement Home.
4. Implement Progress.
5. Implement Analytics Lab.

Verification:

```text
./gradlew.bat testDebugUnitTest
./gradlew.bat connectedDebugAndroidTest
```

Connected tests require an available emulator or device.

### Round 8: Firebase QA Documentation

Scope:

1. Add DebugView checklist.
2. Add Remote Config experiment setup guide.
3. Add growth funnel interpretation notes.

Verification:

```text
./gradlew.bat testDebugUnitTest
```

