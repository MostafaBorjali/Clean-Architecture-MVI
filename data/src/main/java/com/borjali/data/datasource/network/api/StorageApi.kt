package com.borjali.data.datasource.network.api

import com.borjali.data.datasource.network.ApiTablesNames
import com.borjali.domain.model.upload.UploadResponseDto
import com.borjali.domain.model.base.ServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * BiozoonApi retrofit interface.
 */
interface StorageApi {
    /**
     * upload file request.
     *
     * @param file
     * @param prefix
     * that contains the retrofit requestBody and prefix text
     *
     * @return the state message success or error
     * wrapped with UploadResponseDto for replace url by uri
     */
    @Multipart
    @POST(ApiTablesNames.upload)
    suspend fun upload(@Part file: MultipartBody.Part?, @Part("prefix") prefix: RequestBody): ServerResponse<UploadResponseDto?>
}
