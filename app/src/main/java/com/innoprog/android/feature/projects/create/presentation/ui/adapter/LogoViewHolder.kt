package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding

class LogoViewHolder(val binding: ItemDownloadMediaButtomBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(url: String?, clickListener: (url: String) -> Unit) {
        binding.uploadButton.setOnClickListener {
            // TODO
        }
    }
}
