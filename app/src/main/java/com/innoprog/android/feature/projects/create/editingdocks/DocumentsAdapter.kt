package com.innoprog.android.feature.projects.create.editingdocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemDocksBinding
import com.innoprog.android.feature.projects.domain.models.DocumentsModel

class DocumentsAdapter(private val onDocumentClickListener: (url: String) -> Unit) :
    Adapter<DocumentViewHolder>() {

    var items = listOf<DocumentsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(ItemDocksBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onDocumentClickListener(items[position].documentURL)
        }
    }
}

class DocumentViewHolder(private val binding: ItemDocksBinding) : ViewHolder(binding.root) {

    fun bind(item: DocumentsModel) {
        binding.documentName.text = item.documentTitle
    }
}
