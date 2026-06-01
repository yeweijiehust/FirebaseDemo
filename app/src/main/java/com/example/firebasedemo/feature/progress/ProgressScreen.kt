package com.example.firebasedemo.feature.progress

import androidx.compose.runtime.Composable
import com.example.firebasedemo.feature.shared.GrowthLabPlaceholderScreen

@Composable
fun ProgressScreen(
    onBack: () -> Unit
) {
    GrowthLabPlaceholderScreen(
        title = "Progress",
        subtitle = "This page will summarize completions, streaks, activation, and retention signals.",
        learningGoal = "This page will make growth metrics visible so analytics events can be connected to product outcomes.",
        primaryActionLabel = "Back to home",
        onPrimaryAction = onBack
    )
}
