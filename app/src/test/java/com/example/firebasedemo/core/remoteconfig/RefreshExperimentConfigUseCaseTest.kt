package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class RefreshExperimentConfigUseCaseTest {
    @Test
    fun returnsConfigAfterRefreshCompletes() = runBlocking {
        val provider = FakeExperimentConfigProvider(
            initialConfig = RemoteConfigDefaults.growthExperimentConfig,
            refreshedConfig = GrowthExperimentConfig(
                onboardingVariant = OnboardingVariant.GUIDED,
                suggestedHabitVariant = HabitSuggestionVariant.PERSONALIZED,
                homeHeadlineVariant = HomeHeadlineVariant.STREAK,
                analyticsLabEnabled = false
            ),
            refreshResult = RemoteConfigRefreshResult.Activated
        )
        val useCase = RefreshExperimentConfigUseCase(provider)

        val assignment = useCase()

        assertEquals(RemoteConfigRefreshResult.Activated, assignment.refreshResult)
        assertEquals(OnboardingVariant.GUIDED, assignment.config.onboardingVariant)
        assertEquals(HabitSuggestionVariant.PERSONALIZED, assignment.config.suggestedHabitVariant)
        assertEquals(HomeHeadlineVariant.STREAK, assignment.config.homeHeadlineVariant)
        assertEquals(false, assignment.config.analyticsLabEnabled)
    }

    @Test
    fun returnsCurrentConfigWhenRefreshFails() = runBlocking {
        val initialConfig = RemoteConfigDefaults.growthExperimentConfig
        val provider = FakeExperimentConfigProvider(
            initialConfig = initialConfig,
            refreshedConfig = initialConfig,
            refreshResult = RemoteConfigRefreshResult.Failed("IllegalStateException")
        )
        val useCase = RefreshExperimentConfigUseCase(provider)

        val assignment = useCase()

        assertEquals(RemoteConfigRefreshResult.Failed("IllegalStateException"), assignment.refreshResult)
        assertEquals(initialConfig, assignment.config)
    }

    private class FakeExperimentConfigProvider(
        initialConfig: GrowthExperimentConfig,
        private val refreshedConfig: GrowthExperimentConfig,
        private val refreshResult: RemoteConfigRefreshResult
    ) : ExperimentConfigProvider {
        private var config = initialConfig

        override fun currentConfig(): GrowthExperimentConfig {
            return config
        }

        override suspend fun refresh(): RemoteConfigRefreshResult {
            config = refreshedConfig
            return refreshResult
        }
    }
}
