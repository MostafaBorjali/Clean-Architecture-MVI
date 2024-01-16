package com.borjali.presentation.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borjali.domain.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

abstract class BaseViewModel<EventState, ViewState> : ViewModel() {
    private val dataSharedFlow: MutableSharedFlow<DataState<ViewState>> = MutableSharedFlow()

    // keep this protected so that only the ViewModel can modify the state
    protected val _dataState: MutableLiveData<DataState<ViewState>> = MutableLiveData()

    // Create a publicly accessible LiveData object that can be observed
    val dataState: LiveData<DataState<ViewState>> = _dataState

    init {
        dataSharedFlow
            .asSharedFlow()
            .onEach { dataState ->
                _dataState.value = dataState.also {
                    Timber.d("data state value is ${it.data}")
                }
            }
            .launchIn(viewModelScope)
    }

    fun setEventState(eventState: EventState) {
        dataSharedFlow.let { channel ->
            handleEventState(eventState).onEach { data ->
                channel.emit(data)
            }
        }.launchIn(viewModelScope)
    }

    abstract fun handleEventState(eventState: EventState): Flow<DataState<ViewState>>
}
