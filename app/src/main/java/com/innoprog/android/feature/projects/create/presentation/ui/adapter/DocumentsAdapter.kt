package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemDocksBinding
import com.innoprog.android.feature.projects.domain.models.DocumentsModel

class DocumentViewHolder(private val binding: ItemDocksBinding) : ViewHolder(binding.root) {

    fun bind(documentURL: String, onDocumentClickListener: (url: String) -> Unit) {
        binding.documentName.text = documentURL.substringAfterLast(".")
        binding.documentName.setOnClickListener {
            onDocumentClickListener(documentURL)
        }
    }
}
