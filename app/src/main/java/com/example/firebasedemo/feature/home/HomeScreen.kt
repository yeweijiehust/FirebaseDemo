package com.example.firebasedemo.feature.home

import androidx.compose.runtime.Composable
import com.example.firebasedemo.feature.shared.GrowthLabPlaceholderScreen

@Composable
fun HomeScreen(
    onOpenProgress: () -> Unit,
    onOpenAnalyticsLab: () -> Unit
) {
    GrowthLabPlaceholderScreen(
        title = "Home",
        subtitle = "This will become the daily habit logging loop.",
        learningGoal = "This page will compare progress-focused and streak-focused headlines while measuring daily engagement.",
        primaryActionLabel = "View progress",
        onPrimaryAction = onOpenProgress,
        secondaryActionLabel = "Open analytics lab",
        onSecondaryAction = onOpenAnalyticsLab
    )
}
