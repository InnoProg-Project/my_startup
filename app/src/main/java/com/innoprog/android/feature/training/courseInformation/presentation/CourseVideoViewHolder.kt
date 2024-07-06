package com.innoprog.android.feature.training.courseInformation.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemTrainingVideoBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel

class CourseVideoViewHolder(
    private val binding: ItemTrainingVideoBinding
) : ViewHolder(binding.root) {

    fun bind(item: CourseInformationVideoModel, position: Int) {
        binding.videoDescriptionTV.text = binding.root.context.getString(
            R.string.course_details_video_description, position + 1
        )

        Glide.with(binding.root.context)
            .load(item.videoURL)
            .fitCenter()
            .placeholder(R.drawable.news_sample)
            .into(binding.videoPlaceholderIV)
    }

    fun setOnVideoClickListener(clickListener: View.OnClickListener) {
        binding.videoPlaceholderIV.setOnClickListener(clickListener)
    }
}