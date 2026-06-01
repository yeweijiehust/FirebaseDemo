package com.example.firebasedemo.data.local.source

import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity

interface GrowthLocalDataSource {
    suspend fun selectedGoal(): OnboardingStateEntity?
    suspend fun saveSelectedGoal(entity: OnboardingStateEntity)
    suspend fun activeHabit(): HabitEntity?
    suspend fun saveActiveHabit(entity: HabitEntity)
    suspend fun habitLogs(habitId: String): List<HabitLogEntity>
    suspend fun insertHabitLog(entity: HabitLogEntity): Boolean
}
