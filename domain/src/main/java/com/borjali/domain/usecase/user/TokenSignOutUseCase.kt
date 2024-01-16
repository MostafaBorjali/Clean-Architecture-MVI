package com.borjali.domain.usecase.user


import com.borjali.domain.repository.UserRepository
import com.borjali.domain.viewstate.StateOfView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn

/**
 * A TokenSignOutUseCase class to wrap the log out action  when refresh token expire
 * and retrieve data from all screen .
 *
 * @param repository the instance of UserRepository.
 */
class TokenSignOutUseCase(private val repository: UserRepository) {
    fun invoke(stateOfView: StateOfView) = repository.tokenSignOut(stateOfView).flowOn(IO)
}
