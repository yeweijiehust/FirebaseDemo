package com.example.firebasedemo.core.remoteconfig

import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import com.example.firebasedemo.domain.model.HabitSuggestionVariant
import com.example.firebasedemo.domain.model.HomeHeadlineVariant
import com.example.firebasedemo.domain.model.OnboardingVariant
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import javax.inject.Inject
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseExperimentConfigProvider @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val analyticsTracker: AnalyticsTracker
) : ExperimentConfigProvider {
    init {
        remoteConfig.setDefaultsAsync(RemoteConfigDefaults.firebaseDefaults)
    }

    override fun currentConfig(): GrowthExperimentConfig {
        return GrowthExperimentConfig(
            onboardingVariant = readOnboardingVariant(),
            suggestedHabitVariant = readSuggestedHabitVariant(),
            homeHeadlineVariant = readHomeHeadlineVariant(),
            analyticsLabEnabled = remoteConfig.getBoolean(ExperimentKey.ANALYTICS_LAB_ENABLED)
        )
    }

    override suspend fun refresh(): RemoteConfigRefreshResult {
        return try {
            if (remoteConfig.fetchAndActivate().awaitResult()) {
                analyticsTracker.track(GrowthAnalyticsEvent.remoteConfigActivated(Source.FIREBASE))
                RemoteConfigRefreshResult.Activated
            } else {
                RemoteConfigRefreshResult.Unchanged
            }
        } catch (exception: Exception) {
            val errorType = exception::class.simpleName ?: "unknown"
            analyticsTracker.track(GrowthAnalyticsEvent.remoteConfigFetchFailed(errorType))
            RemoteConfigRefreshResult.Failed(errorType)
        }
    }

    private fun readOnboardingVariant(): OnboardingVariant {
        val value = remoteConfig.getString(ExperimentKey.ONBOARDING_VARIANT)
        return OnboardingVariant.fromRemoteConfigValue(value)
            ?: RemoteConfigDefaults.growthExperimentConfig.onboardingVariant
    }

    private fun readSuggestedHabitVariant(): HabitSuggestionVariant {
        val value = remoteConfig.getString(ExperimentKey.SUGGESTED_HABIT_VARIANT)
        return HabitSuggestionVariant.fromRemoteConfigValue(value)
            ?: RemoteConfigDefaults.growthExperimentConfig.suggestedHabitVariant
    }

    private fun readHomeHeadlineVariant(): HomeHeadlineVariant {
        val value = remoteConfig.getString(ExperimentKey.HOME_HEADLINE_VARIANT)
        return HomeHeadlineVariant.fromRemoteConfigValue(value)
            ?: RemoteConfigDefaults.growthExperimentConfig.homeHeadlineVariant
    }

    private suspend fun <T> Task<T>.awaitResult(): T {
        return suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                if (continuation.isActive) {
                    continuation.resume(result)
                }
            }
            addOnFailureListener { exception ->
                if (continuation.isActive) {
                    continuation.resumeWithException(exception)
                }
            }
            addOnCanceledListener {
                if (continuation.isActive) {
                    continuation.cancel()
                }
            }
        }
    }

    object Source {
        const val FIREBASE = "firebase"
    }

    companion object {
        fun settings(): FirebaseRemoteConfigSettings {
            return FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build()
        }
    }
}
