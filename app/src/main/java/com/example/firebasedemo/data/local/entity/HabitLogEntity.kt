package com.example.firebasedemo.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "habit_logs",
    primaryKeys = ["habitId", "loggedDate"]
)
data class HabitLogEntity(
    val habitId: String,
    val loggedDate: String
)
