package com.example.firebasedemo.domain.model

import java.time.LocalDate

data class GrowthMetrics(
    val totalCompletions: Int,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastLoggedDate: LocalDate?,
    val activationStatus: ActivationStatus
)
