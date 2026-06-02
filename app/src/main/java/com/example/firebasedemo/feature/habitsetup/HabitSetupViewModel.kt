package com.example.firebasedemo.feature.habitsetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.model.SuggestedHabit
import com.example.firebasedemo.domain.usecase.GetSelectedGoalUseCase
import com.example.firebasedemo.domain.usecase.GetSuggestedHabitsUseCase
import com.example.firebasedemo.domain.usecase.SaveActiveHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HabitSetupViewModel @Inject constructor(
    configProvider: ExperimentConfigProvider,
    private val getSelectedGoal: GetSelectedGoalUseCase,
    private val getSuggestedHabits: GetSuggestedHabitsUseCase,
    private val saveActiveHabit: SaveActiveHabitUseCase,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val suggestionVariant = configProvider.currentConfig().suggestedHabitVariant
    private val _uiState = MutableStateFlow(
        HabitSetupUiState(
            variant = suggestionVariant
        )
    )
    val uiState: StateFlow<HabitSetupUiState> = _uiState.asStateFlow()

    init {
        analyticsTracker.setUserProperty(
            GrowthUserProperty.SUGGESTED_HABIT_VARIANT,
            suggestionVariant.remoteConfigValue
        )
        viewModelScope.launch {
            val goalId = getSelectedGoal() ?: OnboardingGoalId.BUILD_CONSISTENCY
            _uiState.update {
                it.copy(
                    selectedGoalId = goalId,
                    suggestions = getSuggestedHabits(goalId, suggestionVariant)
                )
            }
        }
    }

    fun selectSuggestion(suggestion: SuggestedHabit) {
        _uiState.update { it.copy(selectedHabitId = suggestion.habit.id) }
        analyticsTracker.track(
            GrowthAnalyticsEvent.habitSuggestedSelected(
                habitId = suggestion.habit.id,
                habitCategory = suggestion.habit.category.analyticsValue,
                source = suggestion.source.analyticsValue,
                variant = suggestionVariant.remoteConfigValue
            )
        )
    }

    fun continueWithSelectedHabit(onComplete: () -> Unit) {
        val suggestion = _uiState.value.suggestions.firstOrNull {
            it.habit.id == _uiState.value.selectedHabitId
        } ?: return
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            saveActiveHabit(suggestion.habit)
            analyticsTracker.track(
                GrowthAnalyticsEvent.habitCreated(
                    habitId = suggestion.habit.id,
                    habitCategory = suggestion.habit.category.analyticsValue,
                    source = suggestion.source.analyticsValue
                )
            )
            _uiState.update { it.copy(isSaving = false) }
            onComplete()
        }
    }
}
