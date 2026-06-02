package com.example.firebasedemo.core.di

import com.example.firebasedemo.core.time.SystemTodayProvider
import com.example.firebasedemo.core.time.TodayProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TimeModule {
    @Binds
    fun bindTodayProvider(
        implementation: SystemTodayProvider
    ): TodayProvider
}
