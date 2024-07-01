package com.innoprog.android.uikit

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.TextWatcher
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getDrawable
import androidx.transition.TransitionManager
import com.innoprog.android.uikit.ext.applyStyleable

class InnoProgInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(
    context,
    attrs,
    defStyleAttr
) {
    private var state: InnoProgInputViewState = InnoProgInputViewState.INACTIVE

    private val editTextView by lazy { findViewById<EditText>(R.id.edit_text) }
    private val captionTextView by lazy { findViewById<TextView>(R.id.caption) }
    private val emptyHintTextView by lazy { findViewById<TextView>(R.id.hint_empty) }
    private val leftIcon by lazy { findViewById<ImageView>(R.id.left_icon) }
    private val rightIcon by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val backgroundEditTextView by lazy { findViewById<ConstraintLayout>(R.id.background_edit_text_view) }

    private val layerDrawable: LayerDrawable by lazy {
        AppCompatResources.getDrawable(
            context,
            R.drawable.inno_prog_input_view_background
        ) as LayerDrawable
    }

    private val constraintSet by lazy {
        ConstraintSet().apply {
            clone(backgroundEditTextView)
        }
    }

    private val focusChangeListener by lazy {
        OnFocusChangeListener { _, hasFocus ->
            if (hasFocus && state != InnoProgInputViewState.DISABLED) {
                editTextView.requestFocus()
                showKeyboard()
            }
        }
    }

    init {
        inflate(
            context,
            R.layout.inno_prog_input_view,
            this
        )

        editTextView.id = View.generateViewId()

        attrs?.applyStyleable(context, R.styleable.InnoProgInputView) {

            layerDrawable.findDrawableByLayerId(R.id.rectangle_background)
                .setTint(context.getColor(R.color.text_field_fill))
            backgroundEditTextView.background = layerDrawable

            when (
                getInt(
                    R.styleable.InnoProgInputView_state,
                    InnoProgInputViewState.INACTIVE.number
                )
            ) {
                InnoProgInputViewState.DISABLED.number -> {
                    state = InnoProgInputViewState.DISABLED
                }

                InnoProgInputViewState.ERROR.number -> {
                    state = InnoProgInputViewState.ERROR
                }

                InnoProgInputViewState.FOCUSED.number -> {
                    state = InnoProgInputViewState.FOCUSED
                }

                else -> {
                    state = InnoProgInputViewState.INACTIVE
                }
            }
            renderState(state)

            captionTextView.text = getString(R.styleable.InnoProgInputView_caption)
            leftIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_left_icon))
            rightIcon.setImageDrawable(getDrawable(R.styleable.InnoProgInputView_right_icon))
            emptyHintTextView.text = getString(R.styleable.InnoProgInputView_label)
            getString(R.styleable.InnoProgInputView_text)?.let { setText(it) }
        }

        isFocusable = true
        setFocusableInTouchMode(true)
        isClickable = true
        this@InnoProgInputView.onFocusChangeListener = focusChangeListener

        editTextView.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showHintFilled()
                renderState(InnoProgInputViewState.FOCUSED)
            } else {
                if (editTextView.text.isNullOrEmpty()) {
                    showHintEmpty()
                }
                renderState(state)
            }
        }
    }

    fun setInputType(type: Int) {
        editTextView.inputType = type
    }

    fun setCaptionText(text: String) {
        captionTextView.text = text
    }

    fun setTransformationMethod(method: TransformationMethod) {
        editTextView.transformationMethod = method
    }

    fun setRightIcon(src: Int) {
        rightIcon.setImageDrawable(getDrawable(context, src))
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState() ?: Bundle()
        return SavedState(superState).apply {
            text = editTextView.text.toString()
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        (state as? SavedState)?.let {
            editTextView.setText(it.text)
            super.onRestoreInstanceState(it.superState)
        } ?: super.onRestoreInstanceState(state)
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
                    .setTint(context.getColor(R.color.accent_secondary))
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

    private fun showHintEmpty() {
        emptyHintTextView.textSize = SP_16

        constraintSet.connect(
            R.id.hint_empty,
            ConstraintSet.TOP,
            R.id.left_icon,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            R.id.hint_empty,
            ConstraintSet.BOTTOM,
            R.id.left_icon,
            ConstraintSet.BOTTOM
        )

        TransitionManager.beginDelayedTransition(backgroundEditTextView)
        constraintSet.applyTo(backgroundEditTextView)
    }

    private fun showHintFilled() {
        emptyHintTextView.textSize = SP_12

        constraintSet.connect(
            R.id.hint_empty,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.clear(R.id.hint_empty, ConstraintSet.BOTTOM)

        constraintSet.setMargin(
            R.id.hint_empty,
            ConstraintSet.TOP,
            resources.getDimensionPixelSize(R.dimen.margin_5)
        )

        TransitionManager.beginDelayedTransition(backgroundEditTextView)
        constraintSet.applyTo(backgroundEditTextView)
    }

    fun setLeftIconClickListener(onClickListener: OnClickListener) {
        leftIcon.setOnClickListener(onClickListener)
    }

    fun setRightIconClickListener(onClickListener: OnClickListener) {
        rightIcon.setOnClickListener(onClickListener)
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editTextView.addTextChangedListener(textWatcher)
    }

    fun getText(): String {
        return editTextView.text.toString()
    }

    fun setHintText(text: String) {
        emptyHintTextView.text = text
    }

    fun setText(text: String) {
        if (text.isNotBlank()) {
            showHintFilled()
            editTextView.setText(text)
        }
    }

    fun setCaption(text: String) {
        captionTextView.text = text
    }

    fun setSingleLine(singleLine: Boolean) {
        editTextView.isSingleLine = singleLine
    }

    private companion object {
        const val FULL_VISIBLE = 1f
        const val VISIBILITY_40_PERCENT = 0.4f

        const val SP_12 = 12f
        const val SP_16 = 16f

        const val KEY_SUPER_STATE = "superState"
    }
}

private class SavedState : View.BaseSavedState {
    var text: String? = null

    constructor(superState: Parcelable?) : super(superState)

    private constructor(inParcel: Parcel) : super(inParcel) {
        text = inParcel.readString()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeString(text)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
            override fun createFromParcel(`in`: Parcel): SavedState {
                return SavedState(`in`)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}