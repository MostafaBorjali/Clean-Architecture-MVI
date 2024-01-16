package com.borjali.domain.usecase.user

import com.borjali.domain.repository.UserRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.UserViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ForgetPasswordUseCase(private val repository: UserRepository) {

    fun invoke(email: String): Flow<DataState<UserViewState>> {
        return repository.forgetPassword(email).flowOn(IO)
    }
}
