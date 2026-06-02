package com.example.firebasedemo.feature.analyticslab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.domain.usecase.ResetLearningJourneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AnalyticsLabViewModel @Inject constructor(
    private val resetLearningJourney: ResetLearningJourneyUseCase,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnalyticsLabUiState())
    val uiState: StateFlow<AnalyticsLabUiState> = _uiState.asStateFlow()

    fun resetJourney(onComplete: () -> Unit) {
        if (_uiState.value.isResetting) {
            return
        }

        _uiState.update { it.copy(isResetting = true) }
        viewModelScope.launch {
            resetLearningJourney()
            analyticsTracker.track(
                GrowthAnalyticsEvent.learningJourneyReset(source = Source.ANALYTICS_LAB)
            )
            analyticsTracker.setUserProperty(GrowthUserProperty.ONBOARDING_GOAL, null)
            analyticsTracker.setUserProperty(GrowthUserProperty.ACTIVATION_STATUS, null)
            _uiState.update { it.copy(isResetting = false) }
            onComplete()
        }
    }

    object Source {
        const val ANALYTICS_LAB = "analytics_lab"
    }
}
