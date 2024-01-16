package com.borjali.presentation.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.Gravity.CENTER
import android.view.Gravity.LEFT
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.borjali.domain.utils.MessageType
import com.borjali.presentation.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SnackBarMessage {
    @SuppressLint("RtlHardcoded", "WrongConstant")
    fun show(
        context: Context?,
        message: String?,
        messageType: MessageType,
        root: View
    ) {

        val snackbar = Snackbar.make(root, message.toString(), 4000)
        val views = snackbar.view
        views.background = ContextCompat.getDrawable(context!!, R.drawable.snack_bar_background)
        when (messageType) {
            is MessageType.SUCCESS -> {
                DrawableCompat.setTint(
                    views.background,
                    ContextCompat.getColor(context, R.color.success_4)
                )
            }

            is MessageType.ERROR -> {
                DrawableCompat.setTint(
                    views.background,
                    ContextCompat.getColor(context, R.color.warning_4)
                )
            }

            else -> {
                DrawableCompat.setTint(
                    views.background,
                    ContextCompat.getColor(context, R.color.primary_3)
                )
            }
        }
        val txtMessage =
            views.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)

        txtMessage.textSize = 18f
        (LEFT or CENTER).also {
            txtMessage.gravity = it
        }
        txtMessage.layoutParams.apply {
            height = this.width
        }

        val params = views.layoutParams
        if (params is CoordinatorLayout.LayoutParams) {
            params.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            params.width = CoordinatorLayout.LayoutParams.MATCH_PARENT
            snackbar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
            views.layoutParams = params
        }

        snackbar.show()
    }
}
