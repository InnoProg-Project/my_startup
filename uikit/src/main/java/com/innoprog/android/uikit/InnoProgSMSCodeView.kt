package com.innoprog.android.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputConnectionWrapper
import android.widget.LinearLayout
import android.widget.Space
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import androidx.core.view.postDelayed
import com.innoprog.android.uikit.ext.hideKeyboard
import com.innoprog.android.uikit.ext.showKeyboard

@SuppressLint("ResourceType")
class InnoProgSMSCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var codeLength: Int
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
                codeLength = getInt(R.styleable.SMSCodeView_maxLine, DEFAULT_LENGTH)
                orientation = HORIZONTAL
                isFocusable = true
                isFocusableInTouchMode = true
                setOnClickListener {
                    if (requestFocus()) {
                        showKeyboard()
                        setState(InnoProgInputViewState.FOCUSED)
                    }
                }
            } finally {
                recycle()
            }
        }
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
        val isDisable = inputState == InnoProgInputViewState.DISABLED
        if (isDisable) {
            setOnClickListener(null)
        }
        removeAllViews()
        for (i in 0 until codeLength) {
            val symbolView = SmsCodeSymbolView(context)
            symbolView.updateStyle(setStyle(inputState))
            symbolView.state =
                SmsCodeSymbolView.State(isActive = (i == enteredCode.length && !isDisable))
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
        this.enteredCode = enteredCode.substring(0, enteredCode.length - 1)
    }

    override fun onCheckIsTextEditor(): Boolean = true

    private fun KeyEvent.isDigitKey(): Boolean {
        return keyCode in KeyEvent.KEYCODE_0..KeyEvent.KEYCODE_9
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        with(outAttrs) {
            inputType = InputType.TYPE_CLASS_NUMBER
            imeOptions = EditorInfo.IME_ACTION_DONE
        }
        return object : InputConnectionWrapper(BaseInputConnection(this, false), true) {

            override fun deleteSurroundingText(beforeLength: Int, afterLength: Int): Boolean {
                return if (beforeLength == 1 && afterLength == 0) {
                    sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) &&
                    sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL))
                } else super.deleteSurroundingText(beforeLength, afterLength)
            }
        }
    }

    private fun setStyle(state: InnoProgInputViewState): SymbolStyle {
        return when (state) {
            InnoProgInputViewState.INACTIVE -> {
                SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    Color.TRANSPARENT,
                    context.getColor(R.color.text_tertiary)
                )
            }

            InnoProgInputViewState.DISABLED -> {
                SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    Color.TRANSPARENT,
                    context.getColor(R.color.text_tertiary)
                )
            }

            InnoProgInputViewState.ERROR -> {
                SymbolStyle(
                    false,
                    context.getColor(R.color.text_field_fill),
                    context.getColor(R.color.dark),
                    context.getColor(R.color.text_primary)
                )
            }

            InnoProgInputViewState.FOCUSED -> {
                SymbolStyle(
                    true,
                    context.getColor(R.color.text_field_fill),
                    context.getColor(R.color.accent_default),
                    context.getColor(R.color.text_primary)
                )
            }
        }
    }

    fun setState(state: InnoProgInputViewState) {
        inputState = state
        setupSymbolSubviews()
        if (state == InnoProgInputViewState.FOCUSED) {
            setOnKeyListener { _, keyCode, event -> handleKeyEvent(keyCode, event) }
            postDelayed(KEYBOARD_AUTO_SHOW_DELAY) {
                requestFocus()
                showKeyboard()
            }
        } else enteredCode = "2222"
    }
    companion object {
        private const val KEYBOARD_AUTO_SHOW_DELAY = 500L
        private const val DEFAULT_LENGTH = 4
    }
}
