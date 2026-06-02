package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.GrowthMetrics
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate
import javax.inject.Inject

class GetGrowthMetricsUseCase @Inject constructor(
    private val repository: GrowthRepository,
    private val calculateGrowthMetrics: CalculateGrowthMetricsUseCase
) {
    suspend operator fun invoke(today: LocalDate): GrowthMetrics? {
        val habit = repository.activeHabit() ?: return null
        val logs = repository.habitLogs(habit.id)
        val onboardingCompleted = repository.selectedGoalId() != null
        return calculateGrowthMetrics(
            onboardingCompleted = onboardingCompleted,
            logs = logs,
            today = today
        )
    }
}
