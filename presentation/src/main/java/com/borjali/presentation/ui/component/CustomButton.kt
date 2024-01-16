package com.borjali.presentation.ui.component

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.borjali.presentation.R
import com.borjali.presentation.databinding.LayoutCustomButtonBinding


class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private lateinit var binding: LayoutCustomButtonBinding

    private var color = ContextCompat.getColor(context, R.color.white)
    private var textSize = resources.getDimensionPixelSize(R.dimen.text_button)
    private var type = 0
    private var buttonText = ""

    init {
        getAttributes(attrs, defStyle)
        setupView()
    }

    private fun setupView() {
        binding = LayoutCustomButtonBinding.inflate(LayoutInflater.from(context), this, true)
        with(binding) {
            customButtonText.text = buttonText
            customButtonText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            //  customButtonText.setTextColor(color)
        }
    }

    fun getText(): String {
        return binding.customButtonText.text.toString()
    }

    fun setText(buttonText: String) {
        binding.customButtonText.text = buttonText
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading
        with(binding) {
            if (loading) {
                customButtonText.visibility = View.INVISIBLE
                customButtonProgress.visibility = View.VISIBLE
            } else {
                customButtonText.visibility = View.VISIBLE
                customButtonProgress.visibility = View.INVISIBLE
            }
        }
    }

    private fun getAttributes(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomButton,
            defStyle,
            0
        )

        color = a.getColor(R.styleable.CustomButton_button_text_color, color)
        textSize = a.getDimensionPixelOffset(R.styleable.CustomButton_buttonTextSize, textSize)
        type = a.getInt(R.styleable.CustomButton_buttonType, type)
        buttonText = a.getString(R.styleable.CustomButton_buttonText).toString()

        a.recycle()
    }
}
