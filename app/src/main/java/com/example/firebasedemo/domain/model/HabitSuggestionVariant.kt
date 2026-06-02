package com.example.firebasedemo.domain.model

enum class HabitSuggestionVariant(val remoteConfigValue: String) {
    POPULAR("popular"),
    PERSONALIZED("personalized");

    companion object {
        fun fromRemoteConfigValue(value: String): HabitSuggestionVariant? {
            return entries.firstOrNull { it.remoteConfigValue == value }
        }
    }
}
