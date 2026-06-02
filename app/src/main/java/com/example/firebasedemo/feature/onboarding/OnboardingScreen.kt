package com.example.firebasedemo.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firebasedemo.domain.model.OnboardingGoal
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.model.OnboardingVariant

@Composable
fun OnboardingScreen(
    onContinue: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingContent(
        uiState = uiState.value,
        onGoalSelected = viewModel::selectGoal,
        onContinue = {
            viewModel.continueWithSelectedGoal(onContinue)
        }
    )
}

@Composable
private fun OnboardingContent(
    uiState: OnboardingUiState,
    onGoalSelected: (OnboardingGoalId) -> Unit,
    onContinue: () -> Unit
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
                text = "Growth Habit Lab",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = if (uiState.variant == OnboardingVariant.GUIDED) {
                    "Pick the outcome you want most. We will turn it into a tiny daily action and measure whether the experience helps you activate."
                } else {
                    "Choose a goal to start your habit experiment."
                },
                style = MaterialTheme.typography.bodyLarge
            )
            uiState.goals.forEach { goal ->
                GoalCard(
                    goal = goal,
                    selected = goal.id == uiState.selectedGoalId,
                    guided = uiState.variant == OnboardingVariant.GUIDED,
                    onClick = {
                        onGoalSelected(goal.id)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onContinue,
                enabled = uiState.selectedGoalId != null && !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (uiState.isSaving) "Saving goal" else "Continue to habit setup")
            }
        }
    }
}

@Composable
private fun GoalCard(
    goal: OnboardingGoal,
    selected: Boolean,
    guided: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (guided) {
                    "${goal.description} This will become your activation intent."
                } else {
                    goal.description
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
