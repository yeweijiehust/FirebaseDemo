package com.example.firebasedemo.domain.repository

import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.OnboardingGoalId
import java.time.LocalDate

interface GrowthRepository {
    suspend fun selectedGoalId(): OnboardingGoalId?
    suspend fun saveSelectedGoal(goalId: OnboardingGoalId)
    suspend fun activeHabit(): Habit?
    suspend fun saveActiveHabit(habit: Habit)
    suspend fun habitLogs(habitId: String): List<HabitLog>
    suspend fun logHabit(habitId: String, date: LocalDate): HabitLogResult
}
