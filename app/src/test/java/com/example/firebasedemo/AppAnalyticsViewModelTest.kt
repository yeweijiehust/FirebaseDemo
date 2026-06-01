package com.example.firebasedemo

import com.example.firebasedemo.core.analytics.AnalyticsEvent
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import org.junit.Assert.assertEquals
import org.junit.Test

class AppAnalyticsViewModelTest {
    private val analyticsTracker = RecordingAnalyticsTracker()
    private val viewModel = AppAnalyticsViewModel(analyticsTracker)

    @Test
    fun appOpenIsTrackedOnlyOnce() {
        viewModel.onAppShown()
        viewModel.onAppShown()

        assertEquals(
            listOf("app_opened"),
            analyticsTracker.events.map { it.name }
        )
    }

    @Test
    fun repeatedSameScreenIsTrackedOnce() {
        viewModel.onScreenShown("home")
        viewModel.onScreenShown("home")

        assertEquals(
            listOf("screen_viewed"),
            analyticsTracker.events.map { it.name }
        )
        assertEquals("home", analyticsTracker.events.single().parameters["screen_name"])
    }

    @Test
    fun differentScreensAreTrackedInOrder() {
        viewModel.onScreenShown("onboarding")
        viewModel.onScreenShown("habit_setup")
        viewModel.onScreenShown("home")

        assertEquals(
            listOf("onboarding", "habit_setup", "home"),
            analyticsTracker.events.map { it.parameters["screen_name"] }
        )
    }

    private class RecordingAnalyticsTracker : AnalyticsTracker {
        val events = mutableListOf<AnalyticsEvent>()
        val userProperties = mutableMapOf<String, String?>()

        override fun track(event: AnalyticsEvent) {
            events += event
        }

        override fun setUserProperty(name: String, value: String?) {
            userProperties[name] = value
        }
    }
}
