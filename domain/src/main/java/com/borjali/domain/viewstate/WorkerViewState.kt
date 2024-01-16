package com.borjali.domain.viewstate

import com.borjali.domain.model.worker.WorkerDto


data class WorkerViewState(
    val message: String? = null,
    val workers: List<WorkerDto> = arrayListOf(),
)
