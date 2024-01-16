package com.borjali.data.datasource.network.api

import com.borjali.data.DataConstants.ACCEPT_LANGUAGE
import com.borjali.data.datasource.network.ApiTablesNames.forgetPassword
import com.borjali.data.datasource.network.ApiTablesNames.login
import com.borjali.data.datasource.network.ApiTablesNames.refreshToken
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.model.user.LoginRequestPostDto
import com.borjali.domain.model.user.LoginResponse
import com.borjali.domain.model.user.RefreshToken
import com.borjali.domain.model.user.UserDto
import retrofit2.Call
import retrofit2.http.*

/**
 * UserApi retrofit interface.
 */
interface UserApi {

    /**
     * Authenticate request.
     *
     * @param loginRequestPostDto that contains the login credentials
     * @return the user token
     */
    @POST(login)
    suspend fun login(
        @Body loginRequestPostDto: LoginRequestPostDto,
        @Header(ACCEPT_LANGUAGE) language: String
    ): ServerResponse<LoginResponse?>

    /**
     * refresh request.
     *
     * @param refreshToken that contains the refresh token
     * @return the user token
     */
    @POST(refreshToken)
    fun refreshToken(@Body refreshToken: RefreshToken):
            Call<ServerResponse<LoginResponse?>>

    /**
     * forgetPassword request.
     *
     * @param loginRequestPostDto that contains the email for send forgetPassword link
     * @return the state message success or error wrapped with UserDto
     */
    @POST(forgetPassword)
    suspend fun forgetPassword(
        @Body loginRequestPostDto: LoginRequestPostDto
    ): ServerResponse<UserDto?>
}
