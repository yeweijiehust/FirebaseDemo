package com.example.firebasedemo.feature.analyticslab

import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.usecase.ResetLearningJourneyUseCase
import com.example.firebasedemo.testing.FakeGrowthRepository
import com.example.firebasedemo.testing.MainDispatcherRule
import com.example.firebasedemo.testing.RecordingAnalyticsTracker
import java.time.LocalDate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class AnalyticsLabViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeGrowthRepository()
    private val analyticsTracker = RecordingAnalyticsTracker()

    @Test
    fun resetJourneyClearsLocalStateTracksEventAndClearsProperties() = runTest {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS
        repository.habit = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )
        repository.logs = listOf(HabitLog("focus_sprint", LocalDate.of(2026, 6, 2)))
        val viewModel = viewModel()
        var completed = false

        viewModel.resetJourney {
            completed = true
        }

        assertNull(repository.goalId)
        assertNull(repository.habit)
        assertEquals(emptyList<HabitLog>(), repository.logs)
        assertTrue(completed)
        assertFalse(viewModel.uiState.value.isResetting)
        assertEquals(listOf("learning_journey_reset"), analyticsTracker.events.map { it.name })
        assertEquals("analytics_lab", analyticsTracker.events.single().parameters["source"])
        assertTrue(analyticsTracker.userProperties.containsKey(GrowthUserProperty.ONBOARDING_GOAL))
        assertTrue(analyticsTracker.userProperties.containsKey(GrowthUserProperty.ACTIVATION_STATUS))
        assertNull(analyticsTracker.userProperties[GrowthUserProperty.ONBOARDING_GOAL])
        assertNull(analyticsTracker.userProperties[GrowthUserProperty.ACTIVATION_STATUS])
    }

    private fun viewModel(): AnalyticsLabViewModel {
        return AnalyticsLabViewModel(
            resetLearningJourney = ResetLearningJourneyUseCase(repository),
            analyticsTracker = analyticsTracker
        )
    }
}
