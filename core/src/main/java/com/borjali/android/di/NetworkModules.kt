package com.borjali.android.di


import com.borjali.android.BuildConfig
import com.borjali.data.DataConstants.TIME_OUT_API
import com.borjali.data.datasource.network.api.CleanAppApi
import com.borjali.data.datasource.network.api.StorageApi
import com.borjali.data.datasource.network.api.UserApi
import com.borjali.data.datasource.network.utils.TokenInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////// AUTH RETROFIT MODULE //////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Provides
    @Singleton
    fun buildAuthClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_API, TimeUnit.SECONDS) // write timeout
            readTimeout(TIME_OUT_API, TimeUnit.SECONDS) // read timeout
            addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                }
            )
            if (BuildConfig.DEBUG)
                addNetworkInterceptor(StethoInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun provideAuthRetrofitService(): UserApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(buildAuthClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApi::class.java)

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////  RETROFIT With Token  MODULE //////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Singleton
    @Provides
    fun provideCleanAppClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            connectTimeout(TIME_OUT_API, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_API, TimeUnit.SECONDS) // write timeout
            readTimeout(TIME_OUT_API, TimeUnit.SECONDS) // read timeout
            addInterceptor(tokenInterceptor)
            authenticator(tokenInterceptor)
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
                addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideCleanAppRetrofitService(tokenInterceptor: TokenInterceptor, gson: Gson):
            CleanAppApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(provideCleanAppClient(tokenInterceptor))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(CleanAppApi::class.java)

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////// STORAGE RETROFIT MODULE ///////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Singleton
    @Provides
    fun provideStorageClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
            callTimeout(1, TimeUnit.HOURS)
            connectTimeout(1, TimeUnit.HOURS)
            writeTimeout(1, TimeUnit.HOURS) // write timeout
            readTimeout(1, TimeUnit.HOURS) // read timeout
            retryOnConnectionFailure(true)
            addInterceptor(tokenInterceptor)
            authenticator(tokenInterceptor)
            protocols(Collections.singletonList(Protocol.HTTP_1_1))
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(StethoInterceptor())
                addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                )
            }
        }.build()
    }

    @Singleton
    @Provides
    fun provideStorageRetrofitService(tokenInterceptor: TokenInterceptor):
            StorageApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(provideStorageClient(tokenInterceptor))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StorageApi::class.java)
}
