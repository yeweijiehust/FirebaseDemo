package com.example.firebasedemo.core.di

import android.content.Context
import androidx.room.Room
import com.example.firebasedemo.data.local.dao.GrowthDao
import com.example.firebasedemo.data.local.database.GrowthDatabase
import com.example.firebasedemo.data.local.source.GrowthLocalDataSource
import com.example.firebasedemo.data.local.source.RoomGrowthLocalDataSource
import com.example.firebasedemo.data.repository.RoomGrowthRepository
import com.example.firebasedemo.domain.repository.GrowthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PersistenceBindingModule {
    @Binds
    fun bindGrowthLocalDataSource(
        implementation: RoomGrowthLocalDataSource
    ): GrowthLocalDataSource

    @Binds
    fun bindGrowthRepository(
        implementation: RoomGrowthRepository
    ): GrowthRepository
}

@Module
@InstallIn(SingletonComponent::class)
object PersistenceProviderModule {
    @Provides
    @Singleton
    fun provideGrowthDatabase(
        @ApplicationContext context: Context
    ): GrowthDatabase {
        return Room.databaseBuilder(
            context,
            GrowthDatabase::class.java,
            "growth_habit_lab.db"
        ).build()
    }

    @Provides
    fun provideGrowthDao(database: GrowthDatabase): GrowthDao {
        return database.growthDao()
    }
}
