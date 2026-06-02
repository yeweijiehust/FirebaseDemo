package com.example.firebasedemo.core.remoteconfig

interface ExperimentConfigProvider {
    fun currentConfig(): GrowthExperimentConfig
    suspend fun refresh(): RemoteConfigRefreshResult
}
