package com.example.firebasedemo.core.analytics

data class AnalyticsEvent(
    val name: String,
    val parameters: Map<String, String> = emptyMap()
)
