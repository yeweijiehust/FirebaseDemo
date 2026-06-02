package com.example.firebasedemo.feature.analyticslab

import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.core.remoteconfig.ExperimentKey
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnalyticsLabReferenceTest {
    @Test
    fun eventCatalogMatchesAnalyticsVocabulary() {
        val expectedNames = setOf(
            GrowthAnalyticsEvent.Name.APP_OPENED,
            GrowthAnalyticsEvent.Name.SCREEN_VIEWED,
            GrowthAnalyticsEvent.Name.ONBOARDING_STARTED,
            GrowthAnalyticsEvent.Name.ONBOARDING_GOAL_SELECTED,
            GrowthAnalyticsEvent.Name.ONBOARDING_COMPLETED,
            GrowthAnalyticsEvent.Name.HABIT_SUGGESTED_SELECTED,
            GrowthAnalyticsEvent.Name.HABIT_CREATED,
            GrowthAnalyticsEvent.Name.HABIT_LOGGED,
            GrowthAnalyticsEvent.Name.PROGRESS_VIEWED,
            GrowthAnalyticsEvent.Name.EXPERIMENT_EXPOSED,
            GrowthAnalyticsEvent.Name.REMOTE_CONFIG_ACTIVATED,
            GrowthAnalyticsEvent.Name.REMOTE_CONFIG_FETCH_FAILED,
            GrowthAnalyticsEvent.Name.LEARNING_JOURNEY_RESET
        )

        assertEquals(expectedNames, AnalyticsLabReference.eventCatalog.map { it.name }.toSet())
    }

    @Test
    fun eventCatalogShowsParametersForEveryEvent() {
        assertTrue(AnalyticsLabReference.eventCatalog.all { it.parameters.isNotEmpty() })
    }

    @Test
    fun experimentCatalogMatchesRemoteConfigKeys() {
        val expectedKeys = setOf(
            ExperimentKey.ONBOARDING_VARIANT,
            ExperimentKey.SUGGESTED_HABIT_VARIANT,
            ExperimentKey.HOME_HEADLINE_VARIANT,
            ExperimentKey.ANALYTICS_LAB_ENABLED
        )

        assertEquals(expectedKeys, AnalyticsLabReference.experimentCatalog.map { it.key }.toSet())
    }
}
