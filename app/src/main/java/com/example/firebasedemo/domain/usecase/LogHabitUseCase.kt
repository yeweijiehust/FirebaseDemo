package com.example.firebasedemo.domain.usecase

import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.repository.GrowthRepository
import java.time.LocalDate
import javax.inject.Inject

class LogHabitUseCase @Inject constructor(
    private val repository: GrowthRepository
) {
    suspend operator fun invoke(habitId: String, date: LocalDate): HabitLogResult {
        return repository.logHabit(habitId, date)
    }
}
