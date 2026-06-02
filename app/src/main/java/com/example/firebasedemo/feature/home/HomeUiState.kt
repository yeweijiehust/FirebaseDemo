package com.example.firebasedemo.feature.home

import com.example.firebasedemo.domain.model.GrowthMetrics
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingGoalId

data class HomeUiState(
    val selectedGoalId: OnboardingGoalId? = null,
    val activeHabit: Habit? = null,
    val metrics: GrowthMetrics? = null,
    val headlineVariant: HomeHeadlineVariant = HomeHeadlineVariant.PROGRESS,
    val analyticsLabEnabled: Boolean = true,
    val loggedToday: Boolean = false,
    val message: String? = null,
    val isLoading: Boolean = true
)
