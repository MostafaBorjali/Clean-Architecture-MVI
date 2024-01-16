package com.borjali.data.datasource.network

/**
 * ApiTablesNames is an object that contains all the api tables names
 */
object ApiTablesNames {
    const val user = "users"
    const val forgetPassword = "/v1/auth/forget-password"
    const val refreshToken = "/v1/auth/refresh-token"
    const val login = "/v1/auth/login"
    const val updateAvatar = "/v1/user/{id}"
    const val userLogOut = "/v1/user/logout"
    const val upload = "file"
    const val Workers = "/MostafaBorjali/FakeRestApi/workers"
}