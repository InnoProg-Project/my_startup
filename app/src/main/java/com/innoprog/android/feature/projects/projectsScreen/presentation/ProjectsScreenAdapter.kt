package com.innoprog.android.feature.projects.projectsScreen.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innoprog.android.databinding.ItemProjectBinding
import com.innoprog.android.feature.projects.projectsScreen.domain.model.ProjectScreenModel

class ProjectsScreenAdapter(
    val context: Context,
    val onItemClickListener: (id: String) -> Unit
) : Adapter<ProjectsScreenViewHolder>() {

    var items = listOf<ProjectScreenModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsScreenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProjectsScreenViewHolder(ItemProjectBinding.inflate(layoutInflater, parent, false), context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProjectsScreenViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(items[position].id)
        }
    }
}

class ProjectsScreenViewHolder(
    private val binding: ItemProjectBinding,
    private val context: Context,
) : ViewHolder(binding.root) {

    fun bind(item: ProjectScreenModel) {
        Glide.with(context)
            .load(item.logoFilePath)
            .into(binding.projectLogo)
        binding.projectName.text = item.name
        binding.projectShortDescription.text = item.shortDescription
        binding.projectDescription.text = item.description
    }
}
