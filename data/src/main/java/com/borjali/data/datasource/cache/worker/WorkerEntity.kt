package com.borjali.data.datasource.cache.worker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worker_table")
data class WorkerEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "role") val role: String,
    @ColumnInfo(name = "personnel_code") val personnelCode: Long,
    @ColumnInfo(name = "attachment") val attachment: AttachmentEntity? = null,
)
