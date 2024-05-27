package com.innoprog.android.feature.edit.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.innoprog.android.databinding.ItemImageAndVideoBinding
import java.io.File

class MediaAttachRecyclerAdapter(
    private val onPlayClickListener: (mediaPath: String) -> Unit = {},
    private val onDeleteClickListener: (mediaPath: String) -> Unit = {}
) : RecyclerView.Adapter<MediaAttachRecyclerAdapter.MediaAttachViewHolder>() {

    var mediaList = listOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaAttachViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MediaAttachViewHolder(
            ItemImageAndVideoBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediaAttachViewHolder, position: Int) {
        holder.bind(mediaList[position])
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    inner class MediaAttachViewHolder(private val binding: ItemImageAndVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaPath: String) {

            if (isItVideo(mediaPath)) {
                binding.playButton.setOnClickListener { onPlayClickListener(mediaPath) }
                binding.playButton.isVisible = true
            } else {
                binding.playButton.isVisible = false
            }
            binding.deleteButton.setOnClickListener { onDeleteClickListener(mediaPath) }

            Glide.with(binding.uploadedPhotoAndVideoIV)
                .load(File(mediaPath))
                .into(binding.uploadedPhotoAndVideoIV)

        }

        private fun isItVideo(mediaPath: String): Boolean {
            val extension = mediaPath.substringAfterLast('.', "")
            return when (extension.lowercase()) {
                "mov", "mp4" -> true
                else -> false
            }

        }
    }
}




