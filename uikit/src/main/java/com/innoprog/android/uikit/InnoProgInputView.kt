package com.innoprog.android.uikit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout


class InnoProgInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var state: InnoProgInputViewState = InnoProgInputViewState.INACTIVE

    private val editTextView by lazy { findViewById<EditText>(R.id.edit_text) }
    private val captionTextView by lazy { findViewById<TextView>(R.id.caption) }
    private val emptyHintTextView by lazy { findViewById<TextView>(R.id.hint_empty) }
    private val filledHintTextView by lazy { findViewById<TextView>(R.id.hint_filled) }
    private val leftIcon by lazy { findViewById<ImageView>(R.id.left_icon) }
    private val rightIcon by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val backgroundEditTextView by lazy { findViewById<ConstraintLayout>(R.id.background_edit_text_view) }
    private val strokeEditTextView by lazy { findViewById<FrameLayout>(R.id.stroke_edit_text_view) }

    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    emptyHintTextView.visibility = VISIBLE
                    filledHintTextView.visibility = INVISIBLE
                } else {
                    emptyHintTextView.visibility = INVISIBLE
                    filledHintTextView.visibility = VISIBLE
                }
            }
        }
    }

    private val focusChangeListener by lazy {
        OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && state != InnoProgInputViewState.DISABLED) {
                renderState(InnoProgInputViewState.FOCUSED)
                editTextView.requestFocus()
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editTextView, 0)
            } else {
                renderState(state)
            }
        }
    }

    init {
        inflate(context, R.layout.inno_prog_input_view, this)
        attrs?.applyStyleable(context, R.styleable.InnoProgInputView) {
            when (getInt(
                R.styleable.InnoProgInputView_state,
                InnoProgInputViewState.INACTIVE.value
            )) {
                InnoProgInputViewState.DISABLED.value -> { state = InnoProgInputViewState.DISABLED }
                InnoProgInputViewState.ERROR.value -> { state = InnoProgInputViewState.ERROR }
                InnoProgInputViewState.FOCUSED.value -> { state = InnoProgInputViewState.FOCUSED }
                else -> { state = InnoProgInputViewState.INACTIVE }
            }
            renderState(state)

            backgroundEditTextView.setBackgroundResource(R.drawable.rectangle_with_corners_8dp)
            strokeEditTextView.setBackgroundResource(R.drawable.rectangle_with_stroke)

            editTextView.setText(getString(R.styleable.InnoProgInputView_text))
            emptyHintTextView.text = getString(R.styleable.InnoProgInputView_label)
            filledHintTextView.text = getString(R.styleable.InnoProgInputView_label)
            if (editTextView.text.isNullOrEmpty()) {
                filledHintTextView.visibility = INVISIBLE
            } else {
                emptyHintTextView.visibility = INVISIBLE
            }

            captionTextView.text = getString(R.styleable.InnoProgInputView_caption)
            leftIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_left_icon))
            rightIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_right_icon))
        }

        isFocusable = true
        setFocusableInTouchMode(true)
        isClickable = true
        onFocusChangeListener = focusChangeListener

        editTextView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                renderState(InnoProgInputViewState.FOCUSED)
            } else {
                renderState(state)
            }
        }
        editTextView.addTextChangedListener(textWatcher)
    }

    fun renderState(state: InnoProgInputViewState) {
        when (state) {
            InnoProgInputViewState.INACTIVE -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                emptyHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                filledHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                editTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = true
                this.alpha = 1f
            }

            InnoProgInputViewState.DISABLED -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                emptyHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                filledHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                editTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = false
                this.alpha = 0.4f
            }

            InnoProgInputViewState.ERROR -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(context.getColor(R.color.dark))
                captionTextView.setTextColor(context.getColor(R.color.dark))
                emptyHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                filledHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                editTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = true
                this.alpha = 1f
            }

            InnoProgInputViewState.FOCUSED -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(context.getColor(R.color.stroke))
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                emptyHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                filledHintTextView.setTextColor(context.getColor(R.color.text_secondary))
                editTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = true
                this.alpha = 1f
            }
        }
    }

    fun setLeftIconClickListener(onClickListener: OnClickListener) {
        leftIcon.setOnClickListener(onClickListener)
    }

    fun setRightIconClickListener(onClickListener: OnClickListener) {
        rightIcon.setOnClickListener(onClickListener)
    }

    fun deleteLeftIcon() { leftIcon.setImageDrawable(null) }

    fun deleteRightIcon() { rightIcon.setImageDrawable(null) }

    inline fun AttributeSet.applyStyleable(
        context: Context,
        @StyleableRes styleableResId: IntArray,
        action: TypedArray.() -> Unit
    ) {

        val typedArray = context.obtainStyledAttributes(this, styleableResId)
        typedArray.action()
        typedArray.recycle()
    }
}
