package com.example.firebasedemo

import com.example.firebasedemo.domain.model.JourneyStartPoint

data class AppJourneyUiState(
    val startPoint: JourneyStartPoint = JourneyStartPoint.ONBOARDING,
    val isReady: Boolean = false
)
