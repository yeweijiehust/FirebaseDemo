package com.example.firebasedemo.core.time

import java.time.LocalDate

interface TodayProvider {
    fun today(): LocalDate
}
