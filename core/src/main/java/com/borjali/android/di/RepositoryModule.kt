package com.borjali.android.di

import com.borjali.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.borjali.data.repository.WorkerRepositoryImpl
import com.borjali.domain.repository.UserRepository
import com.borjali.domain.repository.WorkerRepository

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)

interface RepositoryModule {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////// User REPOSITORY ///////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindWorkerRepository(workerRepositoryImpl: WorkerRepositoryImpl): WorkerRepository

}
