package com.borjali.domain.usecase.worker

import com.borjali.domain.repository.WorkerRepository
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.WorkerViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
/**
 * Use Case class for get worker list and retrieve data from worker screen .
 *
 * @param repository the instance of WorkerRepository.
 */
class GetWorkersOfProductsUseCase(private val repository: WorkerRepository) {

    fun invoke(stateOfView: StateOfView): Flow<DataState<WorkerViewState>> {
        return repository.getWorkerOfProducts(stateOfView).flowOn(IO)
    }
}
