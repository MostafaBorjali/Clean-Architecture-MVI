package com.borjali.domain.model.base

import com.google.gson.*
import java.lang.reflect.Type

abstract class BaseTypeAdapter<T>: JsonSerializer<T>, JsonDeserializer<T> {
    override fun serialize(src: T?, typeOfSrc: Type?, context: JsonSerializationContext) =
        context.serialize(src)!!

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?) =
        des(json.toString())

    abstract fun des(json: String?): T
}