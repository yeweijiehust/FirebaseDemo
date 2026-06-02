package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.Habit
import com.example.firebasedemo.domain.repository.GrowthRepository
import javax.inject.Inject

class GetActiveHabitUseCase @Inject constructor(
    private val repository: GrowthRepository
) {
    suspend operator fun invoke(): Habit? {
        return repository.activeHabit()
    }
}
