package com.example.firebasedemo.domain.model

enum class OnboardingGoalId(val analyticsValue: String) {
    BUILD_CONSISTENCY("build_consistency"),
    IMPROVE_FOCUS("improve_focus"),
    LEARN_DAILY("learn_daily"),
    MOVE_MORE("move_more");

    companion object {
        fun fromAnalyticsValue(value: String): OnboardingGoalId? {
            return entries.firstOrNull { it.analyticsValue == value }
        }
    }
}
