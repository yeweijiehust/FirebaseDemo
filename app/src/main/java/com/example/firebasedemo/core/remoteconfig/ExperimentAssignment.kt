package com.example.firebasedemo.core.remoteconfig

data class ExperimentAssignment(
    val config: GrowthExperimentConfig,
    val refreshResult: RemoteConfigRefreshResult
)
