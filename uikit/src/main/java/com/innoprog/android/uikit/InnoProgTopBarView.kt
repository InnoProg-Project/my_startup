package com.innoprog.android.uikit

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class InnoProgTopBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InnoProgTopBarView)

    private var textTitle = typedArray.getText(R.styleable.InnoProgTopBarView_topBar_title)

    private var rightIconDrawable: Drawable? =
        typedArray.getDrawable(R.styleable.InnoProgTopBarView_topBar_right_icon)
    private var leftIconDrawable: Drawable? =
        typedArray.getDrawable(R.styleable.InnoProgTopBarView_topBar_left_icon)

    private val titleTV by lazy { findViewById<TextView>(R.id.title_tv) }
    private val rightIconIV by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val leftIconIV by lazy { findViewById<ImageView>(R.id.left_icon) }

    init {
        inflate(context, R.layout.inno_prog_topbar_view, this)
        setTitleText(textTitle)
        rightIconDrawable?.let { setRightIcon(it) }
        leftIconDrawable?.let { setLeftIcon(it) }
    }

    fun setTitleText(text: CharSequence) {
        titleTV.text = text
    }

    fun setLeftIcon(drawable: Drawable) {
        leftIconIV.setImageDrawable(drawable)
    }

    fun setRightIcon(drawable: Drawable) {
        rightIconIV.setImageDrawable(drawable)
    }

    fun setLeftIconClickListener(click: () -> Unit) {
        leftIconIV.setOnClickListener { click() }
    }

    fun setRightIconClickListener(click: () -> Unit) {
        rightIconIV.setOnClickListener { click() }
    }
}
