package com.example.firebasedemo.core.analytics

interface AnalyticsTracker {
    fun track(event: AnalyticsEvent)
    fun setUserProperty(name: String, value: String?)
}
