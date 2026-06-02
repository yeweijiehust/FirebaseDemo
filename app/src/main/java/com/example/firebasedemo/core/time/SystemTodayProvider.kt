package com.example.firebasedemo.core.time

import java.time.LocalDate
import javax.inject.Inject

class SystemTodayProvider @Inject constructor() : TodayProvider {
    override fun today(): LocalDate {
        return LocalDate.now()
    }
}
