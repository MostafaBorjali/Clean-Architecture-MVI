package com.borjali.android.di

import android.app.Application
import androidx.room.Room
import com.borjali.data.datasource.cache.CleanAppDatabase
import com.borjali.data.datasource.cache.EntityConverters.initialize
import com.borjali.data.datasource.cache.worker.WorkerDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    private val databaseName = "clean_app_database"

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setPrettyPrinting()
        return gsonBuilder.create()
    }

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application,
    ): CleanAppDatabase {
        initialize(provideGson())
        return Room.databaseBuilder(application, CleanAppDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Singleton
    @Provides
    fun provideWorkerDao(appDatabase: CleanAppDatabase): WorkerDao = appDatabase.workerDao()

}
