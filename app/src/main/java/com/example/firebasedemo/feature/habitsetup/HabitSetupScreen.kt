package com.example.firebasedemo.feature.habitsetup

import androidx.compose.runtime.Composable
import com.example.firebasedemo.feature.shared.GrowthLabPlaceholderScreen

@Composable
fun HabitSetupScreen(
    onContinue: () -> Unit
) {
    GrowthLabPlaceholderScreen(
        title = "Habit Setup",
        subtitle = "Turn a user goal into one measurable behavior.",
        learningGoal = "This page will test popular versus personalized habit suggestions and track habit creation.",
        primaryActionLabel = "Go to home",
        onPrimaryAction = onContinue
    )
}
