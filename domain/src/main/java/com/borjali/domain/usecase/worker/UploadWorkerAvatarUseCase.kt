package com.borjali.domain.usecase.worker

import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.repository.WorkerRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.WorkerViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

/**
 * Use Case class for upload worker image and retrieve data from worker screen .
 *
 * @param repository the instance of WorkerRepository.
 */
class UploadWorkerAvatarUseCase(private val repository: WorkerRepository) {

    fun invoke(workerDto: WorkerDto, file: File, stateOfView: StateOfView): Flow<DataState<WorkerViewState>> {
        return repository.uploadWorkerAvatar(workerDto,file,stateOfView).flowOn(IO)
    }
}
