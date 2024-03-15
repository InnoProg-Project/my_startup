package com.innoprog.android.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import java.lang.StrictMath.min

@SuppressLint("ResourceType")
class InnoProgSMSCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var inputCode = arrayListOf<String>("0", "0", "0", "0")

    private val boxBackground: Int
    private val maxLineInput: Int
    private val itemWidth = 48
    private val itemHeight = itemWidth + 20
    private var centerY: Float = itemHeight.toFloat() / 2

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SMSCodeView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                boxBackground = getColor(
                    R.styleable.SMSCodeView_boxBackgroundColor,
                    ContextCompat.getColor(context, R.drawable.sms_code_backgraund)
                )
                maxLineInput = getInt(R.styleable.SMSCodeView_maxLine, 4)
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = itemWidth * maxLineInput + 24 * (maxLineInput - 1)
        val desiredHeight = itemHeight
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            min(
                desiredWidth,
                widthSize
            ), min(
                desiredHeight,
                heightSize
            )
        )
    }

    private val inputPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.input_text_size)
        textAlign = Paint.Align.CENTER
    }

    private val codeBoxPaint = Paint().apply {
        color = resources.getColor(R.color.background_secondary)
    }

    private fun boxX(number: Int): Float {
        val size = itemWidth + 24
        return size * number + itemWidth.toFloat() / 2
    }


    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawCodeBoxPaint()
        drawInputText()
    }

    private fun Canvas.drawCodeBoxPaint() {
        for (i in 0..maxLineInput) {
            val centerX = boxX(i)
            drawRect(
                RectF(
                    centerX - itemWidth / 2F,
                    centerY - itemHeight,
                    centerX + itemWidth / 2F,
                    centerY + itemHeight / 2F
                ), codeBoxPaint
            )
        }
    }

    private fun Canvas.drawInputText() {
        val y = centerY + height.toFloat() / 2 - inputPaint.descent()
        for (index in inputCode.indices) {
            val x = boxX(index)
            drawText(
                inputCode[index],
                x,
                y,
                inputPaint
            )
        }
    }
}