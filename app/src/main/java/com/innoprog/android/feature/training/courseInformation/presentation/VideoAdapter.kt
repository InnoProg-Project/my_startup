package com.innoprog.android.feature.training.courseInformation.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.innoprog.android.databinding.ItemTrainingVideoBinding
import com.innoprog.android.feature.training.courseInformation.domain.model.CourseInformationVideoModel

class VideoAdapter(
    private val context: Context,
    private val onVideoClickListener: (String) -> Unit
) : Adapter<VideoViewHolder>() {

    var items = mutableListOf<CourseInformationVideoModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideoViewHolder(
            ItemTrainingVideoBinding.inflate(layoutInflater, parent, false),
            context,
            onVideoClickListener
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setVideoList(videoList: List<CourseInformationVideoModel>) {
        items.clear()
        items.addAll(videoList)
        Log.e("setVideoList", "$items")
        notifyDataSetChanged()
    }
}

