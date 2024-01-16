package com.borjali.domain.model.worker

import java.io.Serializable

data class WorkerDto(
    val fullName: String,
    val id: String,
    val role: String,
    val personnelCode: Long,
    var activeWorkLog: Boolean = false,
    var activeRestTime: Boolean = false,
    var attachment: AttachmentDto?,
):Serializable