package com.example.firebasedemo.data.local.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.firebasedemo.data.local.database.GrowthDatabase
import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GrowthDaoInstrumentedTest {
    private lateinit var database: GrowthDatabase
    private lateinit var dao: GrowthDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            context,
            GrowthDatabase::class.java
        ).build()
        dao = database.growthDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveActiveHabitReplacesPreviousActiveHabit() = runBlocking {
        dao.saveActiveHabit(
            HabitEntity(
                id = "read_10_minutes",
                title = "Read 10 minutes",
                category = "learning",
                isActive = true
            )
        )
        dao.saveActiveHabit(
            HabitEntity(
                id = "focus_sprint",
                title = "Do one focus sprint",
                category = "focus",
                isActive = true
            )
        )

        val activeHabit = requireNotNull(dao.activeHabit())

        assertEquals("focus_sprint", activeHabit.id)
        assertEquals("Do one focus sprint", activeHabit.title)
    }

    @Test
    fun resetJourneyClearsPersistedJourneyState() = runBlocking {
        dao.saveSelectedGoal(OnboardingStateEntity(goalId = "improve_focus"))
        dao.saveActiveHabit(
            HabitEntity(
                id = "focus_sprint",
                title = "Do one focus sprint",
                category = "focus",
                isActive = true
            )
        )
        dao.insertHabitLog(
            HabitLogEntity(
                habitId = "focus_sprint",
                loggedDate = "2026-06-02"
            )
        )

        dao.resetJourney()

        assertNull(dao.selectedGoal())
        assertNull(dao.activeHabit())
        assertEquals(emptyList<HabitLogEntity>(), dao.habitLogs("focus_sprint"))
    }
}
