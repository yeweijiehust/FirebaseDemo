package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant

object RemoteConfigDefaults {
    val growthExperimentConfig = GrowthExperimentConfig(
        onboardingVariant = OnboardingVariant.CONTROL,
        suggestedHabitVariant = HabitSuggestionVariant.POPULAR,
        homeHeadlineVariant = HomeHeadlineVariant.PROGRESS,
        analyticsLabEnabled = true
    )

    val firebaseDefaults: Map<String, Any> = mapOf(
        ExperimentKey.ONBOARDING_VARIANT to growthExperimentConfig.onboardingVariant.remoteConfigValue,
        ExperimentKey.SUGGESTED_HABIT_VARIANT to growthExperimentConfig.suggestedHabitVariant.remoteConfigValue,
        ExperimentKey.HOME_HEADLINE_VARIANT to growthExperimentConfig.homeHeadlineVariant.remoteConfigValue,
        ExperimentKey.ANALYTICS_LAB_ENABLED to growthExperimentConfig.analyticsLabEnabled
    )
}
