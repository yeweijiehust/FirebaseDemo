package com.example.firebasedemo.data.local.source

import com.example.firebasedemo.data.local.dao.GrowthDao
import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity
import javax.inject.Inject

class RoomGrowthLocalDataSource @Inject constructor(
    private val growthDao: GrowthDao
) : GrowthLocalDataSource {
    override suspend fun selectedGoal(): OnboardingStateEntity? {
        return growthDao.selectedGoal()
    }

    override suspend fun saveSelectedGoal(entity: OnboardingStateEntity) {
        growthDao.saveSelectedGoal(entity)
    }

    override suspend fun activeHabit(): HabitEntity? {
        return growthDao.activeHabit()
    }

    override suspend fun saveActiveHabit(entity: HabitEntity) {
        growthDao.saveActiveHabit(entity)
    }

    override suspend fun habitLogs(habitId: String): List<HabitLogEntity> {
        return growthDao.habitLogs(habitId)
    }

    override suspend fun insertHabitLog(entity: HabitLogEntity): Boolean {
        return growthDao.insertHabitLog(entity) != -1L
    }
}
