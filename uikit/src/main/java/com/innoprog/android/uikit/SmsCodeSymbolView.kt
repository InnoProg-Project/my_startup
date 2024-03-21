package com.innoprog.android.uikit

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import androidx.annotation.ColorInt

@SuppressLint("ViewConstructor")
internal class SmsCodeSymbolView(context: Context, private val symbolStyle: SymbolStyle) :
    View(context) {
    data class State(
        val symbol: Char? = null,
        val isActive: Boolean = false
    )

    var state: State = State()
        set(value) {
            if (field == value) return
            field = value
            updateState(state)
        }
    private val itemWidth: Int = resources.getDimensionPixelSize(R.dimen.sms_item_size)
    private val itemHeight: Int = resources.getDimensionPixelSize(R.dimen.sms_item_size)
    private val cornerRadius: Float =
        resources.getDimensionPixelSize(R.dimen.corner_radius_sms_cod_box).toFloat()

    private val backgroundPaint: Paint = Paint().apply {
        color = symbolStyle.backgroundColor
        style = Paint.Style.FILL
    }
    private val borderPaint: Paint = Paint().apply {
        isAntiAlias = true
        color = symbolStyle.borderColor
        style = Paint.Style.STROKE
        strokeWidth = resources.getDimensionPixelSize(R.dimen.sms_item_border_width).toFloat()
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = resources.getDimension(R.dimen.text_size_16)
        color = symbolStyle.textColor
        textAlign = Paint.Align.CENTER
    }

    private val backgroundRect = RectF()
    private val borderRect = RectF()
    private var textAnimator: Animator? = null

    private fun updateState(state: State) = with(state) {
        textAnimator?.cancel()
        if (symbol == null && isActive && symbolStyle.showCursor) {
            textAnimator = ObjectAnimator.ofInt(textPaint, "alpha", FULL, FULL, ZERO, ZERO)
                .apply {
                    duration = cursorAlphaAnimDuration
                    startDelay = cursorAlphaAnimStartDelay
                    repeatCount = ObjectAnimator.INFINITE
                    repeatMode = ObjectAnimator.REVERSE
                    addUpdateListener { invalidate() }
                }
        } else {
            val startAlpha = if (symbol == null) FULL else ZERO
            val endAlpha = if (symbol == null) ZERO else FULL
            textAnimator = ObjectAnimator.ofInt(textPaint, "alpha", startAlpha, endAlpha)
                .apply {
                    duration = textPaintAlphaAnimDuration
                    addUpdateListener { invalidate() }
                }
        }

        textAnimator?.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(itemWidth, itemHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        backgroundRect.left = zeroPoint
        backgroundRect.top = zeroPoint
        backgroundRect.right = measuredWidth.toFloat() + borderPaint.strokeWidth / 2
        backgroundRect.bottom = measuredHeight.toFloat() + borderPaint.strokeWidth / 2
        borderRect.left = zeroPoint
        borderRect.top = zeroPoint
        borderRect.right = measuredWidth.toFloat()
        borderRect.bottom = measuredHeight.toFloat()
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        drawCodeBoxPaint()
        drawCodeBoxBorderPaint()
        drawInputText()
    }

    private fun Canvas.drawCodeBoxBorderPaint() {
        drawRoundRect(
            borderRect,
            cornerRadius,
            cornerRadius,
            borderPaint
        )
    }

    private fun Canvas.drawCodeBoxPaint() {
        drawRoundRect(
            backgroundRect,
            cornerRadius,
            cornerRadius,
            backgroundPaint
        )
    }

    private val y = itemWidth / 2 + textPaint.textSize / 2 - textPaint.descent()
    private fun Canvas.drawInputText() {
        drawText(
            if (state.isActive && symbolStyle.showCursor) cursorSymbol else state.symbol?.toString()
                ?: "",
            backgroundRect.width() / 2 + borderPaint.strokeWidth / 2,
            y,
            textPaint
        )
    }

    companion object {
        const val textPaintAlphaAnimDuration = 25L
        const val cursorAlphaAnimDuration = 500L
        const val cursorAlphaAnimStartDelay = 200L
        const val cursorSymbol = "|"
        const val zeroPoint = 0f
        const val ZERO = 0
        const val FULL = 255
    }
}

data class SymbolStyle(
    val showCursor: Boolean,
    @ColorInt val backgroundColor: Int,
    @ColorInt val borderColor: Int,
    @ColorInt val textColor: Int,
)
