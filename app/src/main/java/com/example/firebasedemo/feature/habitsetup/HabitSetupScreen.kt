package com.example.firebasedemo.feature.habitsetup

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
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.SuggestedHabit

@Composable
fun HabitSetupScreen(
    onContinue: () -> Unit,
    viewModel: HabitSetupViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HabitSetupContent(
        uiState = uiState.value,
        onSuggestionSelected = viewModel::selectSuggestion,
        onContinue = {
            viewModel.continueWithSelectedHabit(onContinue)
        }
    )
}

@Composable
private fun HabitSetupContent(
    uiState: HabitSetupUiState,
    onSuggestionSelected: (SuggestedHabit) -> Unit,
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
                text = "Choose your first habit",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = if (uiState.variant == HabitSuggestionVariant.PERSONALIZED) {
                    "These suggestions are ordered from your onboarding goal, giving us a personalized variant to compare."
                } else {
                    "These are popular starter habits, giving us a simple control experience to compare."
                },
                style = MaterialTheme.typography.bodyLarge
            )
            uiState.suggestions.forEach { suggestion ->
                HabitSuggestionCard(
                    suggestion = suggestion,
                    selected = suggestion.habit.id == uiState.selectedHabitId,
                    onClick = {
                        onSuggestionSelected(suggestion)
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onContinue,
                enabled = uiState.selectedHabitId != null && !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (uiState.isSaving) "Saving habit" else "Start daily loop")
            }
        }
    }
}

@Composable
private fun HabitSuggestionCard(
    suggestion: SuggestedHabit,
    selected: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.secondaryContainer
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
                text = suggestion.habit.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Category: ${suggestion.habit.category.analyticsValue}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Source: ${suggestion.source.analyticsValue}",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
