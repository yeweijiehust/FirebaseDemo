package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.navigation.GrowthHabitDestination
import javax.inject.Inject

class ExperimentExposureTracker @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) {
    private val exposedExperiments = mutableSetOf<String>()

    fun expose(
        destination: GrowthHabitDestination,
        config: GrowthExperimentConfig
    ) {
        when (destination) {
            GrowthHabitDestination.Onboarding -> expose(
                experimentKey = ExperimentKey.ONBOARDING_VARIANT,
                variant = config.onboardingVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.HabitSetup -> expose(
                experimentKey = ExperimentKey.SUGGESTED_HABIT_VARIANT,
                variant = config.suggestedHabitVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.Home -> expose(
                experimentKey = ExperimentKey.HOME_HEADLINE_VARIANT,
                variant = config.homeHeadlineVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.AnalyticsLab,
            GrowthHabitDestination.Progress -> Unit
        }
    }

    private fun expose(
        experimentKey: String,
        variant: String,
        screenName: String
    ) {
        val exposureId = "$experimentKey:$screenName"
        if (!exposedExperiments.add(exposureId)) {
            return
        }

        analyticsTracker.track(
            GrowthAnalyticsEvent.experimentExposed(
                experimentKey = experimentKey,
                variant = variant,
                screenName = screenName
            )
        )
    }
}
