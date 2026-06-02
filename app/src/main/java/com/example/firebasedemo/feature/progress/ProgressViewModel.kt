package com.example.firebasedemo.feature.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.time.TodayProvider
import com.example.firebasedemo.domain.usecase.GetActiveHabitUseCase
import com.example.firebasedemo.domain.usecase.GetGrowthMetricsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val todayProvider: TodayProvider,
    private val getActiveHabit: GetActiveHabitUseCase,
    private val getGrowthMetrics: GetGrowthMetricsUseCase,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val habit = getActiveHabit()
            val metrics = getGrowthMetrics(todayProvider.today())
            _uiState.update {
                it.copy(
                    activeHabit = habit,
                    metrics = metrics,
                    isLoading = false
                )
            }
            metrics?.let {
                analyticsTracker.track(
                    GrowthAnalyticsEvent.progressViewed(
                        streakCount = it.currentStreak,
                        completionCount = it.totalCompletions,
                        activationStatus = it.activationStatus.analyticsValue
                    )
                )
            }
        }
    }
}
