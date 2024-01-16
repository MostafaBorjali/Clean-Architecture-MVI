package com.borjali.presentation.ui.base

import com.borjali.domain.utils.DataState

interface DataStateChangeListener {
    fun onDataStateChangeListener(dataState: DataState<*>?)
}
