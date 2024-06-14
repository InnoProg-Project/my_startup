package com.innoprog.android.uikit

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.innoprog.android.uikit.ext.dpToPx

class InnoProgChipGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var onChipSelectListener: OnChipSelectListener? = null
    private var selectedChipIndex: Int = -1
    private val pxValue = context.dpToPx(MARGIN_START)

    init {
        orientation = HORIZONTAL
    }

    fun setChips(chips: List<String>) {
        removeAllViews()
        chips.forEachIndexed { index, chipText ->
            val chip = Chip(context)
            chip.text = chipText
            chip.setChipBackgroundColorResource(android.R.color.white)
            chip.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            chip.isCheckable = false

            val params = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            if (index > 0) {
                params.setMargins(pxValue, 0, 0, 0)
            }

            chip.layoutParams = params

            chip.setOnClickListener {
                selectChip(index)
            }
            addView(chip)
        }
    }

    fun setOnChipSelectListener(listener: OnChipSelectListener) {
        this.onChipSelectListener = listener
    }

    fun selectChip(chipIndex: Int) {
        if (chipIndex == selectedChipIndex) return

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is Chip) {
                child.setChipBackgroundColorResource(R.color.background_primary)
                child.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            }
        }

        if (chipIndex != -1) {
            val chip = getChildAt(chipIndex) as? Chip
            chip?.let {
                it.setChipBackgroundColorResource(R.color.accent_default)
                it.setTextColor(ContextCompat.getColor(context, R.color.text_primary))
            }

            selectedChipIndex = chipIndex
            onChipSelectListener?.onChipSelected(chipIndex)
        }
    }

    interface OnChipSelectListener {
        fun onChipSelected(chipIndex: Int)
    }

    companion object {
        private const val MARGIN_START = 4
    }
}
