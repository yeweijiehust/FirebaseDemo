package com.example.firebasedemo.feature.progress

import com.example.firebasedemo.domain.model.GrowthMetrics
import com.example.firebasedemo.domain.model.Habit

data class ProgressUiState(
    val activeHabit: Habit? = null,
    val metrics: GrowthMetrics? = null,
    val isLoading: Boolean = true
)
