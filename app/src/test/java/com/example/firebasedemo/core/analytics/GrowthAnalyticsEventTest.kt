package com.example.firebasedemo.core.analytics

import org.junit.Assert.assertEquals
import org.junit.Test

class GrowthAnalyticsEventTest {
    @Test
    fun screenViewedUsesStableNameAndScreenParameter() {
        val event = GrowthAnalyticsEvent.screenViewed("home")

        assertEquals("screen_viewed", event.name)
        assertEquals(mapOf("screen_name" to "home"), event.parameters)
    }

    @Test
    fun habitLoggedConvertsCountsToParameters() {
        val event = GrowthAnalyticsEvent.habitLogged(
            habitId = "focus_sprint",
            habitCategory = "focus",
            streakCount = 3,
            completionCount = 7
        )

        assertEquals("habit_logged", event.name)
        assertEquals(
            mapOf(
                "habit_id" to "focus_sprint",
                "habit_category" to "focus",
                "streak_count" to "3",
                "completion_count" to "7"
            ),
            event.parameters
        )
    }

    @Test
    fun experimentExposedIncludesKeyVariantAndScreen() {
        val event = GrowthAnalyticsEvent.experimentExposed(
            experimentKey = "home_headline_variant",
            variant = "streak",
            screenName = "home"
        )

        assertEquals("experiment_exposed", event.name)
        assertEquals(
            mapOf(
                "experiment_key" to "home_headline_variant",
                "variant" to "streak",
                "screen_name" to "home"
            ),
            event.parameters
        )
    }
}
