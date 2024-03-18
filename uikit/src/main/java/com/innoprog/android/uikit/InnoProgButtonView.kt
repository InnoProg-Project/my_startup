package com.innoprog.android.uikit


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class InnoProgButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.InnoProgButtonView)
    private val size =
        ButtonSize.entries[typedArray.getInt(R.styleable.InnoProgButtonView_buttonSize, 0)]
    private val type =
        ButtonType.entries[typedArray.getInt(R.styleable.InnoProgButtonView_buttonType, 0)]
    private val state =
        ButtonState.entries[typedArray.getInt(R.styleable.InnoProgButtonView_buttonState, 0)]

    private var text = typedArray.getText(R.styleable.InnoProgButtonView_text)

    private var rightIconDrawable: Drawable? =
        typedArray.getDrawable(R.styleable.InnoProgButtonView_rightIcon)
    private var leftIconDrawable: Drawable? =
        typedArray.getDrawable(R.styleable.InnoProgButtonView_leftIcon)

    private val textTV by lazy { findViewById<TextView>(R.id.text_button) }
    private val rightIconIV by lazy { findViewById<ImageView>(R.id.right_icon) }
    private val leftIconIV by lazy { findViewById<ImageView>(R.id.left_icon) }

    enum class ButtonType {
        PRIMARY, DEFAULT, FLAT
    }

    enum class ButtonSize {
        LARGE, MEDIUM, SMALL
    }

    enum class ButtonState {
        ENABLED, DISABLED
    }

    init {

        inflate(context, R.layout.inno_prog_button_view, this)
        setBackgroundResource(R.drawable.button_shape)
        setType()
        setSize()
        setIcons()
        textTV.text = text
        typedArray.recycle()

    }

    private fun setType() {
        when (type) {
            ButtonType.PRIMARY -> setButtonPrimary()
            ButtonType.DEFAULT -> setButtonDefault()
            ButtonType.FLAT -> setButtonFlat()
        }
    }

    private fun setSize() {
        when (size) {
            ButtonSize.LARGE -> setButtonSizeLarge()
            ButtonSize.MEDIUM -> setButtonSizeMedium()
            ButtonSize.SMALL -> setButtonSizeSmall()
        }
    }

    private fun setButtonPrimary() {
        if (state == ButtonState.ENABLED) {
            background.setTint(context.getColor(R.color.accent_default))
            textTV.setTextColor(context.getColor(R.color.text_primary))
        } else {
            background.setTint(context.getColor(R.color.background_secondary))
            textTV.setTextColor(context.getColor(R.color.text_tertiary))
        }
    }

    private fun setButtonDefault() {
        background.setTint(context.getColor(R.color.text_field_fill))
        textTV.setTextColor(context.getColor(R.color.accent_secondary))
        alpha = if (state == ButtonState.ENABLED) 1F else 0.5F
    }

    private fun setButtonFlat() {
        setBackgroundColor(Color.TRANSPARENT)
        textTV.setTextColor(context.getColor(R.color.accent_secondary))
        alpha = if (state == ButtonState.ENABLED) 1F else 0.5F
    }

    private fun setButtonSizeLarge() {
        textTV.setTextAppearance(R.style.TextButtonLarge)
        layoutParams.apply {
            setPadding(
                resources.getDimensionPixelSize(R.dimen.button_padding_20),
                resources.getDimensionPixelSize(R.dimen.button_padding_12),
                resources.getDimensionPixelSize(R.dimen.button_padding_20),
                resources.getDimensionPixelSize(R.dimen.button_padding_12),
            )
        }
        setIconLayoutParam()
    }

    private fun setButtonSizeMedium() {
        textTV.setTextAppearance(R.style.TextButtonMedium)
        layoutParams.apply {
            setPadding(
                resources.getDimensionPixelSize(R.dimen.button_padding_18),
                resources.getDimensionPixelSize(R.dimen.button_padding_10),
                resources.getDimensionPixelSize(R.dimen.button_padding_18),
                resources.getDimensionPixelSize(R.dimen.button_padding_10),
            )
        }
        setIconLayoutParam()
    }

    private fun setButtonSizeSmall() {
        textTV.setTextAppearance(R.style.TextButtonSmall)
        setPadding(
            resources.getDimensionPixelSize(R.dimen.button_padding_12),
            resources.getDimensionPixelSize(R.dimen.button_padding_6),
            resources.getDimensionPixelSize(R.dimen.button_padding_12),
            resources.getDimensionPixelSize(R.dimen.button_padding_6),
        )
        setIconLayoutParam()
    }

    private fun setIcons() {
        leftIconDrawable?.let {
            it.setTint(textTV.currentTextColor)
            leftIconIV.setImageDrawable(it)
        }
        rightIconDrawable?.let {
            it.setTint(textTV.currentTextColor)
            rightIconIV.setImageDrawable(it)
        }
    }

    private fun selectIconMargin(): Int {
        return when (size) {
            ButtonSize.LARGE -> resources.getDimensionPixelSize(R.dimen.button_padding_14)
            ButtonSize.MEDIUM -> resources.getDimensionPixelSize(R.dimen.button_padding_14)
            ButtonSize.SMALL -> resources.getDimensionPixelSize(R.dimen.button_padding_10)
        }
    }

    private fun selectIconSize(): Int {
        return if (size == ButtonSize.SMALL) resources.getDimensionPixelSize(R.dimen.button_icon_size_16)
        else resources.getDimensionPixelSize(R.dimen.button_icon_size_20)
    }

    private fun setIconLayoutParam() {
        leftIconDrawable.let {
            val marginParam = textTV.layoutParams as MarginLayoutParams
            marginParam.marginStart = selectIconMargin()
            textTV.layoutParams = marginParam
            leftIconIV.layoutParams.apply {
                height = selectIconSize()
                width = selectIconSize()
            }
        }
        rightIconDrawable.let {
            val marginParam = textTV.layoutParams as MarginLayoutParams
            marginParam.marginEnd = selectIconMargin()
            textTV.layoutParams = marginParam
            rightIconIV.layoutParams.apply {
                height = selectIconSize()
                width = selectIconSize()
            }
        }
    }
}