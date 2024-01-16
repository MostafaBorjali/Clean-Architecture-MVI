package com.borjali.domain.repository

import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.utils.DataState
import com.borjali.domain.viewstate.StateOfView
import com.borjali.domain.viewstate.WorkerViewState
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Methods of WorkerRepository
 */
interface WorkerRepository {
    /**
     * this function will get all worker of order subset .
     * @param stateOfView
     * the state of view is to Determine who called this method.
     * @return a flow of  state of Success operation  wrapped with DataState and list of workers.
     */
    fun getWorkerOfProducts(
        stateOfView: StateOfView
    ): Flow<DataState<WorkerViewState>>

    /**
     * this function will upload image of worker.
     * first we get image file of worker and upload on storage server and then we get success
     * response, we call another api for update worker image url
     * @param workerDto
     * @param file
     * @param stateOfView
     * the state of view is to Determine who called this method.
     * the workerDto contain workerId to find user
     * the file is image file worker
     * @return a flow of  state of Success operation  wrapped with DataState.
     */
    fun uploadWorkerAvatar(
        workerDto: WorkerDto,
        file: File,
        stateOfView: StateOfView
    ): Flow<DataState<WorkerViewState>>


}
