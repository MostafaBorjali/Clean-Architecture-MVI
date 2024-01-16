package com.borjali.android.di

import android.content.Context
import android.util.Log
import androidx.multidex.BuildConfig
import com.borjali.data.datasource.cache.preferences.CleanAppPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.borjali.domain.preference.CleanAppPreferences
import javax.inject.Singleton
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
class CoreModules {
    @Provides
    fun provideSecurePreferences(context: Context): CleanAppPreferences =
       CleanAppPreferencesImpl(context)

    @Provides
    @Singleton
    fun provideTimberTree(): Timber.Tree =
        object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int) =
                BuildConfig.DEBUG || priority >= Log.INFO
        }

    @Provides
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }


}
