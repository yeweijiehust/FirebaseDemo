package com.example.firebasedemo

import com.example.firebasedemo.core.remoteconfig.GrowthExperimentConfig
import com.example.firebasedemo.core.remoteconfig.RemoteConfigDefaults
import com.example.firebasedemo.core.remoteconfig.RemoteConfigRefreshResult

data class AppExperimentUiState(
    val config: GrowthExperimentConfig = RemoteConfigDefaults.growthExperimentConfig,
    val isReady: Boolean = false,
    val isRefreshing: Boolean = false,
    val refreshResult: RemoteConfigRefreshResult? = null
)
