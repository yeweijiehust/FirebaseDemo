# Replace Generated Tests

Round 14 removes starter-template tests and replaces them with meaningful coverage.

## Why This Exists

Generated sample tests are useful only at project creation time.

Once the app has real behavior, tests should describe product or architecture guarantees instead of proving that arithmetic works or that the package name is correct.

## What Changed

This round removes:

```text
ExampleUnitTest
ExampleInstrumentedTest
```

It adds an instrumented Room DAO test for observable persistence behavior:

```text
saveActiveHabit replaces the previous active habit
resetJourney clears selected goal, active habit, and habit logs
```

## Verification

Unit tests still run with:

```text
./gradlew.bat testDebugUnitTest
```

Android test sources compile with:

```text
./gradlew.bat assembleDebugAndroidTest
```

Running the instrumented test still requires an emulator or device.
