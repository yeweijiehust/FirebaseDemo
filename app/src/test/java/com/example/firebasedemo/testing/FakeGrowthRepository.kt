package com.example.firebasedemo.testing

import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate

class FakeGrowthRepository : GrowthRepository {
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
        val existingLog = logs.firstOrNull {
            it.habitId == habitId && it.loggedDate == date
        }
        if (existingLog != null) {
            return HabitLogResult.AlreadyLogged(existingLog)
        }

        val habitLog = HabitLog(habitId, date)
        logs += habitLog
        return HabitLogResult.Logged(habitLog)
    }

    override suspend fun resetJourney() {
        goalId = null
        habit = null
        logs = emptyList()
    }
}
