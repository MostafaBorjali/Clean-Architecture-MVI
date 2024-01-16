package com.borjali.presentation.ui.login


import dagger.hilt.android.lifecycle.HiltViewModel
import com.borjali.domain.usecase.user.ForgetPasswordUseCase
import com.borjali.domain.usecase.user.LoginUseCase
import com.borjali.domain.viewstate.UserViewState
import com.borjali.presentation.ui.base.BaseViewModel
import com.borjali.presentation.ui.login.LoginEventState.ForgetPasswordEvent
import com.borjali.presentation.ui.login.LoginEventState.LoginEvent
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
/**
 * view model for login manager
 */
@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val loginUseCase: LoginUseCase,
    private val forgetPasswordUseCase: ForgetPasswordUseCase
) :
    BaseViewModel<LoginEventState, UserViewState>() {

    override fun handleEventState(eventState: LoginEventState) =
        flow {
            when (eventState) {
                is LoginEvent -> emitAll(loginUseCase.invoke(eventState.email, eventState.password))
                is ForgetPasswordEvent -> emitAll(forgetPasswordUseCase.invoke(eventState.email))
            }
        }
}
