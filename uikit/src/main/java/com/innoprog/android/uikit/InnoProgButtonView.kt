package com.innoprog.android.uikit


import android.content.Context
import android.graphics.Color
import android.util.AttributeSet


class InnoProgButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {

    private val typedArray = context.obtainStyledAttributes(attrs,R.styleable.ButtonCustomView)
    private val size = ButtonSize.entries[typedArray.getInt(R.styleable.ButtonCustomView_buttonSize, 0)]
    private val type = ButtonType.entries[typedArray.getInt(R.styleable.ButtonCustomView_buttonType, 0)]

    init {

        setBackgroundResource(R.drawable.button_shape)
        isAllCaps = false

        when (size) {
            ButtonSize.LARGE -> setButtonSizeLarge()
            ButtonSize.MEDIUM -> setButtonSizeMedium()
            ButtonSize.SMALL -> setButtonSizeSmall()
        }

        setType()


        typedArray.recycle()
    }


    private fun setType (){
        when (type) {
            ButtonType.PRIMARY -> setButtonPrimary()
            ButtonType.DEFAULT -> setButtonDefault()
            ButtonType.FLAT -> setButtonFlat()
        }
    }

    private fun setButtonPrimary() {
        if (isEnabled) {
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
        alpha = if (isEnabled) {
            1F
        } else {
            0.5F
        }
    }

    private fun setButtonFlat() {
        setBackgroundColor(Color.TRANSPARENT)
        setTextColor(context.getColor(R.color.accent_secondary))
        alpha = if (isEnabled) {
            1F
        } else {
            0.5F
        }
    }

    private fun setButtonSizeLarge() {
        setTextAppearance(R.style.TextButtonLarge)
        height = context.resources.getDimension(R.dimen.button_height_48).toInt()
    }

    private fun setButtonSizeMedium() {
        setTextAppearance(R.style.TextButtonMedium)
        height = context.resources.getDimension(R.dimen.button_height_40).toInt()
    }

    private fun setButtonSizeSmall() {
        setTextAppearance(R.style.TextButtonSmall)
        height = context.resources.getDimension(R.dimen.button_height_32).toInt()
    }

    enum class ButtonType {
        PRIMARY, DEFAULT, FLAT
    }

    enum class ButtonSize {
        LARGE, MEDIUM, SMALL
    }
}