package com.example.firebasedemo.navigation

sealed class GrowthHabitDestination(
    val route: String,
    val screenName: String
) {
    data object Onboarding : GrowthHabitDestination("onboarding", "onboarding")
    data object HabitSetup : GrowthHabitDestination("habit_setup", "habit_setup")
    data object Home : GrowthHabitDestination("home", "home")
    data object Progress : GrowthHabitDestination("progress", "progress")
    data object AnalyticsLab : GrowthHabitDestination("analytics_lab", "analytics_lab")

    companion object {
        val entries = listOf(
            Onboarding,
            HabitSetup,
            Home,
            Progress,
            AnalyticsLab
        )

        fun fromRoute(route: String?): GrowthHabitDestination? {
            return entries.firstOrNull { it.route == route }
        }
    }
}
