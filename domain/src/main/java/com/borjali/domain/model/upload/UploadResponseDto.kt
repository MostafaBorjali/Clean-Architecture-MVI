package com.borjali.domain.model.upload

import com.google.gson.annotations.SerializedName

data class UploadResponseDto(
    @SerializedName("file_url")
    val fileUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
)
