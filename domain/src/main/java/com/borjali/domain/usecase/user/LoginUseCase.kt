package com.borjali.domain.usecase.user

import com.borjali.domain.repository.UserRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.UserViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * A LoginUseCase class to wrap the log in action between the app layer
 * and retrieve data from all screen .
 *
 * @param repository the instance of UserRepository.
 */
class LoginUseCase(private val repository: UserRepository) {

    fun invoke(email: String, password: String): Flow<DataState<UserViewState>> {
        return repository.login(email, password).flowOn(IO)
    }
}
