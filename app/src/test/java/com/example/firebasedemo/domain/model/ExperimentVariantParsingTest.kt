package com.example.firebasedemo.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ExperimentVariantParsingTest {
    @Test
    fun onboardingVariantParsesKnownValues() {
        assertEquals(OnboardingVariant.CONTROL, OnboardingVariant.fromRemoteConfigValue("control"))
        assertEquals(OnboardingVariant.GUIDED, OnboardingVariant.fromRemoteConfigValue("guided"))
        assertNull(OnboardingVariant.fromRemoteConfigValue("surprise"))
    }

    @Test
    fun habitSuggestionVariantParsesKnownValues() {
        assertEquals(HabitSuggestionVariant.POPULAR, HabitSuggestionVariant.fromRemoteConfigValue("popular"))
        assertEquals(HabitSuggestionVariant.PERSONALIZED, HabitSuggestionVariant.fromRemoteConfigValue("personalized"))
        assertNull(HabitSuggestionVariant.fromRemoteConfigValue("randomized"))
    }

    @Test
    fun homeHeadlineVariantParsesKnownValues() {
        assertEquals(HomeHeadlineVariant.PROGRESS, HomeHeadlineVariant.fromRemoteConfigValue("progress"))
        assertEquals(HomeHeadlineVariant.STREAK, HomeHeadlineVariant.fromRemoteConfigValue("streak"))
        assertNull(HomeHeadlineVariant.fromRemoteConfigValue("points"))
    }
}
