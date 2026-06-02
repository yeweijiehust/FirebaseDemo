package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.repository.GrowthRepository
import javax.inject.Inject

class ResetLearningJourneyUseCase @Inject constructor(
    private val repository: GrowthRepository
) {
    suspend operator fun invoke() {
        repository.resetJourney()
    }
}
