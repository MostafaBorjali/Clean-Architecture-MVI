package com.borjali.domain.usecase.user

import com.borjali.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn

class CheckUserIsLoginUseCase(private val repository: UserRepository) {
    fun invoke() = repository.isLoggedIn().flowOn(IO)
}
