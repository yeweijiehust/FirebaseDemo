package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.ActivationStatus
import com.example.firebasedemo.domain.model.GrowthMetrics
import com.example.firebasedemo.domain.model.HabitLog
import java.time.LocalDate
import javax.inject.Inject

class CalculateGrowthMetricsUseCase @Inject constructor() {
    operator fun invoke(
        onboardingCompleted: Boolean,
        logs: List<HabitLog>,
        today: LocalDate
    ): GrowthMetrics {
        val loggedDates = logs
            .map { it.loggedDate }
            .distinct()
            .sorted()
        val totalCompletions = loggedDates.size
        val activationStatus = if (onboardingCompleted && totalCompletions > 0) {
            ActivationStatus.ACTIVATED
        } else {
            ActivationStatus.INACTIVE
        }

        return GrowthMetrics(
            totalCompletions = totalCompletions,
            currentStreak = calculateCurrentStreak(loggedDates, today),
            longestStreak = calculateLongestStreak(loggedDates),
            lastLoggedDate = loggedDates.lastOrNull(),
            activationStatus = activationStatus
        )
    }

    private fun calculateCurrentStreak(
        loggedDates: List<LocalDate>,
        today: LocalDate
    ): Int {
        if (loggedDates.isEmpty()) {
            return 0
        }

        val lastLoggedDate = loggedDates.last()
        if (lastLoggedDate.isBefore(today.minusDays(1))) {
            return 0
        }

        var streak = 1
        var cursor = lastLoggedDate

        for (date in loggedDates.dropLast(1).asReversed()) {
            if (date == cursor.minusDays(1)) {
                streak += 1
                cursor = date
            } else {
                return streak
            }
        }

        return streak
    }

    private fun calculateLongestStreak(loggedDates: List<LocalDate>): Int {
        if (loggedDates.isEmpty()) {
            return 0
        }

        var longestStreak = 1
        var currentStreak = 1
        var previousDate = loggedDates.first()

        for (date in loggedDates.drop(1)) {
            currentStreak = if (date == previousDate.plusDays(1)) {
                currentStreak + 1
            } else {
                1
            }
            longestStreak = maxOf(longestStreak, currentStreak)
            previousDate = date
        }

        return longestStreak
    }
}
