package com.borjali.domain.viewstate

import com.borjali.domain.model.user.UserDto
import com.borjali.domain.model.user.sealed.LoginErrorType

data class UserViewState(
    val errorType: LoginErrorType = LoginErrorType.None,
    val user: UserDto? = null,
    var userIsLogin: Boolean? = false,
)
