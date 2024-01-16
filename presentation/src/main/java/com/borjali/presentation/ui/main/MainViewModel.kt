package com.borjali.presentation.ui.main


import dagger.hilt.android.lifecycle.HiltViewModel
import com.borjali.domain.usecase.user.CheckUserIsLoginUseCase
import com.borjali.domain.usecase.user.TokenSignOutUseCase
import com.borjali.domain.usecase.user.UserLogOutUseCase
import com.borjali.domain.viewstate.UserViewState
import com.borjali.presentation.ui.base.BaseViewModel
import com.borjali.presentation.ui.main.MainEventState.LogOutUser
import com.borjali.presentation.ui.main.MainEventState.TokenSignOut
import com.borjali.presentation.ui.main.MainEventState.UserIsLogin
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
/**
 * shared view model for handle general action
 */
@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val userIsLoginUseCase: CheckUserIsLoginUseCase,
    private val tokenSignOutUseCase: TokenSignOutUseCase,
    private val logOutUseCase: UserLogOutUseCase,
) :
    BaseViewModel<MainEventState, UserViewState>() {

    override fun handleEventState(eventState: MainEventState) =
        flow {
            when (eventState) {
                is UserIsLogin -> emitAll(userIsLoginUseCase.invoke())

                is TokenSignOut -> emitAll(tokenSignOutUseCase.invoke(eventState.stateOfView))

                is LogOutUser -> emitAll(logOutUseCase.invoke(eventState.stateOfView))
            }
        }
}
