package com.example.firebasedemo.core.remoteconfig

import javax.inject.Inject

class RefreshExperimentConfigUseCase @Inject constructor(
    private val configProvider: ExperimentConfigProvider
) {
    suspend operator fun invoke(): ExperimentAssignment {
        val result = configProvider.refresh()
        return ExperimentAssignment(
            config = configProvider.currentConfig(),
            refreshResult = result
        )
    }
}
