# Analytics Lab Reference

Round 11 turns Analytics Lab into a stronger in-app reference.

## Why This Exists

The learning loop works best when the app itself explains what to look for in Firebase.

Before this round, Analytics Lab summarized the funnel and experiment surfaces. Now it also shows the concrete event catalog and Remote Config experiment catalog.

## Event Catalog

Analytics Lab lists each stable event name with:

```text
when the event fires
event parameters
```

This helps compare app behavior with Firebase DebugView without jumping between code and docs.

## Experiment Catalog

Analytics Lab lists each Remote Config key with:

```text
surface
variants
```

This keeps assignment, exposure, and behavior easier to reason about during repeated funnel practice.

## Testing

The reference content is backed by plain Kotlin models so unit tests can verify that:

```text
the event catalog matches GrowthAnalyticsEvent names
the experiment catalog matches ExperimentKey values
each event exposes at least one parameter
```

