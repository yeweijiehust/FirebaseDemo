# Growth Habit Lab Documentation

This documentation supports the Android learning project in this repository. The goal is to learn growth analytics, Firebase Analytics, Remote Config, and A/B testing by building a small real-world style habit app.

## Reading Order

1. [Project Map](00-project-map.md)
2. [Implementation Plan](01-implementation-plan.md)
3. [Analytics Taxonomy](02-analytics-taxonomy.md)
4. [App Shell And Navigation](03-app-shell-navigation.md)
5. [Domain Layer](04-domain-layer.md)
6. [Local Persistence](05-local-persistence.md)
7. [Analytics Layer](06-analytics-layer.md)
8. [Remote Config Layer](07-remote-config-layer.md)
9. [Full UI Flow](08-full-ui-flow.md)
10. [Firebase DebugView Checklist](09-debugview-checklist.md)
11. [A/B Test Playbook](10-ab-test-playbook.md)
12. [Growth Funnel Analysis](11-growth-funnel-analysis.md)

## Product Direction

The app will become Growth Habit Lab, a five-page habit tracking experience designed around measurable growth loops:

1. Onboarding
2. Habit Setup
3. Home
4. Progress
5. Analytics Lab

Each feature will be implemented in small reviewable rounds. Every round should preserve the Clean Architecture boundary, include tests for externally observable behavior, and avoid source code comments.

## Workflow

Each development round follows this sequence:

1. Plan the slice.
2. Implement the slice.
3. Run relevant tests.
4. Review changes.
5. Commit only after approval.
6. Merge and switch to the next branch after approval.
