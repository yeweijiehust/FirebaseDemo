package com.example.firebasedemo.navigation

sealed class GrowthHabitDestination(val route: String) {
    data object Onboarding : GrowthHabitDestination("onboarding")
    data object HabitSetup : GrowthHabitDestination("habit_setup")
    data object Home : GrowthHabitDestination("home")
    data object Progress : GrowthHabitDestination("progress")
    data object AnalyticsLab : GrowthHabitDestination("analytics_lab")
}
