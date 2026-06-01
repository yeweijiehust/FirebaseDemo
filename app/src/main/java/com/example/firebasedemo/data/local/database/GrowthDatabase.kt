package com.example.firebasedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.firebasedemo.data.local.dao.GrowthDao
import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity

@Database(
    entities = [
        OnboardingStateEntity::class,
        HabitEntity::class,
        HabitLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GrowthDatabase : RoomDatabase() {
    abstract fun growthDao(): GrowthDao
}
