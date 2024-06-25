package com.innoprog.android.feature.projects.projectsScreen.presentation.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innoprog.android.databinding.ItemProjectBinding
import com.innoprog.android.feature.projects.domain.models.Project

class ProjectsScreenViewHolder(
    private val binding: ItemProjectBinding
) : ViewHolder(binding.root) {

    fun bind(item: Project) = with(binding) {
        Glide.with(root.context)
            .load(item.logoFilePath)
            .into(binding.projectLogo)
        projectName.text = item.name
        projectShortDescription.text = item.shortDescription
        projectDescription.text = item.description
    }
}