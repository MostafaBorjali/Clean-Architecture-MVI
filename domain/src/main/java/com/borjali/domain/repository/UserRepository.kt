package com.borjali.domain.repository

import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.UserViewState
import kotlinx.coroutines.flow.Flow
/**
 * Methods of UserRepository
 */
interface UserRepository {
    /**
     * Login this function will authorize the user online and will set it locally
     * if authorized successfully.
     *
     * @param email
     * @param password
     * that contains the login credentials
     * @return a flow of the logged in user wrapped with DataState if authorized successfully
     */
    fun login(
        email: String,
        password: String
    ): Flow<DataState<UserViewState>>

    /**
     * this function will check email valid the user  and will send  email to user
     * if response successfully.
     *
     * @param email
     *
     * @return a flow of success or error state wrapped with DataState.
     */
    fun forgetPassword(email: String): Flow<DataState<UserViewState>>

    /**
     * Logout this function will change the current user to the guest user.
     *
     * @return a flow of the guest user wrapped with DataState
     */
    fun tokenSignOut(stateOfView: StateOfView): Flow<DataState<UserViewState>>

    /**
     * this function check user is logged-in.
     *
     * @return a flow of the state of user wrapped with DataState
     */
    fun isLoggedIn(): Flow<DataState<UserViewState>>

    /**
     * this function log out  user and clear local data.
     *
     * @return a flow of the state of user wrapped with DataState
     */
    fun logOutUser(stateOfView: StateOfView): Flow<DataState<UserViewState>>
}
