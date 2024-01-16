package com.borjali.presentation.ui.base

import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.borjali.domain.model.base.CurrentLanguage
import com.borjali.domain.preference.CleanAppPreferences
import com.borjali.domain.utils.DataState
import com.borjali.domain.utils.StateMessage
import com.borjali.domain.utils.UIComponentType
import com.borjali.presentation.extensions.displaySnackBar
import com.borjali.presentation.extensions.displayToast
import com.borjali.presentation.extensions.setAppLocale
import javax.inject.Inject

abstract class BaseActivity :
    AppCompatActivity(),
    DataStateChangeListener {
    @Inject
    lateinit var preferences: CleanAppPreferences

    override fun onDataStateChangeListener(dataState: DataState<*>?) {
        dataState?.let {
            it.stateMessage?.let { stateMessage ->
                handleResponseState(stateMessage)
            }
        }
    }
    /**
     *  perform show message in all fragment
     */
    private fun handleResponseState(stateMessage: StateMessage?) {
        stateMessage?.message?.let { message ->
            when (stateMessage.uiComponentType) {
                is UIComponentType.TOAST -> {
                    displayToast(message)
                }
                is UIComponentType.SNACKBAR -> {
                    displaySnackBar(message, stateMessage.messageType)
                }
            }
        }
    }

    /**
     *  perform hide keyboard after clicking outside EditText
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
    /**
     *  perform AppLocale
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.setAppLocale(CurrentLanguage.language!!))
    }

}
