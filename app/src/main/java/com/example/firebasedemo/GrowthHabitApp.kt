package com.example.firebasedemo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebasedemo.feature.analyticslab.AnalyticsLabScreen
import com.example.firebasedemo.feature.habitsetup.HabitSetupScreen
import com.example.firebasedemo.feature.home.HomeScreen
import com.example.firebasedemo.feature.onboarding.OnboardingScreen
import com.example.firebasedemo.feature.progress.ProgressScreen
import com.example.firebasedemo.navigation.GrowthHabitDestination

@Composable
fun GrowthHabitApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GrowthHabitDestination.Onboarding.route
    ) {
        composable(GrowthHabitDestination.Onboarding.route) {
            OnboardingScreen(
                onContinue = {
                    navController.navigate(GrowthHabitDestination.HabitSetup.route)
                }
            )
        }
        composable(GrowthHabitDestination.HabitSetup.route) {
            HabitSetupScreen(
                onContinue = {
                    navController.navigate(GrowthHabitDestination.Home.route) {
                        popUpTo(GrowthHabitDestination.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(GrowthHabitDestination.Home.route) {
            HomeScreen(
                onOpenProgress = {
                    navController.navigate(GrowthHabitDestination.Progress.route)
                },
                onOpenAnalyticsLab = {
                    navController.navigate(GrowthHabitDestination.AnalyticsLab.route)
                }
            )
        }
        composable(GrowthHabitDestination.Progress.route) {
            ProgressScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(GrowthHabitDestination.AnalyticsLab.route) {
            AnalyticsLabScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
