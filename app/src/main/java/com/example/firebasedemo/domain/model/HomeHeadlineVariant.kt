package com.example.firebasedemo.domain.model

enum class HomeHeadlineVariant(val remoteConfigValue: String) {
    PROGRESS("progress"),
    STREAK("streak");

    companion object {
        fun fromRemoteConfigValue(value: String): HomeHeadlineVariant? {
            return entries.firstOrNull { it.remoteConfigValue == value }
        }
    }
}
