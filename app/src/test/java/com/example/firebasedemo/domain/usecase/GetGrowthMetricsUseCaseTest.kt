package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.ActivationStatus
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetGrowthMetricsUseCaseTest {
    private val today = LocalDate.of(2026, 6, 2)
    private val repository = FakeGrowthRepository()
    private val useCase = GetGrowthMetricsUseCase(
        repository = repository,
        calculateGrowthMetrics = CalculateGrowthMetricsUseCase()
    )

    @Test
    fun returnsNullWhenNoHabitExists() = runBlocking {
        assertNull(useCase(today))
    }

    @Test
    fun returnsInactiveMetricsWhenHabitExistsWithoutGoal() = runBlocking {
        repository.habit = habit
        repository.logs = listOf(HabitLog(habit.id, today))

        val metrics = requireNotNull(useCase(today))

        assertEquals(1, metrics.totalCompletions)
        assertEquals(ActivationStatus.INACTIVE, metrics.activationStatus)
    }

    @Test
    fun returnsActivatedMetricsWhenGoalAndHabitLogsExist() = runBlocking {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS
        repository.habit = habit
        repository.logs = listOf(
            HabitLog(habit.id, today.minusDays(1)),
            HabitLog(habit.id, today)
        )

        val metrics = requireNotNull(useCase(today))

        assertEquals(2, metrics.totalCompletions)
        assertEquals(2, metrics.currentStreak)
        assertEquals(ActivationStatus.ACTIVATED, metrics.activationStatus)
    }

    private class FakeGrowthRepository : GrowthRepository {
        var goalId: OnboardingGoalId? = null
        var habit: Habit? = null
        var logs: List<HabitLog> = emptyList()

        override suspend fun selectedGoalId(): OnboardingGoalId? {
            return goalId
        }

        override suspend fun saveSelectedGoal(goalId: OnboardingGoalId) {
            this.goalId = goalId
        }

        override suspend fun activeHabit(): Habit? {
            return habit
        }

        override suspend fun saveActiveHabit(habit: Habit) {
            this.habit = habit
        }

        override suspend fun habitLogs(habitId: String): List<HabitLog> {
            return logs.filter { it.habitId == habitId }
        }

        override suspend fun logHabit(habitId: String, date: LocalDate): HabitLogResult {
            return HabitLogResult.Logged(HabitLog(habitId, date))
        }

        override suspend fun resetJourney() {
            goalId = null
            habit = null
            logs = emptyList()
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
