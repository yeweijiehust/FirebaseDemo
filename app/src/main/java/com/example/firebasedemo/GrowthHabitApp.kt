package com.example.firebasedemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
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
fun GrowthHabitApp(
    appAnalyticsViewModel: AppAnalyticsViewModel = hiltViewModel(),
    appExperimentViewModel: AppExperimentViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val experimentUiState = appExperimentViewModel.uiState.collectAsStateWithLifecycle()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = GrowthHabitDestination.fromRoute(
        currentBackStackEntry.value?.destination?.route
    )

    LaunchedEffect(Unit) {
        appAnalyticsViewModel.onAppShown()
        appExperimentViewModel.refreshConfig()
    }

    LaunchedEffect(currentDestination?.screenName) {
        currentDestination?.let {
            appAnalyticsViewModel.onScreenShown(it.screenName)
            appExperimentViewModel.exposeScreenExperiments(it)
        }
    }

    if (experimentUiState.value.isReady) {
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
    } else {
        ExperimentLoadingScreen()
    }
}

@Composable
private fun ExperimentLoadingScreen() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = "Preparing experiment settings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "We are making sure assignment, exposure, and screen behavior use the same variant snapshot.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
