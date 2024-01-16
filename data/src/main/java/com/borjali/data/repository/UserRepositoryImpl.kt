package com.borjali.data.repository

import com.borjali.data.datasource.network.api.UserApi
import com.borjali.data.datasource.network.utils.NetworkBoundResource
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.model.user.LoginResponse
import com.borjali.domain.model.user.LoginRequestPostDto
import com.borjali.domain.model.user.UserDto
import com.borjali.data.DataConstants.ACCESS_TOKEN
import com.borjali.domain.repository.UserRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.DataState.ERROR
import com.borjali.domain.utils.DataState.SUCCESS
import com.borjali.domain.utils.StateMessage
import com.borjali.data.DataConstants.REFRESH_TOKEN

import com.borjali.data.datasource.network.api.CleanAppApi
import com.borjali.domain.Constants

import com.borjali.domain.Constants.Companion.IS_LOGGED_IN
import com.borjali.domain.model.user.sealed.LoginErrorType
import com.borjali.domain.model.user.sealed.LoginErrorType.*

import com.borjali.domain.preference.CleanAppPreferences
import com.borjali.domain.utils.MessageType
import com.borjali.domain.utils.UIComponentType
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.UserViewState

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

/**
 * Implementation class of [UserRepository]
 */
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val preferences: CleanAppPreferences,
    private val cleanAppApi: CleanAppApi,
) : UserRepository {

    /**
     * Login this function will authorize the user online and will set it locally
     * if authorized successfully.
     *
     * @param email
     * @param password
     * that contains the login credentials
     * @return a flow of the logged in user wrapped with DataState if authorized successfully
     */
    override fun login(email: String, password: String): Flow<DataState<UserViewState>> = flow {
        val inputValidation = isValidForLogin(email, password)
        if (inputValidation is Success) {

            emitAll(
                object : NetworkBoundResource<LoginResponse, UserDto, UserViewState>(
                    apiCall = { userApi.login(
                        LoginRequestPostDto(
                            userName = email,
                            password
                        ),
                        preferences.getString(Constants.LOCALE, Constants.GERMANY)
                    ) }
                ) {

                    override suspend fun handleNetworkSuccess(
                        response: ServerResponse<LoginResponse?>?
                    ): DataState<UserViewState> {
                        Timber.d("Token net is $response")

                            preferences.setString(ACCESS_TOKEN, response?.data?.access_token.toString())
                            preferences.setString(REFRESH_TOKEN, response?.data?.refresh_token.toString())
                            preferences.setBool(IS_LOGGED_IN, true)
                            return SUCCESS(
                                stateOfView = StateOfView.UserLogin,
                                stateMessage =
                                StateMessage(
                                    message = response?.message,
                                    uiComponentType = UIComponentType.SNACKBAR,
                                    messageType = MessageType.SUCCESS
                                )
                            )
                    }
                }.result
            )
        } else {
            emit(
                ERROR(
                    data = UserViewState(errorType = inputValidation)
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    /**
     * this function will check email valid the user  and will send  email to user
     * if response successfully.
     *
     * @param email
     *
     * @return a flow of success or error state wrapped with DataState.
     */
    override fun forgetPassword(email: String): Flow<DataState<UserViewState>> = flow {
        val emailValidation = isValidForgetPassword(email)
        if (emailValidation is Success) {
            emitAll(
                object : NetworkBoundResource<UserDto, UserDto, UserViewState>(
                    apiCall = { userApi.forgetPassword(
                        LoginRequestPostDto(
                            email = email
                        )
                    ) }
                ) {
                    override suspend fun handleNetworkSuccess(response: ServerResponse<UserDto?>?): DataState<UserViewState> {
                        return SUCCESS(
                            stateOfView = StateOfView.ForgetPassword,
                            stateMessage =
                            StateMessage(
                                message = response?.message,
                                uiComponentType = UIComponentType.SNACKBAR,
                                messageType = MessageType.SUCCESS
                            )
                        )
                    }
                }.result
            )
        } else {
            emit(
                ERROR(
                    data = UserViewState(errorType = emailValidation)
                )
            )
        }
    }

    /**
     * Logout this function will change the current user to the guest user.
     *
     * @return a flow of the guest user wrapped with DataState
     */
    override fun tokenSignOut(stateOfView: StateOfView): Flow<DataState<UserViewState>> = flow {
        preferences.setBool(IS_LOGGED_IN, false)
        emit(SUCCESS(stateOfView = stateOfView))
    }

    /**
     * this function check user is logged in.
     *
     * @return a flow of the state of user wrapped with DataState
     */
    override fun isLoggedIn(): Flow<DataState<UserViewState>> = flow {
            emit(SUCCESS(UserViewState(userIsLogin = true)))
    }.flowOn(Dispatchers.IO)

    override fun logOutUser(stateOfView: StateOfView) = flow {

        emitAll(
            object : NetworkBoundResource<Unit, Unit, UserViewState>(
                apiCall = { cleanAppApi.logOutUser() }) {
                override suspend fun handleNetworkSuccess(
                    response: ServerResponse<Unit?>?
                ): DataState<UserViewState> {
                    preferences.setBool(IS_LOGGED_IN, false)

                  return SUCCESS(stateOfView = stateOfView)
                }

            }.result
        )
    }

    /**
     *  this function will check email and password valid regex or not .
     *
     *  @param email
     *  @param password
     *
     * @return a type of LoginErrorType
     */
    private fun isValidForLogin(email: String, password: String): LoginErrorType {
        return when {
            email.isEmpty() -> EmailFieldHasError
            !email.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() } ->
                EmailFieldNotMatch
            password.isEmpty() -> PasswordFieldHasError
            password.length < 6 -> PasswordFieldLengthError
            else -> Success
        }
    }

    /**
     *  this function will check email valid regex or not .
     *
     *  @param email
     *
     * @return a type of LoginErrorType
     */
    private fun isValidForgetPassword(email: String): LoginErrorType {
        return when {
            email.isEmpty() -> EmailFieldHasError
            !email.let { android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches() } ->
                EmailFieldNotMatch
            else -> Success
        }
    }
}
