package com.example.firebasedemo.feature.analyticslab

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

@Composable
fun AnalyticsLabScreen(
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
            LearningCard(
                title = "Experiment surfaces",
                body = "Onboarding compares control and guided copy. Habit Setup compares popular and personalized suggestions. Home compares progress and streak headlines."
            )
            LearningCard(
                title = "DebugView checklist",
                body = "Open the app, select a goal, create a habit, log today, then inspect event names and parameters in Firebase DebugView."
            )
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
