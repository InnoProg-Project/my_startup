package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemProjectDirectionBinding
import com.innoprog.android.feature.projects.create.domain.model.ProjectDirectionModel

class ChooseProjectDirectionViewHolder(private val binding: ItemProjectDirectionBinding) :
    ViewHolder(binding.root) {

    fun bind(item: ProjectDirectionModel, clickListener: (direction: String) -> Unit) {
        binding.courseDirectionTV.text = item.title
        if (item.isSelected) {
            binding.iconSelected.visibility = View.VISIBLE
            clickListener.invoke(item.title)
        } else {
            binding.iconSelected.visibility = View.GONE
        }
    }
}
