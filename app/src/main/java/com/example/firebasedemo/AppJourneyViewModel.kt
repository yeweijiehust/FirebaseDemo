package com.example.firebasedemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasedemo.domain.usecase.GetJourneyStartPointUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AppJourneyViewModel @Inject constructor(
    private val getJourneyStartPoint: GetJourneyStartPointUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppJourneyUiState())
    val uiState: StateFlow<AppJourneyUiState> = _uiState.asStateFlow()

    fun resolveStartPoint() {
        if (_uiState.value.isReady) {
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    startPoint = getJourneyStartPoint(),
                    isReady = true
                )
            }
        }
    }
}
