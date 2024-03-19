package com.innoprog.android.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import androidx.core.view.postDelayed

@SuppressLint("ResourceType")
class InnoProgSMSCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var codeLength: Int = 4
    private var inputState: InnoProgInputViewState = InnoProgInputViewState.INACTIVE

    private var enteredCode: String = ""
        set(value) {
            val digits = value.filter { char -> char.isDigit() }
            require(digits.length <= codeLength) { "enteredCode=$digits is longer than $codeLength" }
            field = digits
            updateState()
        }
    private val symbolSubviews: Sequence<SmsCodeSymbolView>
        get() = children.filterIsInstance<SmsCodeSymbolView>()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SMSCodeView,
            defStyleAttr,
            defStyleRes
        ).apply {
            try {
                codeLength = getInt(R.styleable.SMSCodeView_maxLine, 4)
                orientation = HORIZONTAL
                isFocusable = true
                isFocusableInTouchMode = true
                setOnClickListener {
                    if (requestFocus()) {
                        showKeyboard()
                    }
                }
            } finally {
                recycle()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    private fun updateState() {
        val codeLengthChanged = codeLength != symbolSubviews.count()
        if (codeLengthChanged) {
            setupSymbolSubviews()
        }

        val viewCode = symbolSubviews.map { it.state.symbol }
            .filterNotNull()
            .joinToString(separator = "")
        val isViewCodeOutdated = enteredCode != viewCode
        if (isViewCodeOutdated) {
            symbolSubviews.forEachIndexed { index, view ->
                view.state = SmsCodeSymbolView.State(
                    symbol = enteredCode.getOrNull(index),
                    isActive = (enteredCode.length == index)
                )
            }
        }
    }

    private fun setupSymbolSubviews() {
        removeAllViews()
        val symbolStyle = getStyle(inputState)
        for (i in 0 until codeLength) {
            val symbolView = SmsCodeSymbolView(context, symbolStyle)
            symbolView.state = SmsCodeSymbolView.State(isActive = (i == enteredCode.length))
            addView(symbolView)

            if (i < codeLength.dec()) {
                val space = Space(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        resources.getDimensionPixelSize(R.dimen.sms_item_indent),
                        0
                    )
                }
                addView(space)
            }
        }
    }

    private fun getStyle(state: InnoProgInputViewState): SymbolStyle {
        when (state) {
            InnoProgInputViewState.INACTIVE -> {
                return SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    Color.TRANSPARENT,
                    context.getColor(R.color.text_tertiary)
                )
            }

            InnoProgInputViewState.DISABLED -> {
                return SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    Color.TRANSPARENT,
                    context.getColor(R.color.text_tertiary)
                )
            }

            InnoProgInputViewState.ERROR -> {
                return SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    context.getColor(R.color.dark),
                    context.getColor(R.color.text_primary)
                )
            }

            InnoProgInputViewState.FOCUSED -> {
                return SymbolStyle(
                    true,
                    context.getColor(R.color.text_field_fill),
                    context.getColor(R.color.accent_default),
                    context.getColor(R.color.text_primary)
                )
            }
        }
    }

    private fun handleKeyEvent(keyCode: Int, event: KeyEvent): Boolean = when {
        event.action != KeyEvent.ACTION_DOWN -> false
        event.isDigitKey() -> {
            val enteredSymbol = event.keyCharacterMap.getNumber(keyCode)
            appendSymbol(enteredSymbol)
            true
        }

        event.keyCode == KeyEvent.KEYCODE_DEL -> {
            removeLastSymbol()
            true
        }

        event.keyCode == KeyEvent.KEYCODE_ENTER -> {
            hideKeyboard()
            true
        }

        else -> false
    }

    private fun appendSymbol(symbol: Char) {
        if (enteredCode.length == codeLength) {
            return
        }
        this.enteredCode = enteredCode + symbol
    }
    private fun removeLastSymbol() {
        if (enteredCode.isEmpty()) {
            return
        }
    }
    private fun KeyEvent.isDigitKey(): Boolean {
        return keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9
    }
    fun setState(state: InnoProgInputViewState) {
        inputState = state
        setupSymbolSubviews()
        if (state == InnoProgInputViewState.FOCUSED){
            setOnKeyListener { _, keyCode, event -> handleKeyEvent(keyCode, event) }
            postDelayed(KEYBOARD_AUTO_SHOW_DELAY) {
                requestFocus()
                showKeyboard()
            }
        } else enteredCode = "2222"
    }
    companion object {
        private const val KEYBOARD_AUTO_SHOW_DELAY = 500L
    }
}
