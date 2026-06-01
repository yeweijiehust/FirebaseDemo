package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.HabitSuggestionSource
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.OnboardingGoalId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetSuggestedHabitsUseCaseTest {
    private val useCase = GetSuggestedHabitsUseCase()

    @Test
    fun popularVariantReturnsSameSuggestionsForEveryGoal() {
        val consistencySuggestions = useCase(
            goalId = OnboardingGoalId.BUILD_CONSISTENCY,
            variant = HabitSuggestionVariant.POPULAR
        )
        val movementSuggestions = useCase(
            goalId = OnboardingGoalId.MOVE_MORE,
            variant = HabitSuggestionVariant.POPULAR
        )

        assertEquals(
            listOf("read_10_minutes", "focus_sprint", "walk_15_minutes", "review_notes"),
            consistencySuggestions.map { it.habit.id }
        )
        assertEquals(
            consistencySuggestions.map { it.habit.id },
            movementSuggestions.map { it.habit.id }
        )
        assertEquals(
            listOf(
                HabitSuggestionSource.POPULAR,
                HabitSuggestionSource.POPULAR,
                HabitSuggestionSource.POPULAR,
                HabitSuggestionSource.POPULAR
            ),
            consistencySuggestions.map { it.source }
        )
        assertEquals(4, consistencySuggestions.count { it.matchedGoalId == null })
    }

    @Test
    fun personalizedVariantPrioritizesSelectedGoal() {
        val focusSuggestions = useCase(
            goalId = OnboardingGoalId.IMPROVE_FOCUS,
            variant = HabitSuggestionVariant.PERSONALIZED
        )
        val movementSuggestions = useCase(
            goalId = OnboardingGoalId.MOVE_MORE,
            variant = HabitSuggestionVariant.PERSONALIZED
        )

        assertEquals("focus_sprint", focusSuggestions.first().habit.id)
        assertEquals("walk_15_minutes", movementSuggestions.first().habit.id)
        assertEquals(
            List(4) { HabitSuggestionSource.PERSONALIZED },
            focusSuggestions.map { it.source }
        )
        assertEquals(
            List(4) { OnboardingGoalId.IMPROVE_FOCUS },
            focusSuggestions.map { it.matchedGoalId }
        )
    }

    @Test
    fun popularSuggestionsDoNotAttachGoalMatch() {
        val suggestions = useCase(
            goalId = OnboardingGoalId.LEARN_DAILY,
            variant = HabitSuggestionVariant.POPULAR
        )

        suggestions.forEach {
            assertNull(it.matchedGoalId)
        }
    }
}
