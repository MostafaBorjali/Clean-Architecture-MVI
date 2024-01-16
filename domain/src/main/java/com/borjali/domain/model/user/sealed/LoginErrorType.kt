package com.borjali.domain.model.user.sealed

sealed class LoginErrorType {
    object None : LoginErrorType()
    object EmailFieldHasError : LoginErrorType()
    object EmailFieldNotMatch : LoginErrorType()
    object PasswordFieldHasError : LoginErrorType()
    object PasswordFieldLengthError : LoginErrorType()
    object UserRole : LoginErrorType()
    object Success : LoginErrorType()
}
