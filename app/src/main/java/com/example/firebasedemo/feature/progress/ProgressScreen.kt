package com.example.firebasedemo.feature.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
fun ProgressScreen(
    onBack: () -> Unit,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ProgressContent(
        uiState = uiState.value,
        onBack = onBack
    )
}

@Composable
private fun ProgressContent(
    uiState: ProgressUiState,
    onBack: () -> Unit
) {
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
                text = "Progress",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = uiState.activeHabit?.title ?: "No active habit selected yet",
                style = MaterialTheme.typography.bodyLarge
            )
            MetricCard("Total completions", "${uiState.metrics?.totalCompletions ?: 0}")
            MetricCard("Current streak", "${uiState.metrics?.currentStreak ?: 0}")
            MetricCard("Longest streak", "${uiState.metrics?.longestStreak ?: 0}")
            MetricCard("Activation status", uiState.metrics?.activationStatus?.analyticsValue ?: "inactive")
            MetricCard("Last logged date", uiState.metrics?.lastLoggedDate?.toString() ?: "none")
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
private fun MetricCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
