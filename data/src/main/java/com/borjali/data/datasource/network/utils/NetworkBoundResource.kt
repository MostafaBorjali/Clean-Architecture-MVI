package com.borjali.data.datasource.network.utils

import com.borjali.domain.Constants.Companion.ENGLISH
import com.borjali.domain.Constants.Companion.MESSAGE
import com.borjali.domain.Constants.Companion.NETWORK_ERROR
import com.borjali.domain.Constants.Companion.NETWORK_ERROR_DE
import com.borjali.domain.Constants.Companion.NETWORK_ERROR_TIMEOUT
import com.borjali.domain.Constants.Companion.NETWORK_ERROR_TIMEOUT_DE
import com.borjali.domain.Constants.Companion.UNKNOWN_ERROR
import com.borjali.domain.Constants.Companion.UNKNOWN_ERROR_DE
import com.borjali.domain.model.base.CurrentLanguage
import com.borjali.domain.model.base.ServerResponse
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.MessageType
import com.borjali.domain.utils.StateMessage
import com.borjali.domain.utils.UIComponentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkBoundResource<NetworkObj, CacheObj, ViewState>(
    private val apiCall: (suspend () -> ServerResponse<NetworkObj?>)? = null,

    ) {

    val result: Flow<DataState<ViewState>> = flow {
        emit(DataState.LOADING(isLoading = true))
        apiCall?.let {
            emitAll(safeAPICall())
        }
        emit(DataState.LOADING(isLoading = false))
    }

    private suspend fun safeAPICall() = flow {
        try {

            val result = apiCall?.invoke()
            if (result == null) {
                emit(buildSnackBarError(
                    if (CurrentLanguage.language == ENGLISH)
                        UNKNOWN_ERROR
                    else
                        UNKNOWN_ERROR_DE
                ))
            } else {
                result.data?.let {
                    updateCache(it)
                }
                handleNetworkSuccess(result)?.let {
                    emit(it)
                }
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    emit(
                        buildSnackBarError(
                            if (CurrentLanguage.language == ENGLISH)
                                NETWORK_ERROR_TIMEOUT
                            else
                                NETWORK_ERROR_TIMEOUT_DE
                        )
                    )

                }

                is IOException -> {
                    emit(
                        buildSnackBarError(
                            if (CurrentLanguage.language == ENGLISH)
                                NETWORK_ERROR
                            else
                                NETWORK_ERROR_DE

                        )

                    )
                }

                is HttpException -> {
                    emit(buildSnackBarError(convertErrorBody(throwable)))
                }

                else -> {
                    emit(
                        buildSnackBarError(
                            if (CurrentLanguage.language == ENGLISH)
                                UNKNOWN_ERROR
                            else
                                UNKNOWN_ERROR_DE
                        )
                    )
                }
            }
        }
    }.flowOn(Dispatchers.IO)


    private fun buildSnackBarError(
        message: String?
    ): DataState<ViewState> {
        return DataState.ERROR(
            stateMessage = StateMessage(
                message = message,
                uiComponentType = UIComponentType.SNACKBAR,
                messageType = MessageType.ERROR
            )
        )
    }

    private fun convertErrorBody(throwable: HttpException): String {
        return try {
            JSONObject(throwable.response()?.errorBody()?.charStream()?.readText().toString())
                .getString(MESSAGE)
                ?: if (CurrentLanguage.language == ENGLISH)
                    UNKNOWN_ERROR
                else
                    UNKNOWN_ERROR_DE
        } catch (exception: Exception) {
            if (CurrentLanguage.language == ENGLISH)
                UNKNOWN_ERROR
            else
                UNKNOWN_ERROR_DE
        }
    }

    open suspend fun handleNetworkSuccess(response: ServerResponse<NetworkObj?>?):
            DataState<ViewState>? {
        return null
    }


    open suspend fun updateCache(networkObject: NetworkObj) {}
}
