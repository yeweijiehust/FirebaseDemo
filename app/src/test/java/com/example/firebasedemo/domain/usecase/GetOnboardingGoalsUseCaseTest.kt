package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.OnboardingGoalId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GetOnboardingGoalsUseCaseTest {
    private val useCase = GetOnboardingGoalsUseCase()

    @Test
    fun returnsFourStableGoals() {
        val goals = useCase()

        assertEquals(4, goals.size)
        assertEquals(
            listOf(
                OnboardingGoalId.BUILD_CONSISTENCY,
                OnboardingGoalId.IMPROVE_FOCUS,
                OnboardingGoalId.LEARN_DAILY,
                OnboardingGoalId.MOVE_MORE
            ),
            goals.map { it.id }
        )
        assertTrue(goals.all { it.title.isNotBlank() })
        assertTrue(goals.all { it.description.isNotBlank() })
    }
}
