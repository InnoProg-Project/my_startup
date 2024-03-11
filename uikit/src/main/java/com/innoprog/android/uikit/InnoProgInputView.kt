package com.innoprog.android.uikit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout

class InnoProgInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.inno_prog_input_view, this)
        attrs?.applyStyleable(context, R.styleable.InnoProgInputView) {

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
