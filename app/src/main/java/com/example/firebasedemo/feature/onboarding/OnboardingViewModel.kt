package com.example.firebasedemo.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.domain.model.OnboardingGoalId
import com.example.firebasedemo.domain.usecase.GetOnboardingGoalsUseCase
import com.example.firebasedemo.domain.usecase.SaveSelectedGoalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getOnboardingGoals: GetOnboardingGoalsUseCase,
    configProvider: ExperimentConfigProvider,
    private val saveSelectedGoal: SaveSelectedGoalUseCase,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val onboardingVariant = configProvider.currentConfig().onboardingVariant
    private val _uiState = MutableStateFlow(
        OnboardingUiState(
            goals = getOnboardingGoals(),
            variant = onboardingVariant
        )
    )
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        analyticsTracker.track(
            GrowthAnalyticsEvent.onboardingStarted(onboardingVariant.remoteConfigValue)
        )
    }

    fun selectGoal(goalId: OnboardingGoalId) {
        _uiState.update { it.copy(selectedGoalId = goalId) }
        analyticsTracker.track(
            GrowthAnalyticsEvent.onboardingGoalSelected(
                goalId = goalId.analyticsValue,
                variant = onboardingVariant.remoteConfigValue
            )
        )
        analyticsTracker.setUserProperty(
            GrowthUserProperty.ONBOARDING_GOAL,
            goalId.analyticsValue
        )
        analyticsTracker.setUserProperty(
            GrowthUserProperty.ONBOARDING_VARIANT,
            onboardingVariant.remoteConfigValue
        )
    }

    fun continueWithSelectedGoal(onComplete: () -> Unit) {
        val goalId = _uiState.value.selectedGoalId ?: return
        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            saveSelectedGoal(goalId)
            analyticsTracker.track(
                GrowthAnalyticsEvent.onboardingCompleted(
                    goalId = goalId.analyticsValue,
                    variant = onboardingVariant.remoteConfigValue
                )
            )
            _uiState.update { it.copy(isSaving = false) }
            onComplete()
        }
    }
}
