@file:Suppress("unused")

package com.borjali.domain.model.base

import com.google.gson.annotations.SerializedName

class ServerResponse<T>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("meta")
    val meta: Meta?,
)
