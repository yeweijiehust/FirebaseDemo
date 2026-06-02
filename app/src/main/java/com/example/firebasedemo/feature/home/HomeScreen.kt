package com.example.firebasedemo.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.firebasedemo.domain.model.HomeHeadlineVariant

@Composable
fun HomeScreen(
    onOpenProgress: () -> Unit,
    onOpenAnalyticsLab: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState.value,
        onLogToday = viewModel::logToday,
        onOpenProgress = onOpenProgress,
        onOpenAnalyticsLab = onOpenAnalyticsLab
    )
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    onLogToday: () -> Unit,
    onOpenProgress: () -> Unit,
    onOpenAnalyticsLab: () -> Unit
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
                text = headline(uiState),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Goal: ${uiState.selectedGoalId?.analyticsValue ?: "not selected"}",
                style = MaterialTheme.typography.bodyLarge
            )
            HabitCard(uiState = uiState)
            MetricsRow(uiState = uiState)
            uiState.message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Button(
                onClick = onLogToday,
                enabled = uiState.activeHabit != null && !uiState.loggedToday,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (uiState.loggedToday) "Logged today" else "Log today")
            }
            OutlinedButton(
                onClick = onOpenProgress,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "View progress")
            }
            if (uiState.analyticsLabEnabled) {
                OutlinedButton(
                    onClick = onOpenAnalyticsLab,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Open analytics lab")
                }
            }
        }
    }
}

@Composable
private fun HabitCard(uiState: HomeUiState) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = uiState.activeHabit?.title ?: "No active habit yet",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Category: ${uiState.activeHabit?.category?.analyticsValue ?: "none"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun MetricsRow(uiState: HomeUiState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        MetricCard(
            title = "Completions",
            value = "${uiState.metrics?.totalCompletions ?: 0}",
            modifier = Modifier.weight(1f)
        )
        MetricCard(
            title = "Streak",
            value = "${uiState.metrics?.currentStreak ?: 0}",
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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

private fun headline(uiState: HomeUiState): String {
    val metrics = uiState.metrics
    return when (uiState.headlineVariant) {
        HomeHeadlineVariant.PROGRESS -> "You have ${metrics?.totalCompletions ?: 0} completions"
        HomeHeadlineVariant.STREAK -> "Your streak is ${metrics?.currentStreak ?: 0} days"
    }
}
