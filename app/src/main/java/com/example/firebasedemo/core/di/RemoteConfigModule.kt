package com.example.firebasedemo.core.di

import com.example.firebasedemo.core.remoteconfig.ExperimentConfigProvider
import com.example.firebasedemo.core.remoteconfig.FirebaseExperimentConfigProvider
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
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
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance().also {
            it.setConfigSettingsAsync(FirebaseExperimentConfigProvider.settings())
        }
    }
}
