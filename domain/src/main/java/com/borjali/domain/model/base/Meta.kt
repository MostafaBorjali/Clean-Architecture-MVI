package com.borjali.domain.model.base

data class Meta(
    val limit: Int,
    val offset: Int,
    val total: Int,
    val sortBy: String,
    val sort: String,
)
