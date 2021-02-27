package ir.borjali.mvi.ui.main.viewstate

import ir.borjali.mvi.data.model.User

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Users(val users: List<User>) : MainState()
    data class Errors(val error: String?) : MainState()

}