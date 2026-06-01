package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.OnboardingGoal
import com.example.firebasedemo.domain.model.OnboardingGoalId

class GetOnboardingGoalsUseCase {
    operator fun invoke(): List<OnboardingGoal> = listOf(
        OnboardingGoal(
            id = OnboardingGoalId.BUILD_CONSISTENCY,
            title = "Build consistency",
            description = "Create a repeatable daily rhythm."
        ),
        OnboardingGoal(
            id = OnboardingGoalId.IMPROVE_FOCUS,
            title = "Improve focus",
            description = "Protect attention for meaningful work."
        ),
        OnboardingGoal(
            id = OnboardingGoalId.LEARN_DAILY,
            title = "Learn daily",
            description = "Make steady progress on a learning goal."
        ),
        OnboardingGoal(
            id = OnboardingGoalId.MOVE_MORE,
            title = "Move more",
            description = "Add light movement to the day."
        )
    )
}
