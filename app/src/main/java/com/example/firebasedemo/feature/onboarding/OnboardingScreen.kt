package com.example.firebasedemo.feature.onboarding

import androidx.compose.runtime.Composable
import com.example.firebasedemo.feature.shared.GrowthLabPlaceholderScreen

@Composable
fun OnboardingScreen(
    onContinue: () -> Unit
) {
    GrowthLabPlaceholderScreen(
        title = "Growth Habit Lab",
        subtitle = "Choose a goal, build a habit loop, and learn how product analytics explains user growth.",
        learningGoal = "This page will capture activation intent and compare control versus guided onboarding variants.",
        primaryActionLabel = "Start habit setup",
        onPrimaryAction = onContinue
    )
}
