package com.example.firebasedemo.feature.analyticslab

import androidx.compose.runtime.Composable
import com.example.firebasedemo.feature.shared.GrowthLabPlaceholderScreen

@Composable
fun AnalyticsLabScreen(
    onBack: () -> Unit
) {
    GrowthLabPlaceholderScreen(
        title = "Analytics Lab",
        subtitle = "This page will explain the app's event taxonomy, funnel, and experiment setup.",
        learningGoal = "This page keeps the learning loop inside the app so every feature has a measurable analytics purpose.",
        primaryActionLabel = "Back to home",
        onPrimaryAction = onBack
    )
}
