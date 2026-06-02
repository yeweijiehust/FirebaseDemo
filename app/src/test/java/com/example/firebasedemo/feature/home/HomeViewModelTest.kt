package com.example.firebasedemo.feature.home

import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.core.time.TodayProvider
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.usecase.CalculateGrowthMetricsUseCase
import com.example.firebasedemo.domain.usecase.GetActiveHabitUseCase
import com.example.firebasedemo.domain.usecase.GetGrowthMetricsUseCase
import com.example.firebasedemo.domain.usecase.GetSelectedGoalUseCase
import com.example.firebasedemo.domain.usecase.LogHabitUseCase
import com.example.firebasedemo.testing.FakeExperimentConfigProvider
import com.example.firebasedemo.testing.FakeGrowthRepository
import com.example.firebasedemo.testing.MainDispatcherRule
import com.example.firebasedemo.testing.RecordingAnalyticsTracker
import java.time.LocalDate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val today = LocalDate.of(2026, 6, 2)
    private val repository = FakeGrowthRepository()
    private val analyticsTracker = RecordingAnalyticsTracker()

    @Test
    fun refreshShowsSavedJourneyAndActivationProperty() {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS
        repository.habit = habit
        repository.logs = listOf(HabitLog(habit.id, today))

        val viewModel = viewModel()

        assertEquals(OnboardingGoalId.IMPROVE_FOCUS, viewModel.uiState.value.selectedGoalId)
        assertEquals(habit, viewModel.uiState.value.activeHabit)
        assertTrue(viewModel.uiState.value.loggedToday)
        assertEquals(
            "activated",
            analyticsTracker.userProperties[GrowthUserProperty.ACTIVATION_STATUS]
        )
    }

    @Test
    fun logTodayTracksOnlyNewCompletions() = runTest {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS
        repository.habit = habit
        val viewModel = viewModel()

        viewModel.logToday()

        assertEquals(listOf("habit_logged"), analyticsTracker.events.map { it.name })
        assertEquals("Habit logged for today", viewModel.uiState.value.message)
        assertEquals(1, viewModel.uiState.value.metrics?.totalCompletions)
    }

    @Test
    fun duplicateLogDoesNotTrackHabitLoggedAgain() = runTest {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS
        repository.habit = habit
        repository.logs = listOf(HabitLog(habit.id, today))
        val viewModel = viewModel()

        viewModel.logToday()

        assertEquals(emptyList<String>(), analyticsTracker.events.map { it.name })
        assertEquals("Today is already logged", viewModel.uiState.value.message)
        assertTrue(viewModel.uiState.value.loggedToday)
    }

    private fun viewModel(): HomeViewModel {
        return HomeViewModel(
            configProvider = FakeExperimentConfigProvider(),
            todayProvider = FakeTodayProvider(today),
            getSelectedGoal = GetSelectedGoalUseCase(repository),
            getActiveHabit = GetActiveHabitUseCase(repository),
            getGrowthMetrics = GetGrowthMetricsUseCase(
                repository = repository,
                calculateGrowthMetrics = CalculateGrowthMetricsUseCase()
            ),
            logHabit = LogHabitUseCase(repository),
            analyticsTracker = analyticsTracker
        )
    }

    private class FakeTodayProvider(
        private val today: LocalDate
    ) : TodayProvider {
        override fun today(): LocalDate {
            return today
        }
    }

    private companion object {
        val habit = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )
    }
}
