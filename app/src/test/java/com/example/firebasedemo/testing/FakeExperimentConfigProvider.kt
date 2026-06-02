package com.example.firebasedemo.testing

import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.remoteconfig.GrowthExperimentConfig
import com.example.firebasedemo.core.remoteconfig.RemoteConfigDefaults
import com.example.firebasedemo.core.remoteconfig.RemoteConfigRefreshResult

class FakeExperimentConfigProvider(
    private val config: GrowthExperimentConfig = RemoteConfigDefaults.growthExperimentConfig
) : ExperimentConfigProvider {
    override fun currentConfig(): GrowthExperimentConfig {
        return config
    }

    override suspend fun refresh(): RemoteConfigRefreshResult {
        return RemoteConfigRefreshResult.Unchanged
    }
}
