package com.example.firebasedemo.core.di

import android.content.Context
import com.example.firebasedemo.core.analytics.AnalyticsTracker
import com.example.firebasedemo.core.analytics.FirebaseAnalyticsTracker
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AnalyticsBindingModule {
    @Binds
    fun bindAnalyticsTracker(
        implementation: FirebaseAnalyticsTracker
    ): AnalyticsTracker
}

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsProviderModule {
    @Provides
    @Singleton
    fun provideFirebaseAnalytics(
        @ApplicationContext context: Context
    ): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }
}
