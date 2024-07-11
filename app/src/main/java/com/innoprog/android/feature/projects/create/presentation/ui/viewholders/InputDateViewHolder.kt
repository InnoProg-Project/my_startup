package com.innoprog.android.feature.projects.create.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemInputViewBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InputDateViewHolder(val binding: ItemInputViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: LocalDate?) {
        binding.itemTitle.text =  R.string.terms.toString()
        binding.inputIv.setHint(R.string.select_date.toString())
        if (data != null) {
            binding.inputIv.setText(data.format(DateTimeFormatter.ISO_DATE))
        }
    }
}