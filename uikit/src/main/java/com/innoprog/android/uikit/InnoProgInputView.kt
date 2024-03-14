package com.innoprog.android.uikit

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.innoprog.android.uikit.ext.applyStyleable


class InnoProgInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var state: InnoProgInputViewState = InnoProgInputViewState.INACTIVE

    private val editTextView by lazy { findViewById<EditText>(R.id.edit_text) }
    private val captionTextView by lazy { findViewById<TextView>(R.id.caption) }
    private val emptyHintTextView by lazy { findViewById<TextView>(R.id.hint_empty) }
    private val leftIcon by lazy { findViewById<ImageView>(R.id.left_icon) }
    private val rightIcon by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val backgroundEditTextView by lazy { findViewById<ConstraintLayout>(R.id.background_edit_text_view) }

    private lateinit var layerDrawable: LayerDrawable

    private val constraintSet by lazy { ConstraintSet().apply { clone(backgroundEditTextView) } }
    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun afterTextChanged(s: Editable?) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    emptyHintTextView.textSize = SP_16

                    constraintSet.connect(
                        R.id.hint_empty,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM,
                        resources.getDimensionPixelSize(R.dimen.margin_16)
                    )

                    constraintSet.setMargin(
                        R.id.hint_empty,
                        ConstraintSet.TOP,
                        resources.getDimensionPixelSize(R.dimen.margin_16)
                    )

                } else {
                    emptyHintTextView.textSize = SP_12

                    constraintSet.connect(
                        R.id.hint_empty,
                        ConstraintSet.BOTTOM,
                        R.id.edit_text,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.setMargin(
                        R.id.hint_empty, ConstraintSet.TOP,
                        resources.getDimensionPixelSize(R.dimen.margin_8)
                    )
                }

                TransitionManager.beginDelayedTransition(backgroundEditTextView)
                constraintSet.applyTo(backgroundEditTextView)
            }
        }
    }

    private val focusChangeListener by lazy {
        OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && state != InnoProgInputViewState.DISABLED) {
                renderState(InnoProgInputViewState.FOCUSED)
                editTextView.requestFocus()
                showKeyboard()
            } else {
                renderState(state)
            }
        }
    }

    init {
        inflate(context, R.layout.inno_prog_input_view, this)
        attrs?.applyStyleable(context, R.styleable.InnoProgInputView) {

            layerDrawable = AppCompatResources
                .getDrawable(context, R.drawable.inno_prog_input_view_background) as LayerDrawable
            layerDrawable.findDrawableByLayerId(R.id.rectangle_background)
                .setTint(context.getColor(R.color.text_field_fill))
            backgroundEditTextView.background = layerDrawable

            when (getInt(
                R.styleable.InnoProgInputView_state,
                InnoProgInputViewState.INACTIVE.number
            )) {
                InnoProgInputViewState.DISABLED.number -> { state = InnoProgInputViewState.DISABLED }
                InnoProgInputViewState.ERROR.number -> { state = InnoProgInputViewState.ERROR }
                InnoProgInputViewState.FOCUSED.number -> { state = InnoProgInputViewState.FOCUSED }
                else -> { state = InnoProgInputViewState.INACTIVE }
            }
            renderState(state)

            captionTextView.text = getString(R.styleable.InnoProgInputView_caption)
            leftIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_left_icon))
            rightIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_right_icon))

            editTextView.setText(getString(R.styleable.InnoProgInputView_text))
            emptyHintTextView.text = getString(R.styleable.InnoProgInputView_label)
            if (editTextView.text.isNotBlank()) {
                emptyHintTextView.textSize = SP_12
                constraintSet.connect(
                    R.id.hint_empty,
                    ConstraintSet.BOTTOM,
                    R.id.edit_text,
                    ConstraintSet.TOP,
                    0
                )
                constraintSet.setMargin(
                    R.id.hint_empty, ConstraintSet.TOP,
                    resources.getDimensionPixelSize(R.dimen.margin_8)
                )
                constraintSet.applyTo(backgroundEditTextView)
            }
        }

        isFocusable = true
        setFocusableInTouchMode(true)
        isClickable = true
        this@InnoProgInputView.onFocusChangeListener = focusChangeListener

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
                layerDrawable.findDrawableByLayerId(R.id.rectangle_stroke)
                    .setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = true
                this.alpha = FULL_VISIBLE
            }

            InnoProgInputViewState.DISABLED -> {
                layerDrawable.findDrawableByLayerId(R.id.rectangle_stroke)
                    .setTint(Color.TRANSPARENT)
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = false
                this.alpha = VISIBILITY_40_PERCENT
            }

            InnoProgInputViewState.ERROR -> {
                layerDrawable.findDrawableByLayerId(R.id.rectangle_stroke)
                    .setTint(context.getColor(R.color.dark))
                captionTextView.setTextColor(context.getColor(R.color.dark))
                editTextView.isEnabled = true
                this.alpha = FULL_VISIBLE
            }

            InnoProgInputViewState.FOCUSED -> {
                layerDrawable.findDrawableByLayerId(R.id.rectangle_stroke)
                    .setTint(context.getColor(R.color.stroke))
                captionTextView.setTextColor(context.getColor(R.color.text_primary))
                editTextView.isEnabled = true
                this.alpha = FULL_VISIBLE
            }
        }
    }

    private fun showKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editTextView, 0)
    }

    fun setLeftIconClickListener(onClickListener: OnClickListener) {
        leftIcon.setOnClickListener(onClickListener)
    }

    fun setRightIconClickListener(onClickListener: OnClickListener) {
        rightIcon.setOnClickListener(onClickListener)
    }

    companion object {
        const val FULL_VISIBLE = 1f
        const val VISIBILITY_40_PERCENT = 0.4f

        const val SP_12 = 12f
        const val SP_16 = 16f
    }
}
