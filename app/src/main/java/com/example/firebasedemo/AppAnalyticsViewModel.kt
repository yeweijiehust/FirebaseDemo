package com.example.firebasedemo

import androidx.lifecycle.ViewModel
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.GrowthAnalyticsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppAnalyticsViewModel @Inject constructor(
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {
    private var appOpenTracked = false
    private var lastTrackedScreenName: String? = null

    fun onAppShown() {
        if (appOpenTracked) {
            return
        }

        appOpenTracked = true
        analyticsTracker.track(GrowthAnalyticsEvent.appOpened())
    }

    fun onScreenShown(screenName: String) {
        if (screenName == lastTrackedScreenName) {
            return
        }

        lastTrackedScreenName = screenName
        analyticsTracker.track(GrowthAnalyticsEvent.screenViewed(screenName))
    }
}
