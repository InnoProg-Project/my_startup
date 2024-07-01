package com.innoprog.android.feature.training.courseInformation.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.innoprog.android.databinding.ItemTrainingDocumentBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationDocumentModel

class DocumentRecyclerViewAdapter(private val onDocumentClickListener: (url: String) -> Unit) :
    Adapter<DocumentViewHolder>() {

    var items = mutableListOf<CourseInformationDocumentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(
            ItemTrainingDocumentBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
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

    fun setDocumentList(documentList: List<CourseInformationDocumentModel>) {
        items.clear()
        items.addAll(documentList)
        notifyDataSetChanged()
    }
}
