package com.example.firebasedemo.testing

import com.example.firebasedemo.core.analytics.AnalyticsEvent
import com.example.firebasedemo.core.analytics.AnalyticsTracker

class RecordingAnalyticsTracker : AnalyticsTracker {
    val events = mutableListOf<AnalyticsEvent>()
    val userProperties = mutableMapOf<String, String?>()

    override fun track(event: AnalyticsEvent) {
        events += event
    }

    override fun setUserProperty(name: String, value: String?) {
        userProperties[name] = value
    }
}
