package com.example.firebasedemo.feature.analyticslab

import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.remoteconfig.ExperimentKey

data class AnalyticsEventReference(
    val name: String,
    val whenItFires: String,
    val parameters: List<String>
)

data class ExperimentReference(
    val key: String,
    val surface: String,
    val variants: List<String>
)

object AnalyticsLabReference {
    val eventCatalog = listOf(
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.APP_OPENED,
            whenItFires = "App content is first shown.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.SOURCE)
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.SCREEN_VIEWED,
            whenItFires = "A top-level screen becomes visible.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.SCREEN_NAME)
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.ONBOARDING_STARTED,
            whenItFires = "Onboarding is first shown.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.VARIANT)
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.ONBOARDING_GOAL_SELECTED,
            whenItFires = "The user selects a growth goal.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.GOAL_ID,
                GrowthAnalyticsEvent.Parameter.VARIANT
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.ONBOARDING_COMPLETED,
            whenItFires = "The user finishes onboarding.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.GOAL_ID,
                GrowthAnalyticsEvent.Parameter.VARIANT
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.HABIT_SUGGESTED_SELECTED,
            whenItFires = "The user selects a suggested habit.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.HABIT_ID,
                GrowthAnalyticsEvent.Parameter.HABIT_CATEGORY,
                GrowthAnalyticsEvent.Parameter.SOURCE,
                GrowthAnalyticsEvent.Parameter.VARIANT
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.HABIT_CREATED,
            whenItFires = "The active habit is saved.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.HABIT_ID,
                GrowthAnalyticsEvent.Parameter.HABIT_CATEGORY,
                GrowthAnalyticsEvent.Parameter.SOURCE
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.HABIT_LOGGED,
            whenItFires = "The user logs a habit completion.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.HABIT_ID,
                GrowthAnalyticsEvent.Parameter.HABIT_CATEGORY,
                GrowthAnalyticsEvent.Parameter.STREAK_COUNT,
                GrowthAnalyticsEvent.Parameter.COMPLETION_COUNT
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.PROGRESS_VIEWED,
            whenItFires = "The user opens Progress.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.STREAK_COUNT,
                GrowthAnalyticsEvent.Parameter.COMPLETION_COUNT,
                GrowthAnalyticsEvent.Parameter.ACTIVATION_STATUS
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.EXPERIMENT_EXPOSED,
            whenItFires = "The user sees an experiment-controlled surface.",
            parameters = listOf(
                GrowthAnalyticsEvent.Parameter.EXPERIMENT_KEY,
                GrowthAnalyticsEvent.Parameter.VARIANT,
                GrowthAnalyticsEvent.Parameter.SCREEN_NAME
            )
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.REMOTE_CONFIG_ACTIVATED,
            whenItFires = "Fetched Remote Config values become active.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.SOURCE)
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.REMOTE_CONFIG_FETCH_FAILED,
            whenItFires = "Remote Config fetch fails.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.ERROR_TYPE)
        ),
        AnalyticsEventReference(
            name = GrowthAnalyticsEvent.Name.LEARNING_JOURNEY_RESET,
            whenItFires = "The local learning journey is reset from Analytics Lab.",
            parameters = listOf(GrowthAnalyticsEvent.Parameter.SOURCE)
        )
    )

    val experimentCatalog = listOf(
        ExperimentReference(
            key = ExperimentKey.ONBOARDING_VARIANT,
            surface = "Onboarding",
            variants = listOf("control", "guided")
        ),
        ExperimentReference(
            key = ExperimentKey.SUGGESTED_HABIT_VARIANT,
            surface = "Habit Setup",
            variants = listOf("popular", "personalized")
        ),
        ExperimentReference(
            key = ExperimentKey.HOME_HEADLINE_VARIANT,
            surface = "Home",
            variants = listOf("progress", "streak")
        ),
        ExperimentReference(
            key = ExperimentKey.ANALYTICS_LAB_ENABLED,
            surface = "Home Analytics Lab entry point",
            variants = listOf("true", "false")
        )
    )
}
