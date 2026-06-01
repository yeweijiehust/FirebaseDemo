package com.example.firebasedemo.data.mapper

import com.example.firebasedemo.data.local.entity.HabitEntity
import com.example.firebasedemo.data.local.entity.HabitLogEntity
import com.example.firebasedemo.data.local.entity.OnboardingStateEntity
import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.model.HabitCategory
import com.example.firebasedemo.domain.model.HabitLog
import com.example.firebasedemo.domain.model.OnboardingGoalId
import java.time.LocalDate
import javax.inject.Inject

class GrowthEntityMapper @Inject constructor() {
    fun toOnboardingStateEntity(goalId: OnboardingGoalId): OnboardingStateEntity {
        return OnboardingStateEntity(
            goalId = goalId.analyticsValue
        )
    }

    fun toGoalId(entity: OnboardingStateEntity): OnboardingGoalId {
        return requireNotNull(OnboardingGoalId.fromAnalyticsValue(entity.goalId))
    }

    fun toHabitEntity(habit: Habit, isActive: Boolean): HabitEntity {
        return HabitEntity(
            id = habit.id,
            title = habit.title,
            category = habit.category.analyticsValue,
            isActive = isActive
        )
    }

    fun toHabit(entity: HabitEntity): Habit {
        return Habit(
            id = entity.id,
            title = entity.title,
            category = requireNotNull(HabitCategory.fromAnalyticsValue(entity.category))
        )
    }

    fun toHabitLogEntity(habitId: String, date: LocalDate): HabitLogEntity {
        return HabitLogEntity(
            habitId = habitId,
            loggedDate = date.toString()
        )
    }

    fun toHabitLog(entity: HabitLogEntity): HabitLog {
        return HabitLog(
            habitId = entity.habitId,
            loggedDate = LocalDate.parse(entity.loggedDate)
        )
    }
}
