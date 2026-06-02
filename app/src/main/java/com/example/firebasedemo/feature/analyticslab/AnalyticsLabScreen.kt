package com.example.firebasedemo.feature.analyticslab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AnalyticsLabScreen(
    onBack: () -> Unit,
    onResetComplete: () -> Unit,
    viewModel: AnalyticsLabViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Analytics Lab",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Use this page as the map between code, product behavior, Firebase DebugView, and experiment thinking.",
                style = MaterialTheme.typography.bodyLarge
            )
            LearningCard(
                title = "Growth funnel",
                body = "app_opened -> onboarding_started -> onboarding_completed -> habit_created -> habit_logged -> progress_viewed"
            )
            EventCatalogCard(
                events = AnalyticsLabReference.eventCatalog
            )
            ExperimentCatalogCard(
                experiments = AnalyticsLabReference.experimentCatalog
            )
            LearningCard(
                title = "DebugView checklist",
                body = "Open the app, select a goal, create a habit, log today, then inspect event names and parameters in Firebase DebugView."
            )
            LearningCard(
                title = "Repeat the funnel",
                body = "Reset your local journey when you want to practice the activation funnel again without using adb or Android settings."
            )
            OutlinedButton(
                onClick = {
                    viewModel.resetJourney(onResetComplete)
                },
                enabled = !uiState.value.isResetting,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (uiState.value.isResetting) "Resetting journey" else "Reset learning journey")
            }
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Back to home")
            }
        }
    }
}

@Composable
private fun EventCatalogCard(events: List<AnalyticsEventReference>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Event catalog",
                style = MaterialTheme.typography.titleMedium
            )
            events.forEach { event ->
                ReferenceRow(
                    title = event.name,
                    body = "${event.whenItFires} Parameters: ${event.parameters.joinToString()}"
                )
            }
        }
    }
}

@Composable
private fun ExperimentCatalogCard(experiments: List<ExperimentReference>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Remote Config experiments",
                style = MaterialTheme.typography.titleMedium
            )
            experiments.forEach { experiment ->
                ReferenceRow(
                    title = experiment.key,
                    body = "${experiment.surface}: ${experiment.variants.joinToString(separator = " vs ")}"
                )
            }
        }
    }
}

@Composable
private fun ReferenceRow(
    title: String,
    body: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun LearningCard(
    title: String,
    body: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
