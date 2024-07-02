package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innoprog.android.databinding.ItemProjectDirectionBinding
import com.innoprog.android.feature.projects.create.domain.model.ProjectDirectionModel

class ChooseProjectDirectionAdapter : Adapter<ChooseProjectDirectionViewHolder>() {

    var items = mutableListOf<ProjectDirectionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChooseProjectDirectionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChooseProjectDirectionViewHolder(ItemProjectDirectionBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChooseProjectDirectionViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            items.forEach {
                if (it.isSelected) {
                    it.isSelected = false
                }
            }
            items[position] = items[position].copy(isSelected = true)
            notifyDataSetChanged()
        }
    }
}

class ChooseProjectDirectionViewHolder(private val binding: ItemProjectDirectionBinding) : ViewHolder(binding.root) {

    fun bind(item: ProjectDirectionModel) {
        binding.courseDirectionTV.text = item.title
        if (item.isSelected)
            binding.iconSelected.visibility = View.VISIBLE
        else
            binding.iconSelected.visibility = View.GONE
    }
}
