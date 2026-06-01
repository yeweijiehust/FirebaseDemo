package com.example.firebasedemo.domain.model

sealed interface HabitLogResult {
    data class Logged(val habitLog: HabitLog) : HabitLogResult
    data class AlreadyLogged(val habitLog: HabitLog) : HabitLogResult
}
