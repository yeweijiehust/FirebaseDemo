package com.example.firebasedemo.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.analytics.GrowthUserProperty
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.time.TodayProvider
import com.example.firebasedemo.domain.model.HabitLogResult
import com.example.firebasedemo.domain.usecase.GetActiveHabitUseCase
import com.example.firebasedemo.domain.usecase.GetGrowthMetricsUseCase
import com.example.firebasedemo.domain.usecase.GetSelectedGoalUseCase
import com.example.firebasedemo.domain.usecase.LogHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    configProvider: ExperimentConfigProvider,
    private val todayProvider: TodayProvider,
    private val getSelectedGoal: GetSelectedGoalUseCase,
    private val getActiveHabit: GetActiveHabitUseCase,
    private val getGrowthMetrics: GetGrowthMetricsUseCase,
    private val logHabit: LogHabitUseCase,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val config = configProvider.currentConfig()
    private val _uiState = MutableStateFlow(
        HomeUiState(
            headlineVariant = config.homeHeadlineVariant,
            analyticsLabEnabled = config.analyticsLabEnabled
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        analyticsTracker.setUserProperty(
            GrowthUserProperty.HOME_HEADLINE_VARIANT,
            config.homeHeadlineVariant.remoteConfigValue
        )
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val today = todayProvider.today()
            val goal = getSelectedGoal()
            val habit = getActiveHabit()
            val metrics = getGrowthMetrics(today)
            _uiState.update {
                it.copy(
                    selectedGoalId = goal,
                    activeHabit = habit,
                    metrics = metrics,
                    loggedToday = metrics?.lastLoggedDate == today,
                    isLoading = false
                )
            }
            analyticsTracker.setUserProperty(
                GrowthUserProperty.ACTIVATION_STATUS,
                metrics?.activationStatus?.analyticsValue
            )
        }
    }

    fun logToday() {
        val habit = _uiState.value.activeHabit ?: return
        viewModelScope.launch {
            val today = todayProvider.today()
            val result = logHabit(habit.id, today)
            val metrics = getGrowthMetrics(today)
            when (result) {
                is HabitLogResult.Logged -> {
                    metrics?.let {
                        analyticsTracker.track(
                            GrowthAnalyticsEvent.habitLogged(
                                habitId = habit.id,
                                habitCategory = habit.category.analyticsValue,
                                streakCount = it.currentStreak,
                                completionCount = it.totalCompletions
                            )
                        )
                    }
                    _uiState.update {
                        it.copy(
                            metrics = metrics,
                            loggedToday = true,
                            message = "Habit logged for today"
                        )
                    }
                }

                is HabitLogResult.AlreadyLogged -> {
                    _uiState.update {
                        it.copy(
                            metrics = metrics,
                            loggedToday = true,
                            message = "Today is already logged"
                        )
                    }
                }
            }
            analyticsTracker.setUserProperty(
                GrowthUserProperty.ACTIVATION_STATUS,
                metrics?.activationStatus?.analyticsValue
            )
        }
    }
}
