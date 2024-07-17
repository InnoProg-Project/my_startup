package com.innoprog.android.feature.projects.create.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemInputViewBinding

class InputViewHolder(val binding: ItemInputViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(label: String?, hint: String, data: String?) {
        if (label.isNullOrEmpty()) {
            binding.itemTitle.text = label
        }
        binding.inputIv.setHint(hint)
        binding.inputIv.setText(data ?: "")
    }
}
