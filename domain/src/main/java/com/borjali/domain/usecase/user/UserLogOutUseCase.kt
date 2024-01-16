package com.borjali.domain.usecase.user

import com.borjali.domain.repository.UserRepository
import com.borjali.domain.viewstate.StateOfView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn
/**
 * A UserLogOutUseCase class to wrap the log out action between the app layer
 * and retrieve data from main screen .
 *
 * @param repository the instance of UserRepository.
 */
class UserLogOutUseCase(private val repository: UserRepository) {
    fun invoke(stateOfView: StateOfView) = repository.logOutUser(stateOfView).flowOn(IO)
}
