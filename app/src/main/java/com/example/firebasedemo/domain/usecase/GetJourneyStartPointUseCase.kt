package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.JourneyStartPoint
import com.example.firebasedemo.domain.repository.GrowthRepository
import javax.inject.Inject

class GetJourneyStartPointUseCase @Inject constructor(
    private val repository: GrowthRepository
) {
    suspend operator fun invoke(): JourneyStartPoint {
        val activeHabit = repository.activeHabit()
        if (activeHabit != null) {
            return JourneyStartPoint.HOME
        }

        val selectedGoal = repository.selectedGoalId()
        if (selectedGoal != null) {
            return JourneyStartPoint.HABIT_SETUP
        }

        return JourneyStartPoint.ONBOARDING
    }
}
