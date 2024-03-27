package com.innoprog.android.feature.training.course_information.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemTrainingDocumentBinding
import com.innoprog.android.feature.training.course_information.presentation.model.CourseInformationDocumentModel

class DocumentRecyclerViewAdapter : Adapter<DocumentViewHolder>() {

    var items = listOf<CourseInformationDocumentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DocumentViewHolder(ItemTrainingDocumentBinding.inflate(layoutInflater,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(items[position])
        //holder.itemView.setOnClickListener { items[position].documentURL }
    }
}

class DocumentViewHolder(private val binding: ItemTrainingDocumentBinding) : ViewHolder(binding.root) {

    fun bind(item: CourseInformationDocumentModel) {
        binding.documentTitleTV.text = item.documentTitle
    }
}
