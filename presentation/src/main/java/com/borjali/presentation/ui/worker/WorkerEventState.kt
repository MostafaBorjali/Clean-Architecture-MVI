package com.borjali.presentation.ui.worker

import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.viewstate.StateOfView
import java.io.File
/**
 * set Event for All worker action
 */
sealed class WorkerEventState {
    /**
     * set Event for get worker list
     */
    data class GetWorkers(val stateOfView: StateOfView) : WorkerEventState()

    /**
     * set Event for upload user avatar
     */
    data class UploadWorkerAvatar(
        val stateOfView: StateOfView,
        val workerDto: WorkerDto,
        val file: File
    ) : WorkerEventState()


}
