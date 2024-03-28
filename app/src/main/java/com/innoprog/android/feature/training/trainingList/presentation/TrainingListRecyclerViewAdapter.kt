package com.innoprog.android.feature.training.trainingList.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemTrainingBinding
import com.innoprog.android.feature.training.trainingList.domain.model.TrainingListModel
import com.innoprog.android.uikit.ImageLoadingType

class TrainingRecyclerViewAdapter(
    private val onItemClickListener: (courseId: Int) -> Unit = {}
) : RecyclerView.Adapter<TrainingListViewHolder>() {

    var items = listOf<TrainingListModel>()

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
            onItemClickListener(items[holder.adapterPosition].trainingId)
        }
    }
}

class TrainingListViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TrainingListModel) {
        binding.trainingDirection.text = item.trainingDirection
        binding.trainingTitle.text = item.trainingTitle
        binding.trainingDescription.text = item.trainingDescription
        binding.trainingAuthorName.text = item.trainingAuthorName
        binding.trainingAuthorPosition.text = item.trainingAuthorPosition
        binding.trainingDate.text = item.trainingDate

        val url = item.trainingAuthorAvatarURL
        val placeholderResId = com.innoprog.android.uikit.R.drawable.ic_person
        val imageType =
            ImageLoadingType.ImageNetwork(url, placeholderResId = placeholderResId)
        binding.trainingAuthorAvatar.loadImage(imageType)
    }
}
