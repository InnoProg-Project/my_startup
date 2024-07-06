package com.innoprog.android.feature.training.courseInformation.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.innoprog.android.databinding.ItemTrainingVideoBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel

class VideoAdapter(
    private val onVideoClickListener: (String) -> Unit
) : Adapter<CourseVideoViewHolder>() {
    private val items = mutableListOf<CourseInformationVideoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseVideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CourseVideoViewHolder(
            ItemTrainingVideoBinding.inflate(layoutInflater, parent, false)
        ).apply {
            setOnVideoClickListener {
                onVideoClickListener(items[bindingAdapterPosition].videoURL)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CourseVideoViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    fun setVideoList(videoList: List<CourseInformationVideoModel>) {
        items.clear()
        items.addAll(videoList)
        notifyDataSetChanged()
    }
}

