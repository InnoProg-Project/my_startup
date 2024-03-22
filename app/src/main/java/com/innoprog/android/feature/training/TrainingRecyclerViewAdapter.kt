package com.innoprog.android.feature.training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.databinding.ItemTrainingBinding
import com.innoprog.android.feature.training.presentation.model.TrainingModel
import com.innoprog.android.uikit.ImageLoadingType

class TrainingRecyclerViewAdapter : RecyclerView.Adapter<TrainingViewHolder>() {

    var items = listOf<TrainingModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainingViewHolder(ItemTrainingBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TrainingViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class TrainingViewHolder(private val binding: ItemTrainingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TrainingModel) {
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
