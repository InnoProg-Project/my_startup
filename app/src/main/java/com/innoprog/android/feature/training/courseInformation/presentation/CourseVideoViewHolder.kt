package com.innoprog.android.feature.training.courseInformation.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemTrainingVideoBinding

class CourseVideoViewHolder(
    private val binding: ItemTrainingVideoBinding
) : ViewHolder(binding.root) {

    fun bind(item: String, position: Int) {
        binding.videoDescriptionTV.text = binding.root.context.getString(
            R.string.course_details_video_description, position + 1
        )

        Glide.with(binding.root.context)
            .load(item)
            .fitCenter()
            .placeholder(R.drawable.news_sample)
            .into(binding.videoPlaceholderIV)
    }

    fun setOnVideoClickListener(clickListener: View.OnClickListener) {
        binding.videoPlaceholderIV.setOnClickListener(clickListener)
    }
}