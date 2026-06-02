package com.example.firebasedemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.core.remoteconfig.ExperimentExposureTracker
import com.example.firebasedemo.core.remoteconfig.RefreshExperimentConfigUseCase
import com.example.firebasedemo.navigation.GrowthHabitDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AppExperimentViewModel @Inject constructor(
    private val refreshExperimentConfig: RefreshExperimentConfigUseCase,
    private val exposureTracker: ExperimentExposureTracker
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppExperimentUiState())
    val uiState: StateFlow<AppExperimentUiState> = _uiState.asStateFlow()

    fun refreshConfig() {
        if (_uiState.value.isReady || _uiState.value.isRefreshing) {
            return
        }

        _uiState.update { it.copy(isRefreshing = true) }
        viewModelScope.launch {
            val assignment = refreshExperimentConfig()
            _uiState.update {
                it.copy(
                    config = assignment.config,
                    isReady = true,
                    isRefreshing = false,
                    refreshResult = assignment.refreshResult
                )
            }
        }
    }

    fun exposeScreenExperiments(destination: GrowthHabitDestination) {
        if (!_uiState.value.isReady) {
            return
        }

        exposureTracker.expose(destination, _uiState.value.config)
    }
}
