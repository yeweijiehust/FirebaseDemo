package com.example.firebasedemo.core.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.remoteconfig.FirebaseExperimentConfigProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteConfigBindingModule {
    @Binds
    fun bindExperimentConfigProvider(
        implementation: FirebaseExperimentConfigProvider
    ): ExperimentConfigProvider
}

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigProviderModule {
    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(
        @ApplicationContext context: Context
    ): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance().also {
            it.setConfigSettingsAsync(
                FirebaseExperimentConfigProvider.settings(
                    isDebugBuild = context.isDebuggable()
                )
            )
        }
    }

    private fun Context.isDebuggable(): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    }
}
