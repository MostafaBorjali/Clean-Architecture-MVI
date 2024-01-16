package com.borjali.data.mapper

import com.borjali.data.datasource.cache.worker.AttachmentEntity
import com.borjali.data.datasource.cache.worker.WorkerEntity
import com.borjali.domain.model.worker.AttachmentDto
import com.borjali.domain.model.worker.WorkerDto

/**
 * Map from WorkerDto to WorkerEntity.
 *
 * @return mapped WorkerEntity
 */
fun WorkerDto.toWorkerEntity() = WorkerEntity(
    id = id,
    fullName = fullName,
    personnelCode = personnelCode,
    role = role,
    attachment = attachment?.toAttachmentEntity(),
)

/**
 * Map from WorkerEntity to WorkerDto.
 *
 * @return mapped WorkerDto
 */
fun WorkerEntity.toWorkerDto() = WorkerDto(
    id = id,
    fullName = fullName,
    personnelCode = personnelCode,
    role = role,
    attachment = attachment?.toAttachmentDto(),
)

/**
 * Map from AttachmentDto to AttachmentEntity.
 *
 * @return mapped AttachmentEntity
 */
fun AttachmentDto.toAttachmentEntity() = AttachmentEntity(
    url = url,
    cover = cover
)

/**
 * Map from AttachmentEntity to AttachmentDto.
 *
 * @return mapped AttachmentDto
 */
fun AttachmentEntity.toAttachmentDto() = AttachmentDto(
    url = url,
    cover = cover
)
