package com.borjali.android.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.borjali.domain.usecase.user.CheckUserIsLoginUseCase
import com.borjali.domain.usecase.user.ForgetPasswordUseCase
import com.borjali.domain.usecase.user.LoginUseCase
import com.borjali.domain.usecase.user.TokenSignOutUseCase
import com.borjali.domain.usecase.user.UserLogOutUseCase
import com.borjali.domain.usecase.worker.GetWorkersOfProductsUseCase
import com.borjali.domain.usecase.worker.UploadWorkerAvatarUseCase
import com.borjali.presentation.ui.worker.WorkerViewModel
import com.borjali.presentation.ui.login.LoginViewModel
import com.borjali.presentation.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun provideMainViewModel(
        userIsLoginUseCase: CheckUserIsLoginUseCase,
        tokenSignOutUseCase: TokenSignOutUseCase,
        userLogOutUseCase: UserLogOutUseCase
    ) = MainViewModel(userIsLoginUseCase, tokenSignOutUseCase,userLogOutUseCase)


    @Provides
    @Singleton
    fun provideLoginViewModel(
        loginUseCase: LoginUseCase,
        forgetPasswordUseCase: ForgetPasswordUseCase
    ) = LoginViewModel(loginUseCase, forgetPasswordUseCase)

    @Provides
    @Singleton
    fun provideWorkerViewModel(
        getWorkersOfProductsUseCase: GetWorkersOfProductsUseCase,
        uploadWorkerAvatarUseCase: UploadWorkerAvatarUseCase,
    ) = WorkerViewModel(
        getWorkersOfProductsUseCase =  getWorkersOfProductsUseCase,
        uploadWorkerAvatarUseCase = uploadWorkerAvatarUseCase,

    )

}
