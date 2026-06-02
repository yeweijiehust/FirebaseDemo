package com.example.firebasedemo.domain.model

enum class OnboardingVariant(val remoteConfigValue: String) {
    CONTROL("control"),
    GUIDED("guided");

    companion object {
        fun fromRemoteConfigValue(value: String): OnboardingVariant? {
            return entries.firstOrNull { it.remoteConfigValue == value }
        }
    }
}
