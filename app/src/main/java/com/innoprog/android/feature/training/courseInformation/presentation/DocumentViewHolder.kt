package com.innoprog.android.feature.training.courseInformation.presentation

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemTrainingDocumentBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationDocumentModel

class DocumentViewHolder(private val binding: ItemTrainingDocumentBinding) :
    ViewHolder(binding.root) {
    fun bind(item: CourseInformationDocumentModel) {
        binding.documentTitleTV.text = item.documentId
    }
}