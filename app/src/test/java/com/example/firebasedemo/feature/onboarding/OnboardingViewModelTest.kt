package com.example.firebasedemo.feature.onboarding

import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.usecase.GetOnboardingGoalsUseCase
import com.example.firebasedemo.domain.usecase.SaveSelectedGoalUseCase
import com.example.firebasedemo.testing.FakeExperimentConfigProvider
import com.example.firebasedemo.testing.FakeGrowthRepository
import com.example.firebasedemo.testing.MainDispatcherRule
import com.example.firebasedemo.testing.RecordingAnalyticsTracker
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class OnboardingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeGrowthRepository()
    private val analyticsTracker = RecordingAnalyticsTracker()

    @Test
    fun selectionTracksGoalAndUserProperties() {
        val viewModel = viewModel()

        viewModel.selectGoal(OnboardingGoalId.IMPROVE_FOCUS)

        assertEquals(OnboardingGoalId.IMPROVE_FOCUS, viewModel.uiState.value.selectedGoalId)
        assertEquals(
            listOf("onboarding_started", "onboarding_goal_selected"),
            analyticsTracker.events.map { it.name }
        )
        assertEquals(
            "improve_focus",
            analyticsTracker.events.last().parameters["goal_id"]
        )
        assertEquals(
            "improve_focus",
            analyticsTracker.userProperties[GrowthUserProperty.ONBOARDING_GOAL]
        )
        assertEquals(
            "control",
            analyticsTracker.userProperties[GrowthUserProperty.ONBOARDING_VARIANT]
        )
    }

    @Test
    fun continueWithSelectedGoalPersistsAndTracksCompletion() = runTest {
        val viewModel = viewModel()
        var completed = false

        viewModel.selectGoal(OnboardingGoalId.LEARN_DAILY)
        viewModel.continueWithSelectedGoal {
            completed = true
        }

        assertEquals(OnboardingGoalId.LEARN_DAILY, repository.goalId)
        assertTrue(completed)
        assertFalse(viewModel.uiState.value.isSaving)
        assertEquals("onboarding_completed", analyticsTracker.events.last().name)
        assertEquals("learn_daily", analyticsTracker.events.last().parameters["goal_id"])
    }

    private fun viewModel(): OnboardingViewModel {
        return OnboardingViewModel(
            getOnboardingGoals = GetOnboardingGoalsUseCase(),
            configProvider = FakeExperimentConfigProvider(),
            saveSelectedGoal = SaveSelectedGoalUseCase(repository),
            analyticsTracker = analyticsTracker
        )
    }
}
