package com.borjali.presentation.ui.main

import com.borjali.domain.viewstate.StateOfView
/**
 * set Event for general action
 */
sealed class MainEventState {
    /**
     * set Event Model for log out when refresh token unAuthorize
     */
    data class TokenSignOut(val stateOfView: StateOfView) : MainEventState()
    /**
     * set Event Model for check user is login
     */
    object UserIsLogin : MainEventState()
    /**
     * set Event Model for log out manager
     */
    data class LogOutUser(val stateOfView: StateOfView) : MainEventState()
}

