package com.example.firebasedemo

import com.example.firebasedemo.core.analytics.AnalyticsEvent
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.remoteconfig.ExperimentKey
import com.example.firebasedemo.core.remoteconfig.GrowthExperimentConfig
import com.example.firebasedemo.core.remoteconfig.RemoteConfigRefreshResult
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant
import com.example.firebasedemo.navigation.GrowthHabitDestination
import org.junit.Assert.assertEquals
import org.junit.Test

class AppExperimentViewModelTest {
    private val analyticsTracker = RecordingAnalyticsTracker()
    private val configProvider = FakeExperimentConfigProvider(
        config = GrowthExperimentConfig(
            onboardingVariant = OnboardingVariant.GUIDED,
            suggestedHabitVariant = HabitSuggestionVariant.PERSONALIZED,
            homeHeadlineVariant = HomeHeadlineVariant.STREAK,
            analyticsLabEnabled = true
        )
    )

    @Test
    fun onboardingExposureUsesConfiguredVariant() {
        val viewModel = AppExperimentViewModel(configProvider, analyticsTracker)

        viewModel.exposeScreenExperiments(GrowthHabitDestination.Onboarding)

        val event = analyticsTracker.events.single()
        assertEquals("experiment_exposed", event.name)
        assertEquals(ExperimentKey.ONBOARDING_VARIANT, event.parameters["experiment_key"])
        assertEquals("guided", event.parameters["variant"])
        assertEquals("onboarding", event.parameters["screen_name"])
    }

    @Test
    fun repeatedExposureForSameScreenAndExperimentIsDeduplicated() {
        val viewModel = AppExperimentViewModel(configProvider, analyticsTracker)

        viewModel.exposeScreenExperiments(GrowthHabitDestination.Home)
        viewModel.exposeScreenExperiments(GrowthHabitDestination.Home)

        assertEquals(1, analyticsTracker.events.size)
        assertEquals(ExperimentKey.HOME_HEADLINE_VARIANT, analyticsTracker.events.single().parameters["experiment_key"])
    }

    @Test
    fun progressHasNoExperimentExposure() {
        val viewModel = AppExperimentViewModel(configProvider, analyticsTracker)

        viewModel.exposeScreenExperiments(GrowthHabitDestination.Progress)

        assertEquals(emptyList<AnalyticsEvent>(), analyticsTracker.events)
    }

    private class FakeExperimentConfigProvider(
        private val config: GrowthExperimentConfig
    ) : ExperimentConfigProvider {
        override fun currentConfig(): GrowthExperimentConfig {
            return config
        }

        override suspend fun refresh(): RemoteConfigRefreshResult {
            return RemoteConfigRefreshResult.Unchanged
        }
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
