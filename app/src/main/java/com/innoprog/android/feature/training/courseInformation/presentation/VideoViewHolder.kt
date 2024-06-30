package com.innoprog.android.feature.training.courseInformation.presentation

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemTrainingVideoBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel

class VideoViewHolder(
    private val binding: ItemTrainingVideoBinding,
    private val context: Context,
    private val onVideoClickListener: (String) -> Unit
) : ViewHolder(binding.root) {

    fun bind(item: CourseInformationVideoModel) {
        binding.videoPlaceholderIV.setOnClickListener {
            onVideoClickListener.invoke(item.videoURL)
        }
        binding.videoDescriptionTV.text = item.videoId

        Log.e("video2", "${item.videoURL}")
        Log.e("video3", "${item.videoId}")
        Glide.with(context)
            .load(item.videoURL)
            .fitCenter()
            .placeholder(R.drawable.news_sample)
            .into(binding.videoPlaceholderIV)
    }
}