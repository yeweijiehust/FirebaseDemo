package com.example.firebasedemo.feature.habitsetup

import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.model.SuggestedHabit

data class HabitSetupUiState(
    val selectedGoalId: OnboardingGoalId? = null,
    val suggestions: List<SuggestedHabit> = emptyList(),
    val selectedHabitId: String? = null,
    val variant: HabitSuggestionVariant = HabitSuggestionVariant.POPULAR,
    val isSaving: Boolean = false
)
