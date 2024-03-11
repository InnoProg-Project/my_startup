package com.innoprog.android.uikit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText

class InnoProgInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var state: InnoProgInputViewState = InnoProgInputViewState.INACTIVE

    private val editTextView by lazy { findViewById<TextInputEditText>(R.id.text_input_edit_text) }
    private val captionTextView by lazy { findViewById<TextView>(R.id.caption) }
    private val leftIcon by lazy { findViewById<ImageView>(R.id.left_icon) }
    private val rightIcon by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val backgroundEditTextView by lazy { findViewById<ConstraintLayout>(R.id.background_edit_text_view) }
    private val strokeEditTextView by lazy { findViewById<ConstraintLayout>(R.id.stroke_edit_text_view) }


    init {
        inflate(context, R.layout.inno_prog_input_view, this)
        attrs?.applyStyleable(context, R.styleable.InnoProgInputView) {
            when (getInt(R.styleable.InnoProgInputView_state, 0)) {
                1 -> {
                    state = InnoProgInputViewState.DISABLED
                }

                2 -> {
                    state = InnoProgInputViewState.ERROR
                }

                3 -> {
                    state = InnoProgInputViewState.FOCUSED
                }

                else -> {
                    state = InnoProgInputViewState.INACTIVE
                }
            }
            renderState(state)

            backgroundEditTextView.setBackgroundResource(R.drawable.rectangle_with_corners_8dp)
            strokeEditTextView.setBackgroundResource(R.drawable.rectangle_with_stroke)

            editTextView.setText(getString(R.styleable.InnoProgInputView_text))
            editTextView.setHint(getString(R.styleable.InnoProgInputView_label))
            captionTextView.text = getString(R.styleable.InnoProgInputView_caption)
            leftIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_left_icon))
            rightIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_right_icon))
        }
    }

    fun renderState(state: InnoProgInputViewState) {
        when (state) {
            InnoProgInputViewState.INACTIVE -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
            }

            InnoProgInputViewState.DISABLED -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill_transparent))
                strokeEditTextView.background.setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
            }

            InnoProgInputViewState.ERROR -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(context.getColor(R.color.dark))
                captionTextView.setTextColor(context.getColor(R.color.dark))
            }

            InnoProgInputViewState.FOCUSED -> {
                backgroundEditTextView.background.setTint(context.getColor(R.color.text_field_fill))
                strokeEditTextView.background.setTint(context.getColor(R.color.stroke))
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
            }
        }
    }

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
