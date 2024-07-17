package com.innoprog.android.feature.projects.create.presentation.ui.viewholders

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemDocksBinding

class DocumentViewHolder(private val binding: ItemDocksBinding) : ViewHolder(binding.root) {

    fun bind(documentURL: String, onDocumentClickListener: (url: String) -> Unit) {
        binding.documentName.text = documentURL.substringAfterLast(".")
        binding.documentName.setOnClickListener {
            onDocumentClickListener(documentURL)
        }
    }
}
