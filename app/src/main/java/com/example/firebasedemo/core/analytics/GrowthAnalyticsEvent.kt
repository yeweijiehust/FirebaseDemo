package com.example.firebasedemo.core.analytics

object GrowthAnalyticsEvent {
    fun appOpened(source: String = Source.APP): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.APP_OPENED,
            parameters = mapOf(Parameter.SOURCE to source)
        )
    }

    fun screenViewed(screenName: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.SCREEN_VIEWED,
            parameters = mapOf(Parameter.SCREEN_NAME to screenName)
        )
    }

    fun onboardingStarted(variant: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.ONBOARDING_STARTED,
            parameters = mapOf(Parameter.VARIANT to variant)
        )
    }

    fun onboardingGoalSelected(goalId: String, variant: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.ONBOARDING_GOAL_SELECTED,
            parameters = mapOf(
                Parameter.GOAL_ID to goalId,
                Parameter.VARIANT to variant
            )
        )
    }

    fun onboardingCompleted(goalId: String, variant: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.ONBOARDING_COMPLETED,
            parameters = mapOf(
                Parameter.GOAL_ID to goalId,
                Parameter.VARIANT to variant
            )
        )
    }

    fun habitSuggestedSelected(
        habitId: String,
        habitCategory: String,
        source: String,
        variant: String
    ): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.HABIT_SUGGESTED_SELECTED,
            parameters = mapOf(
                Parameter.HABIT_ID to habitId,
                Parameter.HABIT_CATEGORY to habitCategory,
                Parameter.SOURCE to source,
                Parameter.VARIANT to variant
            )
        )
    }

    fun habitCreated(
        habitId: String,
        habitCategory: String,
        source: String
    ): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.HABIT_CREATED,
            parameters = mapOf(
                Parameter.HABIT_ID to habitId,
                Parameter.HABIT_CATEGORY to habitCategory,
                Parameter.SOURCE to source
            )
        )
    }

    fun habitLogged(
        habitId: String,
        habitCategory: String,
        streakCount: Int,
        completionCount: Int
    ): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.HABIT_LOGGED,
            parameters = mapOf(
                Parameter.HABIT_ID to habitId,
                Parameter.HABIT_CATEGORY to habitCategory,
                Parameter.STREAK_COUNT to streakCount.toString(),
                Parameter.COMPLETION_COUNT to completionCount.toString()
            )
        )
    }

    fun progressViewed(
        streakCount: Int,
        completionCount: Int,
        activationStatus: String
    ): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.PROGRESS_VIEWED,
            parameters = mapOf(
                Parameter.STREAK_COUNT to streakCount.toString(),
                Parameter.COMPLETION_COUNT to completionCount.toString(),
                Parameter.ACTIVATION_STATUS to activationStatus
            )
        )
    }

    fun experimentExposed(
        experimentKey: String,
        variant: String,
        screenName: String
    ): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.EXPERIMENT_EXPOSED,
            parameters = mapOf(
                Parameter.EXPERIMENT_KEY to experimentKey,
                Parameter.VARIANT to variant,
                Parameter.SCREEN_NAME to screenName
            )
        )
    }

    fun remoteConfigActivated(source: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.REMOTE_CONFIG_ACTIVATED,
            parameters = mapOf(Parameter.SOURCE to source)
        )
    }

    fun remoteConfigFetchFailed(errorType: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.REMOTE_CONFIG_FETCH_FAILED,
            parameters = mapOf(Parameter.ERROR_TYPE to errorType)
        )
    }

    fun learningJourneyReset(source: String): AnalyticsEvent {
        return AnalyticsEvent(
            name = Name.LEARNING_JOURNEY_RESET,
            parameters = mapOf(Parameter.SOURCE to source)
        )
    }

    object Name {
        const val APP_OPENED = "app_opened"
        const val SCREEN_VIEWED = "screen_viewed"
        const val ONBOARDING_STARTED = "onboarding_started"
        const val ONBOARDING_GOAL_SELECTED = "onboarding_goal_selected"
        const val ONBOARDING_COMPLETED = "onboarding_completed"
        const val HABIT_SUGGESTED_SELECTED = "habit_suggested_selected"
        const val HABIT_CREATED = "habit_created"
        const val HABIT_LOGGED = "habit_logged"
        const val PROGRESS_VIEWED = "progress_viewed"
        const val EXPERIMENT_EXPOSED = "experiment_exposed"
        const val REMOTE_CONFIG_ACTIVATED = "remote_config_activated"
        const val REMOTE_CONFIG_FETCH_FAILED = "remote_config_fetch_failed"
        const val LEARNING_JOURNEY_RESET = "learning_journey_reset"
    }

    object Parameter {
        const val ACTIVATION_STATUS = "activation_status"
        const val COMPLETION_COUNT = "completion_count"
        const val ERROR_TYPE = "error_type"
        const val EXPERIMENT_KEY = "experiment_key"
        const val GOAL_ID = "goal_id"
        const val HABIT_CATEGORY = "habit_category"
        const val HABIT_ID = "habit_id"
        const val SCREEN_NAME = "screen_name"
        const val SOURCE = "source"
        const val STREAK_COUNT = "streak_count"
        const val VARIANT = "variant"
    }

    object Source {
        const val APP = "app"
    }
}
