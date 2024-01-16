package com.borjali.data.datasource.network.api

import com.borjali.data.datasource.network.ApiTablesNames.Workers
import com.borjali.data.datasource.network.ApiTablesNames.updateAvatar
import com.borjali.data.datasource.network.ApiTablesNames.userLogOut
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.model.worker.AvatarDto
import com.borjali.domain.model.worker.WorkerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * The main services that handles all endpoint processes with token
 */
interface CleanAppApi {
    /**
     * Get worker list
     * @return list of worker
     */
    @GET(Workers)
    suspend fun getWorkersOfProducts(): ServerResponse<List<WorkerDto>?>

    /**
     * update user avatar
     * @param workerId
     * @return nothing
     */
    @PUT(updateAvatar)
    suspend fun updateWorkerAvatar(
        @Path("id") workerId: String,
        @Body avatarDto: AvatarDto
    ): ServerResponse<Unit?>

    /**
     * log out user
     */
    @POST(userLogOut)
    suspend fun logOutUser(): ServerResponse<Unit?>
}
