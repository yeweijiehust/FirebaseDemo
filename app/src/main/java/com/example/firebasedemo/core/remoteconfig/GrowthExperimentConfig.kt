package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant

data class GrowthExperimentConfig(
    val onboardingVariant: OnboardingVariant,
    val suggestedHabitVariant: HabitSuggestionVariant,
    val homeHeadlineVariant: HomeHeadlineVariant,
    val analyticsLabEnabled: Boolean
)
