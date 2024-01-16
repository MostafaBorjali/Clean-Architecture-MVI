package com.borjali.domain.model.base

open class BaseSealedClass {
    override fun toString(): String {
        return this::class.simpleName ?: ""
    }
}
