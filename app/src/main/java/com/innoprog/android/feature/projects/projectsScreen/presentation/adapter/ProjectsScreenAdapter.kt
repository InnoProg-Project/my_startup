package com.innoprog.android.feature.projects.projectsScreen.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.innoprog.android.databinding.ItemProjectBinding
import com.innoprog.android.feature.projects.domain.models.Project

class ProjectsScreenAdapter(
    private val onItemClickListener: (id: String) -> Unit
) : Adapter<ProjectsScreenViewHolder>() {

    private var listItem = listOf<Project>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsScreenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProjectsScreenViewHolder(ItemProjectBinding.inflate(layoutInflater, parent, false)).apply {
            itemView.setOnClickListener {
                onItemClickListener(listItem[bindingAdapterPosition].id.toString())
            }
        }
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ProjectsScreenViewHolder, position: Int) {
        holder.bind(listItem[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(listItem[position].id.toString())
        }
    }

    fun setListItems(items: List<Project>) {
        listItem = items
        notifyDataSetChanged()
    }
}

