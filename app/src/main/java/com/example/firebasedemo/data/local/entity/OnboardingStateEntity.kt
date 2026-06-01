package com.example.firebasedemo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "onboarding_state")
data class OnboardingStateEntity(
    @PrimaryKey val id: Int = 0,
    val goalId: String
)
