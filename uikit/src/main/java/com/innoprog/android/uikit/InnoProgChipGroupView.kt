package com.innoprog.android.uikit

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip

class InnoProgChipGroupView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private var onChipSelectListener: OnChipSelectListener? = null
    private var selectedChipIndex: Int = -1

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
                params.setMargins(dpToPx(4), 0, 0, 0)
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

    private fun selectChip(chipIndex: Int) {
        if (chipIndex == selectedChipIndex) return

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is Chip) {
                child.setChipBackgroundColorResource(android.R.color.white)
                child.setTextColor(ContextCompat.getColor(context, R.color.text_secondary))
            }
        }

        if (chipIndex != -1) {
            val chip = getChildAt(chipIndex) as? Chip
            chip?.let {
                it.setChipBackgroundColorResource(R.color.accent_default)
                it.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }

            selectedChipIndex = chipIndex
            onChipSelectListener?.onChipSelected(chipIndex)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }

    interface OnChipSelectListener {
        fun onChipSelected(chipIndex: Int)
    }
}