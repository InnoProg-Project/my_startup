package com.innoprog.android.feature.training.trainingList.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemTrainingBinding
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.feature.training.trainingList.presentation.model.CoursesItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TrainingRecyclerViewAdapter(
    private val onItemClickListener: (courseId: String) -> Unit = {}
) : RecyclerView.Adapter<TrainingListViewHolder>() {

    var items = listOf<CoursesItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainingListViewHolder(ItemTrainingBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrainingListViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener(items[holder.adapterPosition].id)
        }
        holder.bind(items[position])
    }
}

class TrainingListViewHolder(private val binding: ItemTrainingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CoursesItem) {
        binding.trainingDirection.isVisible = item.direction.isNotEmpty()
        binding.trainingDirection.text = item.direction
        binding.trainingTitle.text = item.title
        binding.trainingDescription.text = item.description
        binding.trainingAuthorName.text = if (item.authorName.isNotEmpty()) "Anonymous" else item.authorName
        binding.trainingDate.text = item.createdDate
        val initials = item.authorName
            .split(' ')
            .map { it.first().uppercaseChar() }
            .joinToString(separator = "", limit = 2)
        binding.trainingAuthorAvatar.text = initials.ifBlank { "?" }
    }
}
