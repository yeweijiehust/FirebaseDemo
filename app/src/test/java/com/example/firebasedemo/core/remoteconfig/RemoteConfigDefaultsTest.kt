package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RemoteConfigDefaultsTest {
    @Test
    fun defaultsMatchDocumentedControlValues() {
        val config = RemoteConfigDefaults.growthExperimentConfig

        assertEquals(OnboardingVariant.CONTROL, config.onboardingVariant)
        assertEquals(HabitSuggestionVariant.POPULAR, config.suggestedHabitVariant)
        assertEquals(HomeHeadlineVariant.PROGRESS, config.homeHeadlineVariant)
        assertTrue(config.analyticsLabEnabled)
    }

    @Test
    fun firebaseDefaultsUseStableExperimentKeys() {
        assertEquals("control", RemoteConfigDefaults.firebaseDefaults[ExperimentKey.ONBOARDING_VARIANT])
        assertEquals("popular", RemoteConfigDefaults.firebaseDefaults[ExperimentKey.SUGGESTED_HABIT_VARIANT])
        assertEquals("progress", RemoteConfigDefaults.firebaseDefaults[ExperimentKey.HOME_HEADLINE_VARIANT])
        assertEquals(true, RemoteConfigDefaults.firebaseDefaults[ExperimentKey.ANALYTICS_LAB_ENABLED])
    }
}
