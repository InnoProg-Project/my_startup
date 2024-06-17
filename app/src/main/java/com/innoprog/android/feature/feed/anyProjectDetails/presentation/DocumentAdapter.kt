package com.innoprog.android.feature.feed.anyProjectDetails.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemProjectDocumentBinding
import com.innoprog.android.feature.feed.anyProjectDetails.domain.models.DocumentModel

class DocumentAdapter(
    private val documentsList: List<DocumentModel>,
    private val onDocumentClickListener: (url: String) -> Unit
) : Adapter<DocumentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(ItemProjectDocumentBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount() = documentsList.size

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(documentsList[position])
        holder.itemView.setOnClickListener {
            onDocumentClickListener(documentsList[position].documentURL)
        }
    }
}

class DocumentViewHolder(private val binding: ItemProjectDocumentBinding) :
    ViewHolder(binding.root) {

    fun bind(item: DocumentModel) {
        binding.tvDocumentTitle.text = item.documentTitle
    }
}
