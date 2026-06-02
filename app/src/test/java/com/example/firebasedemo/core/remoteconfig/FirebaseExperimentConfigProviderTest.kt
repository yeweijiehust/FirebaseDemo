package com.example.firebasedemo.core.remoteconfig

import org.junit.Assert.assertEquals
import org.junit.Test

class FirebaseExperimentConfigProviderTest {
    @Test
    fun debugSettingsAllowImmediateFetches() {
        val settings = FirebaseExperimentConfigProvider.settings(isDebugBuild = true)

        assertEquals(0L, settings.minimumFetchIntervalInSeconds)
    }

    @Test
    fun releaseSettingsKeepOneHourFetchInterval() {
        val settings = FirebaseExperimentConfigProvider.settings(isDebugBuild = false)

        assertEquals(3600L, settings.minimumFetchIntervalInSeconds)
    }
}
