package com.borjali.data.datasource.network.utils

import com.borjali.data.DataConstants.ACCEPT_LANGUAGE
import com.borjali.data.DataConstants.ACCESS_TOKEN
import com.borjali.data.DataConstants.ANDROID
import com.borjali.data.DataConstants.APPLICATION_JSON
import com.borjali.data.DataConstants.AUTHORIZATION
import com.borjali.data.DataConstants.BEARER
import com.borjali.data.DataConstants.CONTENT_TYPE
import com.borjali.data.DataConstants.REFRESH_TOKEN
import com.borjali.data.DataConstants.X_PLATFORM
import com.borjali.data.datasource.network.api.UserApi
import com.borjali.domain.Constants.Companion.ENGLISH
import com.borjali.domain.Constants.Companion.GERMANY
import com.borjali.domain.Constants.Companion.LOCALE
import com.borjali.domain.model.event.EventSignOut
import com.borjali.domain.model.user.RefreshToken
import com.borjali.domain.preference.CleanAppPreferences
import com.borjali.domain.utils.EventOfCleanApp
import java.io.IOException
import javax.inject.Inject
import okhttp3.*

class TokenInterceptor
@Inject
constructor(
    private val preferences: CleanAppPreferences,
    private val userApi: UserApi,
) : Interceptor,
    Authenticator {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val requestBuilder = request.newBuilder()
        requestBuilder.addHeader(
            AUTHORIZATION,
            BEARER + preferences.getString(ACCESS_TOKEN)
        )
        requestBuilder.addHeader(X_PLATFORM, ANDROID)
        requestBuilder.addHeader(CONTENT_TYPE, APPLICATION_JSON)
        requestBuilder.addHeader(ACCEPT_LANGUAGE, preferences.getString(LOCALE, ENGLISH))
        return chain.proceed(requestBuilder.build())
    }

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            val responses = userApi
                .refreshToken(
                    RefreshToken(
                        preferences.getString(
                            REFRESH_TOKEN
                        )
                    )
                )
                .execute()
            if (responses.isSuccessful) {
                responses.body()?.data?.let {
                    preferences.setString(REFRESH_TOKEN, it.refresh_token)
                    preferences.setString(ACCESS_TOKEN, it.access_token)
                }
            } else {
                EventOfCleanApp.send(EventSignOut)
                return null
            }

            response.request.newBuilder()
                .header(AUTHORIZATION, BEARER + preferences.getString(ACCESS_TOKEN))
                .build()
        } catch (e: IOException) {
            return null
        }
    }
}
