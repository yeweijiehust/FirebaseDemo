package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.JourneyStartPoint
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetJourneyStartPointUseCaseTest {
    private val repository = FakeGrowthRepository()
    private val useCase = GetJourneyStartPointUseCase(repository)

    @Test
    fun startsAtOnboardingWhenNoJourneyStateExists() = runBlocking {
        assertEquals(JourneyStartPoint.ONBOARDING, useCase())
    }

    @Test
    fun startsAtHabitSetupWhenGoalExistsWithoutHabit() = runBlocking {
        repository.goalId = OnboardingGoalId.IMPROVE_FOCUS

        assertEquals(JourneyStartPoint.HABIT_SETUP, useCase())
    }

    @Test
    fun startsAtHomeWhenActiveHabitExists() = runBlocking {
        repository.habit = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )

        assertEquals(JourneyStartPoint.HOME, useCase())
    }

    private class FakeGrowthRepository : GrowthRepository {
        var goalId: OnboardingGoalId? = null
        var habit: Habit? = null

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
            return emptyList()
        }

        override suspend fun logHabit(habitId: String, date: LocalDate): HabitLogResult {
            return HabitLogResult.Logged(HabitLog(habitId, date))
        }

        override suspend fun resetJourney() {
            goalId = null
            habit = null
        }
    }
}
