@file:Suppress("unused")

package com.borjali.presentation.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import com.borjali.domain.utils.MessageType

import com.borjali.presentation.R
import com.borjali.presentation.ui.component.SnackBarMessage

fun Activity.displayToast(msg: String?) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.displaySnackBar(message: String?, messageType: MessageType) {
    SnackBarMessage().show(
        context = this,
        message = message,
        messageType = messageType,
        root = requireViewById(R.id.mainLayout)
    )
}

fun Activity.displayToast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
