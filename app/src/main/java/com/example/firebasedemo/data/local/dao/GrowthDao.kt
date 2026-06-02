package com.example.firebasedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity

@Dao
interface GrowthDao {
    @Query("SELECT * FROM onboarding_state WHERE id = 0 LIMIT 1")
    suspend fun selectedGoal(): OnboardingStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSelectedGoal(entity: OnboardingStateEntity)

    @Query("SELECT * FROM habits WHERE isActive = 1 LIMIT 1")
    suspend fun activeHabit(): HabitEntity?

    @Query("UPDATE habits SET isActive = 0 WHERE isActive = 1")
    suspend fun clearActiveHabit()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHabit(entity: HabitEntity)

    @Transaction
    suspend fun saveActiveHabit(entity: HabitEntity) {
        clearActiveHabit()
        upsertHabit(entity)
    }

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY loggedDate ASC")
    suspend fun habitLogs(habitId: String): List<HabitLogEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHabitLog(entity: HabitLogEntity): Long

    @Query("DELETE FROM habit_logs")
    suspend fun deleteHabitLogs()

    @Query("DELETE FROM habits")
    suspend fun deleteHabits()

    @Query("DELETE FROM onboarding_state")
    suspend fun deleteOnboardingState()

    @Transaction
    suspend fun resetJourney() {
        deleteHabitLogs()
        deleteHabits()
        deleteOnboardingState()
    }
}
