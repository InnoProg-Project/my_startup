package com.innoprog.android.feature.imagegalleryadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R

class ImageGalleryAdapter (private val images: List<Int>) : RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_for_gallery, parent, false)
        return ImageGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageGalleryViewHolder, position: Int) {
        val imageResId = images[position]
        holder.bind(imageResId)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivPublicationCover)

        fun bind(imageResId: Int) {
            imageView.setImageResource(imageResId)
        }
    }
}