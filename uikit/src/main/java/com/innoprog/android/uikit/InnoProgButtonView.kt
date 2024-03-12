package com.innoprog.android.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.innoprog.android.uikit.InnoProgButtonView.ButtonSize.LARGE

class InnoProgButtonView(
    context: Context,
    attributeSet: AttributeSet,
) : androidx.appcompat.widget.AppCompatButton(context, attributeSet) {

    init {
        setBackgroundResource(R.drawable.button_shape)
        isAllCaps = false
        setButtonSizeLarge()
        setButtonPrimary()
    }

    fun setButtonType(type: ButtonType) {
        when (type) {
            ButtonType.PRIMARY -> setButtonPrimary()
            ButtonType.DEFAULT -> setButtonDefault()
            ButtonType.FLAT -> setButtonFlat()
        }
    }

    fun setButtonSize(size: ButtonSize) {
        when (size) {
            LARGE -> setButtonSizeLarge()
            ButtonSize.MEDIUM -> setButtonSizeMedium()
            ButtonSize.SMALL -> setButtonSizeSmall()
        }
    }

    private fun setButtonPrimary() {
        if (isActivated) {
            background.setTint(context.getColor(R.color.accent_default))
            setTextColor(context.getColor(R.color.text_primary))


        } else {
            background.setTint(context.getColor(R.color.background_secondary))
            setTextColor(context.getColor(R.color.text_tertiary))
        }
    }

    private fun setButtonDefault() {
        background.setTint(context.getColor(R.color.text_field_fill))
        setTextColor(context.getColor(R.color.accent_secondary))
        if (isActivated) {
            alpha = 1F

        } else {
            alpha = 0.5F
        }
    }

    private fun setButtonFlat() {
        setBackgroundColor(Color.TRANSPARENT)
        setTextColor(context.getColor(R.color.accent_secondary))
        if (isActivated) {
            alpha = 1F
        } else {
            alpha = 0.5F
        }
    }

    private fun setButtonSizeLarge() {
        setTextAppearance(R.style.TextButtonLarge)
        maxHeight = context.resources.getDimension(R.dimen.button_height_48).toInt()
    }

    private fun setButtonSizeMedium() {
        setTextAppearance(R.style.TextButtonMedium)
        maxHeight = context.resources.getDimension(R.dimen.button_height_40).toInt()
    }

    private fun setButtonSizeSmall() {
        setTextAppearance(R.style.TextButtonSmall)
        maxHeight = context.resources.getDimension(R.dimen.button_height_32).toInt()
    }

    enum class ButtonType {
        PRIMARY, DEFAULT, FLAT
    }

    enum class ButtonSize {
        LARGE, MEDIUM, SMALL
    }
}