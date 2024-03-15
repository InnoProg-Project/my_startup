package com.innoprog.android.uikit


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity


class InnoProgButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ButtonCustomView)
    private val size =
        ButtonSize.entries[typedArray.getInt(R.styleable.ButtonCustomView_buttonSize, 0)]
    private val type =
        ButtonType.entries[typedArray.getInt(R.styleable.ButtonCustomView_buttonType, 0)]

    private val rightIcon: Drawable? =
        typedArray.getDrawable(R.styleable.ButtonCustomView_rightIcon)
    private val leftIcon: Drawable? = typedArray.getDrawable(R.styleable.ButtonCustomView_leftIcon)

    init {
        setBackgroundResource(R.drawable.button_shape)
        gravity = Gravity.CENTER

        when (size) {
            ButtonSize.LARGE -> setButtonSizeLarge()
            ButtonSize.MEDIUM -> setButtonSizeMedium()
            ButtonSize.SMALL -> setButtonSizeSmall()
        }
        typedArray.recycle()
    }


    private fun setType() {
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
        layoutParams.apply {
            setPadding(
                resources.getDimensionPixelSize(R.dimen.button_padding_20),
                resources.getDimensionPixelSize(R.dimen.button_padding_12),
                resources.getDimensionPixelSize(R.dimen.button_padding_20),
                resources.getDimensionPixelSize(R.dimen.button_padding_12),
            )
        }
}

    private fun setButtonSizeMedium() {
        setTextAppearance(R.style.TextButtonMedium)
        height = context.resources.getDimension(R.dimen.button_height_40).toInt()
        layoutParams.apply {
            gravity = Gravity.CENTER
            setPadding(
                context.resources.getDimension(R.dimen.button_padding_18).toInt(),
                context.resources.getDimension(R.dimen.button_padding_10).toInt(),
                context.resources.getDimension(R.dimen.button_padding_18).toInt(),
                context.resources.getDimension(R.dimen.button_padding_10).toInt(),
            )
        }
    }

    private fun setButtonSizeSmall() {
        setTextAppearance(R.style.TextButtonSmall)
        height = context.resources.getDimension(R.dimen.button_height_32).toInt()
        layoutParams.apply {
            gravity = Gravity.CENTER
            setPadding(
                context.resources.getDimension(R.dimen.button_padding_12).toInt(),
                context.resources.getDimension(R.dimen.button_padding_6).toInt(),
                context.resources.getDimension(R.dimen.button_padding_12).toInt(),
                context.resources.getDimension(R.dimen.button_padding_6).toInt(),
            )
        }
    }

    private fun setIcons() {

        leftIcon?.let {
            it.setTint(currentHintTextColor)
            it.setBounds(
                0,
                0,
                selectIconSize(),
                selectIconSize()
            )
        }
        rightIcon?.let {
            it.setTint(currentHintTextColor)
            it.setBounds(
                0,
                0,
                selectIconSize(),
                selectIconSize()
            )
        }
        setCompoundDrawables(leftIcon, null, rightIcon, null)
        compoundDrawablePadding = selectIconPadding()

    }

    private fun selectIconPadding(): Int {
        return when (size) {
            ButtonSize.LARGE -> resources.getDimensionPixelSize(R.dimen.button_padding_14)
            ButtonSize.MEDIUM -> resources.getDimensionPixelSize(R.dimen.button_padding_14)
            ButtonSize.SMALL -> resources.getDimensionPixelSize(R.dimen.button_padding_10)
        }
    }

    private fun selectIconSize(): Int {
        return when (size) {
            ButtonSize.SMALL -> resources.getDimensionPixelSize(R.dimen.button_icon_size_16)
            else -> resources.getDimensionPixelSize(R.dimen.button_icon_size_20)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setType()
        setIcons()
    }

    enum class ButtonType {
        PRIMARY, DEFAULT, FLAT
    }

    enum class ButtonSize {
        LARGE, MEDIUM, SMALL
    }
}