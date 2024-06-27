package com.innoprog.android.feature.imagegalleryadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R

class ImageGalleryAdapter : RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>() {

    private val imageList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageGalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_for_gallery, parent, false)
        return ImageGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageGalleryViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.bind(imageResId)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivPublicationCover)

        fun bind(imageResId: Int) {
            imageView.setImageResource(imageResId)
        }
    }

    fun setImageList(images: List<Int>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }
}
