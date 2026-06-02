package com.example.firebasedemo.feature.onboarding

import com.example.firebasedemo.domain.model.OnboardingGoal
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.model.OnboardingVariant

data class OnboardingUiState(
    val goals: List<OnboardingGoal> = emptyList(),
    val selectedGoalId: OnboardingGoalId? = null,
    val variant: OnboardingVariant = OnboardingVariant.CONTROL,
    val isSaving: Boolean = false
)
