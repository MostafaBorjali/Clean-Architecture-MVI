@file:Suppress("KDocUnresolvedReference", "UNUSED_PARAMETER")

package com.borjali.domain.utils

import com.borjali.domain.viewstate.StateOfView

/**
 * DataState class is a wrapper class to wrap th data between the app layers.
 */
sealed class DataState<T>(
    var loading: Boolean,
    var data: T? = null,
    var stateMessage: StateMessage? = null,
    var stateOfView: StateOfView? = StateOfView.None

) {

    /**
     * A generic data class to wrap the succeeded data result.
     *
     * @param data the result data
     */
    class SUCCESS<T>(
        data: T? = null,
        stateMessage: StateMessage? = null,
        stateOfView: StateOfView? = StateOfView.None
    ) : DataState<T>(
        loading = false,
        data = data,
        stateMessage = stateMessage,
        stateOfView = stateOfView
    )

    /**
     * A data class to wrap the fail exception result.
     *
     * @param exception the exception result
     */
    class ERROR<T>(
        data: T? = null,
        stateMessage: StateMessage? = null,
        stateOfView: StateOfView? = StateOfView.None
    ) : DataState<T>(
        loading = false,
        data = data,
        stateMessage = stateMessage,
        stateOfView = stateOfView
    )

    /**
     * A nothing object that emits the loading state.
     */
    class LOADING<T>(
        isLoading: Boolean,
        cachedData: T? = null,
        stateOfView: StateOfView? = StateOfView.None
    ) : DataState<T>(
        loading = isLoading,
        data = cachedData,
        stateOfView = stateOfView
    )

    /**
     * A nothing object that emits the empty result state,
     * we use this if the request or the query succeeded but with empty results.
     */
    class Empty<T>(
        isLoading: Boolean,
        cachedData: T? = null,
        stateOfView: StateOfView? = StateOfView.None
    ) : DataState<T?>(
        loading = false,
        data = null,
        stateOfView = stateOfView
    )
}
