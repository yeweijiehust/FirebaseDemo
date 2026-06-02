package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.core.analytics.AnalyticsEvent
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant
import com.example.firebasedemo.navigation.GrowthHabitDestination
import org.junit.Assert.assertEquals
import org.junit.Test

class ExperimentExposureTrackerTest {
    private val analyticsTracker = RecordingAnalyticsTracker()
    private val exposureTracker = ExperimentExposureTracker(analyticsTracker)
    private val config = GrowthExperimentConfig(
        onboardingVariant = OnboardingVariant.GUIDED,
        suggestedHabitVariant = HabitSuggestionVariant.PERSONALIZED,
        homeHeadlineVariant = HomeHeadlineVariant.STREAK,
        analyticsLabEnabled = true
    )

    @Test
    fun onboardingExposureUsesConfiguredSnapshot() {
        exposureTracker.expose(GrowthHabitDestination.Onboarding, config)

        val event = analyticsTracker.events.single()
        assertEquals("experiment_exposed", event.name)
        assertEquals(ExperimentKey.ONBOARDING_VARIANT, event.parameters["experiment_key"])
        assertEquals("guided", event.parameters["variant"])
        assertEquals("onboarding", event.parameters["screen_name"])
    }

    @Test
    fun sameScreenExposureIsDeduplicated() {
        exposureTracker.expose(GrowthHabitDestination.Home, config)
        exposureTracker.expose(GrowthHabitDestination.Home, config)

        assertEquals(1, analyticsTracker.events.size)
        assertEquals(ExperimentKey.HOME_HEADLINE_VARIANT, analyticsTracker.events.single().parameters["experiment_key"])
    }

    @Test
    fun progressHasNoExperimentExposure() {
        exposureTracker.expose(GrowthHabitDestination.Progress, config)

        assertEquals(emptyList<AnalyticsEvent>(), analyticsTracker.events)
    }

    private class RecordingAnalyticsTracker : AnalyticsTracker {
        val events = mutableListOf<AnalyticsEvent>()
        val userProperties = mutableMapOf<String, String?>()

        override fun track(event: AnalyticsEvent) {
            events += event
        }

        override fun setUserProperty(name: String, value: String?) {
            userProperties[name] = value
        }
    }
}
