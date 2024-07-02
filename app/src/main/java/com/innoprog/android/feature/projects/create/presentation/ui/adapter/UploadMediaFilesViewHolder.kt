package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding

class UploadMediaFilesViewHolder(val binding: ItemDownloadMediaButtomBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: () -> Unit) {
        binding.uploadButton.setOnClickListener {
            clickListener()
        }
    }
}
