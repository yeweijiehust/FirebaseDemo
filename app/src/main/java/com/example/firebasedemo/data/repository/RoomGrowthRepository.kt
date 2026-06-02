package com.example.firebasedemo.data.repository

import com.example.firebasedemo.data.local.source.GrowthLocalDataSource
import com.example.firebasedemo.data.mapper.GrowthEntityMapper
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate
import javax.inject.Inject

class RoomGrowthRepository @Inject constructor(
    private val localDataSource: GrowthLocalDataSource,
    private val mapper: GrowthEntityMapper
) : GrowthRepository {
    override suspend fun selectedGoalId(): OnboardingGoalId? {
        return localDataSource.selectedGoal()?.let(mapper::toGoalId)
    }

    override suspend fun saveSelectedGoal(goalId: OnboardingGoalId) {
        localDataSource.saveSelectedGoal(mapper.toOnboardingStateEntity(goalId))
    }

    override suspend fun activeHabit(): Habit? {
        return localDataSource.activeHabit()?.let(mapper::toHabit)
    }

    override suspend fun saveActiveHabit(habit: Habit) {
        localDataSource.saveActiveHabit(mapper.toHabitEntity(habit, isActive = true))
    }

    override suspend fun habitLogs(habitId: String): List<HabitLog> {
        return localDataSource.habitLogs(habitId).map(mapper::toHabitLog)
    }

    override suspend fun logHabit(habitId: String, date: LocalDate): HabitLogResult {
        val entity = mapper.toHabitLogEntity(habitId, date)
        val habitLog = mapper.toHabitLog(entity)

        return if (localDataSource.insertHabitLog(entity)) {
            HabitLogResult.Logged(habitLog)
        } else {
            HabitLogResult.AlreadyLogged(habitLog)
        }
    }

    override suspend fun resetJourney() {
        localDataSource.resetJourney()
    }
}
