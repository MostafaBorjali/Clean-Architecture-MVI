package com.borjali.presentation.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.borjali.presentation.R
import com.borjali.presentation.databinding.LayoutCustomInputTextBinding


@Suppress("unused")
@SuppressLint("UseCompatLoadingForDrawables,ClickableViewAccessibility")
class CustomInputText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    lateinit var binding: LayoutCustomInputTextBinding
    var textColor = ContextCompat.getColor(context, R.color.primary_5)
    private var hintTextColor = ContextCompat.getColor(context, R.color.gray_4)
    var textSize = resources.getDimensionPixelSize(R.dimen.largeText)
    var type = 0
    private var length = 750
    private var iconDrawable: Drawable? = null
    var text = ""
    private var hint = ""
    private var requestFocus = true
    var textChanged: (String) -> Unit = {}

    init {
        getAttributes(attrs)
        if (!isInEditMode)
            setupView()
    }

    private fun setupView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.layout_custom_input_text,
            this,
            true
        )
        with(binding) {
            inputText.hint = hint
            inputTextLabel.text = hint
            arrayOf<InputFilter>(InputFilter.LengthFilter(length)).also { inputText.filters = it }
            if (iconDrawable != null) {
                inputTextIcon.visibility = VISIBLE
                inputTextIcon.setImageDrawable(iconDrawable)
            } else {
                inputTextIcon.visibility = GONE
            }

            inputText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    text = s.toString()
                    textChanged(text)
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.isNotEmpty()) {
                        inputTextClose.visibility = View.VISIBLE
                        inputTextLabel.visibility = View.VISIBLE
                        inputTextLayout.background =
                            ContextCompat.getDrawable(context, R.drawable.input_background_active)
                        statusMassage.text = ""
                        inputTextLabel.setTextColor(
                            ContextCompat.getColor(
                                context,
                                R.color.gray_4
                            )
                        )
                    } else {
                        inputTextClose.visibility = View.GONE
                        inputTextLabel.visibility = if (type == 3) {
                            View.INVISIBLE
                        } else {
                            View.GONE
                        }
                    }
                }
            })
        }
        focusHandel()
        inputType()
    }

    private fun inputType() {
        with(binding) {
            inputText.inputType = when (type) {
                0 -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                1 -> InputType.TYPE_TEXT_VARIATION_PASSWORD
                2 -> InputType.TYPE_CLASS_NUMBER
                3 -> InputType.TYPE_TEXT_FLAG_MULTI_LINE
                4 -> InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                else -> InputType.TYPE_CLASS_TEXT
            }
            inputTextClose.setImageResource(
                if (type == 1) {
                    R.drawable.ic_union
                } else {
                    R.drawable.ic_close
                }
            )
            inputTextClose.setOnClickListener {
                if (type == 1) {
                    if (inputText.transformationMethod == HideReturnsTransformationMethod.getInstance()) {
                        inputText.transformationMethod = PasswordTransformationMethod.getInstance()
                        inputTextClose.setImageResource(R.drawable.ic_union)
                    } else {
                        inputText.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        inputTextClose.setImageResource(R.drawable.ic_eye_view)
                    }
                } else {
                    inputText.text.clear()
                }
            }
            if (type == 1) {
                inputText.transformationMethod = PasswordTransformationMethod()
            }


            if (type == 4) {
                inputText.apply {
                    isSingleLine = true
                    maxLines = 1
                    gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                    inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                }
            }
        }
    }

    private fun focusHandel() {
        with(binding) {
            inputText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    statusMassage.text = ""

                    inputTextLayout.background =
                        ContextCompat.getDrawable(context, R.drawable.input_background_active)
                    inputTextLabel.setTextColor(ContextCompat.getColor(context, R.color.gray_4))
                    inputTextClose.visibility = View.VISIBLE
                    if (inputText.text.isNotEmpty()) {
                        inputTextLabel.visibility = View.VISIBLE
                        inputTextClose.visibility = View.VISIBLE
                    } else {

                        inputTextLabel.visibility = if (type == 3) {
                            View.INVISIBLE
                        } else {
                            View.GONE
                        }
                        inputTextClose.visibility = View.GONE
                    }
                } else {
                    statusMassage.text = ""
                    inputTextClose.visibility = View.GONE
                    textSize = resources.getDimensionPixelSize(R.dimen._16sdp)
                    inputTextLayout.background =
                        ContextCompat.getDrawable(context, R.drawable.input_background_deactivate)
                    inputTextLabel.setTextColor(ContextCompat.getColor(context, R.color.gray_4))
                    inputTextLabel.visibility = if (inputText.text.isNotEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    fun getInputText(): String {
        return text
    }

    fun setInputTextWithBreak(text: String) {
        binding.inputText.setText(text)
        binding.inputText.setSelection(binding.inputText.length())
        binding.inputTextClose.visibility = View.GONE
        binding.inputTextLayout.background =
            ContextCompat.getDrawable(context, R.drawable.input_background_deactivate)
    }

    fun setInputTxt(text: String) {
        binding.inputText.setText(text)
        binding.inputTextClose.visibility = View.GONE
        binding.inputTextLayout.background =
            ContextCompat.getDrawable(context, R.drawable.input_background_deactivate)
    }

    fun error(errorMessage: String) {
        binding.inputTextLayout.background =
            ContextCompat.getDrawable(context, R.drawable.input_background_error)
        binding.inputTextLabel.setTextColor(ContextCompat.getColor(context, R.color.warning_4))
        binding.statusMassage.setTextColor(ContextCompat.getColor(context, R.color.warning_4))
        binding.statusMassage.text = errorMessage
        if (requestFocus) {
            binding.inputText.requestFocus()
        }
    }

    fun success(successMessage: String) {
        binding.inputTextLayout.background =
            ContextCompat.getDrawable(context, R.drawable.input_background_success)
        binding.inputTextLabel.setTextColor(ContextCompat.getColor(context, R.color.success_4))
        binding.statusMassage.setTextColor(ContextCompat.getColor(context, R.color.success_4))
        binding.statusMassage.text = successMessage
    }

    fun clear() {
        with(binding) {
            inputText.text.clear()
            statusMassage.text = ""
            inputTextLabel.visibility = View.GONE
            inputTextClose.visibility = View.GONE
            textSize = resources.getDimensionPixelSize(R.dimen._16sdp)
            inputTextLayout.background =
                ContextCompat.getDrawable(context, R.drawable.input_background_deactivate)
            inputTextLabel.setTextColor(ContextCompat.getColor(context, R.color.gray_4))
        }
    }

    private fun getAttributes(attrs: AttributeSet?) {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInputText)
        for (i in 0 until a.indexCount) {
            when (val attr = a.getIndex(i)) {
                R.styleable.CustomInputText_iconDrawable -> iconDrawable = a.getDrawable(attr)
                R.styleable.CustomInputText_textColor -> textColor = a.getColor(attr, textColor)
                R.styleable.CustomInputText_hintTextColor ->
                    hintTextColor =
                        a.getColor(attr, hintTextColor)

                R.styleable.CustomInputText_textSize -> textSize = a.getInt(attr, textSize)
                R.styleable.CustomInputText_type -> type = a.getInt(attr, type)
                R.styleable.CustomInputText_length -> length = a.getInt(attr, length)
                R.styleable.CustomInputText_text -> text = a.getString(attr).toString()
                R.styleable.CustomInputText_hint -> hint = a.getString(attr).toString()
                R.styleable.CustomInputText_isFocusable ->
                    requestFocus =
                        a.getBoolean(attr, requestFocus)
            }
        }
        a.recycle()
    }
}

@BindingAdapter("text")
fun setText(view: CustomInputText, text: String?) {
    text?.let {
        if (view.text != text)
            view.setInputTxt(text)
    }
}

@BindingAdapter("error")
fun setError(view: CustomInputText, errorMessage: String?) {
    errorMessage?.let(view::error)
}

@BindingAdapter("errorResourceId")
fun setResourceError(view: CustomInputText, errorMessage: Int?) {
    errorMessage?.let {
        if (it != 0)
            view.error(view.context.getString(errorMessage))
    }
}

@BindingAdapter("success")
fun setSuccess(view: CustomInputText, successMessage: String?) {
    successMessage?.let {
        view.success(it)
    }
}

@BindingAdapter("textAttrChanged")
fun setInverseBindingText(view: CustomInputText, inverseBindingListener: InverseBindingListener?) {
    view.textChanged = {
        inverseBindingListener?.onChange()
    }
}

@InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
fun getSelectedValue(view: CustomInputText): String {
    return view.text
}
