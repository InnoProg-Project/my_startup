package com.innoprog.android.feature.projects.create.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding
import com.innoprog.android.databinding.ItemImageAndVideoBinding

class UploadMediaFilesViewHolder(val binding: ItemDownloadMediaButtomBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: (url: String) -> Unit) {
        binding.uploadButton.setOnClickListener {
            // TODO
        }
    }
}

class MediaFilesViewHolder(val binding: ItemImageAndVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String, clickListener: (String) -> Unit) {
        binding.deleteButton.setOnClickListener {
            // TODO
        }
    }
}

