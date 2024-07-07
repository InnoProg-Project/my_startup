package com.innoprog.android.feature.training.courseInformation.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.innoprog.android.databinding.ItemTrainingDocumentBinding

class DocumentRecyclerViewAdapter(
    private val onDocumentClickListener: (url: String) -> Unit
) : Adapter<DocumentViewHolder>() {
    private val items = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(
            ItemTrainingDocumentBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                onDocumentClickListener(items[bindingAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun setDocumentList(documentList: List<String>) {
        items.clear()
        items.addAll(documentList)
        notifyDataSetChanged()
    }
}
