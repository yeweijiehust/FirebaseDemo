package com.example.firebasedemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.remoteconfig.ExperimentKey
import com.example.firebasedemo.core.remoteconfig.GrowthExperimentConfig
import com.example.firebasedemo.core.remoteconfig.RemoteConfigDefaults
import com.example.firebasedemo.navigation.GrowthHabitDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AppExperimentViewModel @Inject constructor(
    private val configProvider: ExperimentConfigProvider,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private val _config = MutableStateFlow(RemoteConfigDefaults.growthExperimentConfig)
    val config: StateFlow<GrowthExperimentConfig> = _config.asStateFlow()
    private val exposedExperiments = mutableSetOf<String>()

    init {
        _config.value = configProvider.currentConfig()
    }

    fun refreshConfig() {
        viewModelScope.launch {
            configProvider.refresh()
            _config.value = configProvider.currentConfig()
        }
    }

    fun exposeScreenExperiments(destination: GrowthHabitDestination) {
        when (destination) {
            GrowthHabitDestination.Onboarding -> expose(
                experimentKey = ExperimentKey.ONBOARDING_VARIANT,
                variant = _config.value.onboardingVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.HabitSetup -> expose(
                experimentKey = ExperimentKey.SUGGESTED_HABIT_VARIANT,
                variant = _config.value.suggestedHabitVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.Home -> expose(
                experimentKey = ExperimentKey.HOME_HEADLINE_VARIANT,
                variant = _config.value.homeHeadlineVariant.remoteConfigValue,
                screenName = destination.screenName
            )

            GrowthHabitDestination.AnalyticsLab,
            GrowthHabitDestination.Progress -> Unit
        }
    }

    private fun expose(
        experimentKey: String,
        variant: String,
        screenName: String
    ) {
        val exposureId = "$experimentKey:$screenName"
        if (!exposedExperiments.add(exposureId)) {
            return
        }

        analyticsTracker.track(
            GrowthAnalyticsEvent.experimentExposed(
                experimentKey = experimentKey,
                variant = variant,
                screenName = screenName
            )
        )
    }
}
