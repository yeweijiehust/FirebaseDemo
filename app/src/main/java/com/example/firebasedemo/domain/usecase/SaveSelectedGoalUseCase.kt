package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.repository.GrowthRepository
import javax.inject.Inject

class SaveSelectedGoalUseCase @Inject constructor(
    private val repository: GrowthRepository
) {
    suspend operator fun invoke(goalId: OnboardingGoalId) {
        repository.saveSelectedGoal(goalId)
    }
}
