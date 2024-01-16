package com.borjali.presentation.ui.login

/**
 * set Event for login manager
 */
sealed class LoginEventState {

    /**
     * set Event Model for login manager
     */
    data class LoginEvent(val email: String, val password: String) : LoginEventState()

    /**
     * set Event Model for forget password
     */
    data class ForgetPasswordEvent(val email: String) : LoginEventState()
}
