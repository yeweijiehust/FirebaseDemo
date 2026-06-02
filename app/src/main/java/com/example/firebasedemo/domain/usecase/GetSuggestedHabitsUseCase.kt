package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitSuggestionSource
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.model.SuggestedHabit
import javax.inject.Inject

class GetSuggestedHabitsUseCase @Inject constructor() {
    operator fun invoke(
        goalId: OnboardingGoalId,
        variant: HabitSuggestionVariant
    ): List<SuggestedHabit> {
        return when (variant) {
            HabitSuggestionVariant.POPULAR -> popularHabits.map {
                SuggestedHabit(
                    habit = it,
                    source = HabitSuggestionSource.POPULAR,
                    matchedGoalId = null
                )
            }

            HabitSuggestionVariant.PERSONALIZED -> personalizedHabits(goalId).map {
                SuggestedHabit(
                    habit = it,
                    source = HabitSuggestionSource.PERSONALIZED,
                    matchedGoalId = goalId
                )
            }
        }
    }

    private fun personalizedHabits(goalId: OnboardingGoalId): List<Habit> {
        return when (goalId) {
            OnboardingGoalId.BUILD_CONSISTENCY -> listOf(
                reviewNotes,
                readTenMinutes,
                focusSprint,
                walkFifteenMinutes
            )

            OnboardingGoalId.IMPROVE_FOCUS -> listOf(
                focusSprint,
                reviewNotes,
                readTenMinutes,
                walkFifteenMinutes
            )

            OnboardingGoalId.LEARN_DAILY -> listOf(
                readTenMinutes,
                reviewNotes,
                focusSprint,
                walkFifteenMinutes
            )

            OnboardingGoalId.MOVE_MORE -> listOf(
                walkFifteenMinutes,
                focusSprint,
                reviewNotes,
                readTenMinutes
            )
        }
    }

    private companion object {
        val readTenMinutes = Habit(
            id = "read_10_minutes",
            title = "Read for 10 minutes",
            category = HabitCategory.LEARNING
        )
        val focusSprint = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )
        val walkFifteenMinutes = Habit(
            id = "walk_15_minutes",
            title = "Walk for 15 minutes",
            category = HabitCategory.MOVEMENT
        )
        val reviewNotes = Habit(
            id = "review_notes",
            title = "Review today's notes",
            category = HabitCategory.CONSISTENCY
        )
        val popularHabits = listOf(
            readTenMinutes,
            focusSprint,
            walkFifteenMinutes,
            reviewNotes
        )
    }
}
