package com.innoprog.android.feature.training.trainingList.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemTrainingBinding
import com.innoprog.android.feature.training.trainingList.domain.model.CourseShort
import com.innoprog.android.uikit.ImageLoadingType

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

class TrainingListViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CourseShort) {
        binding.trainingDirection.text = item.direction
        binding.trainingTitle.text = item.title
        binding.trainingDescription.text = item.description
        binding.trainingAuthorName.text = item.authorName
        binding.trainingAuthorPosition.text = item.authorPost
        binding.trainingDate.text = item.createdDate

        val url = item.avatarURL
        val placeholderResId = com.innoprog.android.uikit.R.drawable.ic_person
        val imageType =
            ImageLoadingType.ImageNetwork(url, placeholderResId = placeholderResId)
        binding.trainingAuthorAvatar.loadImage(imageType)
    }
}
