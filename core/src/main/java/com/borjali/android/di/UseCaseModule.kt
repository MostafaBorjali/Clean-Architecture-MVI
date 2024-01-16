package com.borjali.android.di


import com.borjali.domain.repository.UserRepository
import com.borjali.domain.repository.WorkerRepository
import com.borjali.domain.usecase.user.CheckUserIsLoginUseCase
import com.borjali.domain.usecase.user.ForgetPasswordUseCase
import com.borjali.domain.usecase.user.LoginUseCase
import com.borjali.domain.usecase.user.TokenSignOutUseCase
import com.borjali.domain.usecase.user.UserLogOutUseCase
import com.borjali.domain.usecase.worker.GetWorkersOfProductsUseCase
import com.borjali.domain.usecase.worker.UploadWorkerAvatarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Provides
    fun provideLoginUseCase(userRepository: UserRepository) = LoginUseCase(userRepository)

    @Provides
    fun provideForgetPasswordUseCase(userRepository: UserRepository) =
        ForgetPasswordUseCase(userRepository)

    @Provides
    fun provideCheckUserIsLoginUseCase(userRepository: UserRepository) =
        CheckUserIsLoginUseCase(userRepository)

    @Provides
    fun provideGetUsersOfProjectsUseCase(workerRepository: WorkerRepository) =
        GetWorkersOfProductsUseCase(workerRepository)

    @Provides
    fun provideUploadWorkerAvatarUseCase(workerRepository: WorkerRepository) =
        UploadWorkerAvatarUseCase(workerRepository)

    @Provides
    fun provideTokenSignOutUseCase(userRepository: UserRepository) =
        TokenSignOutUseCase(userRepository)

    @Provides
    fun provideUserLogOutUseCase(userRepository: UserRepository) =
        UserLogOutUseCase(userRepository)

}
