package com.example.firebasedemo.domain.model

data class SuggestedHabit(
    val habit: Habit,
    val source: HabitSuggestionSource,
    val matchedGoalId: OnboardingGoalId?
)
