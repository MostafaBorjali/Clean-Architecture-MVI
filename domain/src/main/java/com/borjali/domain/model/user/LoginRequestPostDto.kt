package com.borjali.domain.model.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRequestPostDto(
    @SerializedName("username")
    val userName: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("extra")
    val extra: ExtraRequest? = null,
    val fcmToken: String? = null
)

@Keep
data class ExtraRequest(
    @SerializedName("os")
    val os: String = "Android",
    @SerializedName("osVersion")
    val osVersion: String? = null,
    @SerializedName("appVersion")
    val appVersion: String? = null,
    @SerializedName("platform")
    val platform: String? = "ANDROID"

)
