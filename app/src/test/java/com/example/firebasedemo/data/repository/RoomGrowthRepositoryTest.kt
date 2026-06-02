package com.example.firebasedemo.data.repository

import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity
import com.example.firebasedemo.data.local.source.GrowthLocalDataSource
import com.example.firebasedemo.data.mapper.GrowthEntityMapper
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.model.OnboardingGoalId
import java.time.LocalDate
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RoomGrowthRepositoryTest {
    private val localDataSource = FakeGrowthLocalDataSource()
    private val repository = RoomGrowthRepository(
        localDataSource = localDataSource,
        mapper = GrowthEntityMapper()
    )

    @Test
    fun selectedGoalIsNullUntilSaved() = runBlocking {
        assertNull(repository.selectedGoalId())

        repository.saveSelectedGoal(OnboardingGoalId.LEARN_DAILY)

        assertEquals(OnboardingGoalId.LEARN_DAILY, repository.selectedGoalId())
    }

    @Test
    fun activeHabitIsNullUntilSaved() = runBlocking {
        val habit = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )

        assertNull(repository.activeHabit())

        repository.saveActiveHabit(habit)

        assertEquals(habit, repository.activeHabit())
    }

    @Test
    fun duplicateHabitLogReturnsAlreadyLoggedAndKeepsSingleStoredLog() = runBlocking {
        val date = LocalDate.of(2026, 6, 1)

        val firstResult = repository.logHabit("focus_sprint", date)
        val secondResult = repository.logHabit("focus_sprint", date)
        val logs = repository.habitLogs("focus_sprint")

        assertTrue(firstResult is HabitLogResult.Logged)
        assertTrue(secondResult is HabitLogResult.AlreadyLogged)
        assertEquals(1, logs.size)
        assertEquals(date, logs.first().loggedDate)
    }

    @Test
    fun habitLogsAreReturnedInDateOrder() = runBlocking {
        repository.logHabit("focus_sprint", LocalDate.of(2026, 6, 3))
        repository.logHabit("focus_sprint", LocalDate.of(2026, 6, 1))
        repository.logHabit("focus_sprint", LocalDate.of(2026, 6, 2))

        val logs = repository.habitLogs("focus_sprint")

        assertEquals(
            listOf(
                LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 2),
                LocalDate.of(2026, 6, 3)
            ),
            logs.map { it.loggedDate }
        )
    }

    @Test
    fun resetJourneyClearsGoalHabitAndLogs() = runBlocking {
        val habit = Habit(
            id = "focus_sprint",
            title = "Do one focus sprint",
            category = HabitCategory.FOCUS
        )
        repository.saveSelectedGoal(OnboardingGoalId.IMPROVE_FOCUS)
        repository.saveActiveHabit(habit)
        repository.logHabit(habit.id, LocalDate.of(2026, 6, 1))

        repository.resetJourney()

        assertNull(repository.selectedGoalId())
        assertNull(repository.activeHabit())
        assertEquals(emptyList<LocalDate>(), repository.habitLogs(habit.id).map { it.loggedDate })
    }

    private class FakeGrowthLocalDataSource : GrowthLocalDataSource {
        private var selectedGoal: OnboardingStateEntity? = null
        private var activeHabit: HabitEntity? = null
        private val habitLogs = mutableSetOf<HabitLogEntity>()

        override suspend fun selectedGoal(): OnboardingStateEntity? {
            return selectedGoal
        }

        override suspend fun saveSelectedGoal(entity: OnboardingStateEntity) {
            selectedGoal = entity
        }

        override suspend fun activeHabit(): HabitEntity? {
            return activeHabit
        }

        override suspend fun saveActiveHabit(entity: HabitEntity) {
            activeHabit = entity
        }

        override suspend fun habitLogs(habitId: String): List<HabitLogEntity> {
            return habitLogs
                .filter { it.habitId == habitId }
                .sortedBy { it.loggedDate }
        }

        override suspend fun insertHabitLog(entity: HabitLogEntity): Boolean {
            return habitLogs.add(entity)
        }

        override suspend fun resetJourney() {
            selectedGoal = null
            activeHabit = null
            habitLogs.clear()
        }
    }
}
