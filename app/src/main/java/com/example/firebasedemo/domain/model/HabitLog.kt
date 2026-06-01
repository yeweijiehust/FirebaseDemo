package com.example.firebasedemo.domain.model

import java.time.LocalDate

data class HabitLog(
    val habitId: String,
    val loggedDate: LocalDate
)
