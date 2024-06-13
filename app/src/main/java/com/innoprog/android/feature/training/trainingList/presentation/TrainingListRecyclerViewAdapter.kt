package com.innoprog.android.feature.training.trainingList.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemTrainingBinding
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TrainingRecyclerViewAdapter(
    private val onItemClickListener: (courseId: String) -> Unit = {}
) : RecyclerView.Adapter<TrainingListViewHolder>() {

    var items = listOf<CourseShort>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainingListViewHolder(ItemTrainingBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrainingListViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            // TODO("Move listener to create viewHolder")
            onItemClickListener(items[holder.adapterPosition].id)
        }
    }
}

class TrainingListViewHolder(private val binding: ItemTrainingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CourseShort) {
        binding.trainingDirection.text = item.direction
        binding.trainingTitle.text = item.title
        binding.trainingDescription.text = item.description
        binding.trainingAuthorName.text = item.authorName
        val date = LocalDate.parse(item.createdDate, DateTimeFormatter.ISO_DATE)
        binding.trainingDate.text = date.format(DateTimeFormatter.ofPattern("dd MMM"))
        val initials = item.authorName
            .split(' ')
            .map { it.first().uppercaseChar() }
            .joinToString(separator = "", limit = 2)
        binding.trainingAuthorAvatar.text = initials.ifBlank { "?" }
    }
}
