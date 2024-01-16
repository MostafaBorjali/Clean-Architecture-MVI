package com.borjali.android

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.borjali.domain.Constants.Companion.ENGLISH
import com.facebook.stetho.Stetho
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp

import com.borjali.domain.Constants.Companion.GERMANY
import com.borjali.domain.Constants.Companion.LOCALE
import com.borjali.domain.model.base.CurrentLanguage
import com.borjali.domain.preference.CleanAppPreferences

import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var preferences: CleanAppPreferences


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
        initYandexMetrica()
        setLocale()
    }
    private fun setLocale() {
        CurrentLanguage.language = preferences.getString(LOCALE, ENGLISH)
    }

    private fun initYandexMetrica() {
        val config = YandexMetricaConfig.newConfigBuilder("b4c56e0b-8130-493e-b3e4-4dbf20fbc6f6")
            .withLogs()
            .build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
