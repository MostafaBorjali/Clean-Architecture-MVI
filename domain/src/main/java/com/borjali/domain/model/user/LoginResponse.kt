package com.borjali.domain.model.user

data class LoginResponse(
    val access_token: String,
    val refresh_token: String,
    val user: UserDto
)
