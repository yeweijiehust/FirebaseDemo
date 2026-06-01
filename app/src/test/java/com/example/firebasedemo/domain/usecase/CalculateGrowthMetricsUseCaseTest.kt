package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.ActivationStatus
import com.example.firebasedemo.domain.model.HabitLog
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalculateGrowthMetricsUseCaseTest {
    private val useCase = CalculateGrowthMetricsUseCase()
    private val today = LocalDate.of(2026, 6, 1)

    @Test
    fun noLogsProducesInactiveZeroMetrics() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = emptyList(),
            today = today
        )

        assertEquals(0, metrics.totalCompletions)
        assertEquals(0, metrics.currentStreak)
        assertEquals(0, metrics.longestStreak)
        assertNull(metrics.lastLoggedDate)
        assertEquals(ActivationStatus.INACTIVE, metrics.activationStatus)
    }

    @Test
    fun onboardingAndOneLogProducesActivatedMetrics() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = listOf(log(today)),
            today = today
        )

        assertEquals(1, metrics.totalCompletions)
        assertEquals(1, metrics.currentStreak)
        assertEquals(1, metrics.longestStreak)
        assertEquals(today, metrics.lastLoggedDate)
        assertEquals(ActivationStatus.ACTIVATED, metrics.activationStatus)
    }

    @Test
    fun logWithoutOnboardingDoesNotActivateUser() {
        val metrics = useCase(
            onboardingCompleted = false,
            logs = listOf(log(today)),
            today = today
        )

        assertEquals(1, metrics.totalCompletions)
        assertEquals(ActivationStatus.INACTIVE, metrics.activationStatus)
    }

    @Test
    fun duplicateSameDayLogsCountAsOneCompletion() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = listOf(
                log(today.minusDays(1)),
                log(today.minusDays(1)),
                log(today)
            ),
            today = today
        )

        assertEquals(2, metrics.totalCompletions)
        assertEquals(2, metrics.currentStreak)
        assertEquals(2, metrics.longestStreak)
    }

    @Test
    fun currentStreakRemainsActiveWhenLastLogWasYesterday() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = listOf(
                log(today.minusDays(3)),
                log(today.minusDays(2)),
                log(today.minusDays(1))
            ),
            today = today
        )

        assertEquals(3, metrics.currentStreak)
        assertEquals(3, metrics.longestStreak)
        assertEquals(today.minusDays(1), metrics.lastLoggedDate)
    }

    @Test
    fun currentStreakIsZeroWhenLastLogIsOlderThanYesterday() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = listOf(
                log(today.minusDays(4)),
                log(today.minusDays(3))
            ),
            today = today
        )

        assertEquals(0, metrics.currentStreak)
        assertEquals(2, metrics.longestStreak)
        assertEquals(today.minusDays(3), metrics.lastLoggedDate)
    }

    @Test
    fun longestStreakSurvivesBrokenCurrentStreak() {
        val metrics = useCase(
            onboardingCompleted = true,
            logs = listOf(
                log(today.minusDays(8)),
                log(today.minusDays(7)),
                log(today.minusDays(6)),
                log(today.minusDays(1)),
                log(today)
            ),
            today = today
        )

        assertEquals(5, metrics.totalCompletions)
        assertEquals(2, metrics.currentStreak)
        assertEquals(3, metrics.longestStreak)
    }

    private fun log(date: LocalDate): HabitLog {
        return HabitLog(
            habitId = "focus_sprint",
            loggedDate = date
        )
    }
}
