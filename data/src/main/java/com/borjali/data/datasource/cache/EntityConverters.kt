@file:Suppress("unused")

package com.borjali.data.datasource.cache

import androidx.room.TypeConverter
import com.borjali.data.datasource.cache.worker.AttachmentEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object EntityConverters {

    private lateinit var gson: Gson

    fun initialize(gson: Gson) {
        EntityConverters.gson = gson
    }

    @TypeConverter
    fun attachmentEntityToJson(attachmentEntity: AttachmentEntity): String? {
        val type = object : TypeToken<AttachmentEntity>() {}.type
        return gson.toJson(attachmentEntity, type)
    }

    @TypeConverter
    fun jsonToAttachmentEntity(attachmentEntity: String?): AttachmentEntity? {
        val type = object : TypeToken<AttachmentEntity?>() {}.type
        return gson.fromJson(attachmentEntity, type)
    }


}
