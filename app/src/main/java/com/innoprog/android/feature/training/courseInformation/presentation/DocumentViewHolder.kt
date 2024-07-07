package com.innoprog.android.feature.training.courseInformation.presentation

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemTrainingDocumentBinding

class DocumentViewHolder(private val binding: ItemTrainingDocumentBinding) :
    ViewHolder(binding.root) {
    fun bind(item: String, position: Int) {
        binding.documentTitleTV.text = binding.root.context.getString(
            R.string.course_details_document_description, position + 1)
    }
}