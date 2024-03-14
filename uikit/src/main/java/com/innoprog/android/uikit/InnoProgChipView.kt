package com.innoprog.android.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.innoprog.android.uikit.ext.applyStyleable

class InnoProgChipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val chip by lazy { findViewById<Chip>(R.id.chip_view) }
    private var check: Boolean = false

    init {
        inflate(context, R.layout.inno_prog_chip_view, this)
        attrs?.applyStyleable(context, R.styleable.ChipCustomView) {

            chip.text = getText(R.styleable.ChipCustomView_chipText)

            if (getBoolean(R.styleable.ChipCustomView_chipChecked, false)) {
                chip.setTextColor(context.getColor(R.color.text_primary))
                chip.chipBackgroundColor =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context, R.color.accent_default
                        )
                    )
                check = true
            } else {
                chip.setTextColor(context.getColor(R.color.text_secondary))
                chip.chipBackgroundColor =
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            R.color.background_primary
                        )
                    )
                check = false
            }
        }
    }

    private fun checked() {
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.accent_default))
        chip.setTextColor(context.getColor(R.color.text_primary))
        check = true
    }

    private fun unChecked() {
        chip.chipBackgroundColor =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.background_primary))
        chip.setTextColor(context.getColor(R.color.text_secondary))
        check = false
    }

    fun checkedChip() {
        if (check) {
            unChecked()
        } else {
            checked()
        }
    }
}