package com.borjali.presentation.ui.worker

import dagger.hilt.android.lifecycle.HiltViewModel
import com.borjali.domain.usecase.worker.GetWorkersOfProductsUseCase
import com.borjali.domain.usecase.worker.UploadWorkerAvatarUseCase
import com.borjali.domain.viewstate.WorkerViewState
import com.borjali.presentation.ui.base.BaseViewModel
import com.borjali.presentation.ui.worker.WorkerEventState.*
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * shared view model for handle all worker action
 */
@HiltViewModel
class WorkerViewModel
@Inject
constructor(
    private val getWorkersOfProductsUseCase: GetWorkersOfProductsUseCase,
    private val uploadWorkerAvatarUseCase: UploadWorkerAvatarUseCase,
    ) :
    BaseViewModel<WorkerEventState, WorkerViewState>() {

    override fun handleEventState(eventState: WorkerEventState) =
        flow {
            when (eventState) {
                is GetWorkers -> emitAll(
                    getWorkersOfProductsUseCase.invoke(
                        eventState.stateOfView
                    )
                )



                is UploadWorkerAvatar -> emitAll(
                    uploadWorkerAvatarUseCase.invoke(
                        eventState.workerDto,
                        eventState.file,
                        eventState.stateOfView
                    )
                )




            }
        }
}
