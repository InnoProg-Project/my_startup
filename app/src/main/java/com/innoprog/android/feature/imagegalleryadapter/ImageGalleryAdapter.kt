package com.innoprog.android.feature.imagegalleryadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.innoprog.android.R

class ImageGalleryAdapter : RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>() {

    private val imageList = mutableListOf<Any>()

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

        /**
         * Временно указан тип Any, чтобы избежать ошибок на разных фрагментах,
         * но лучше нам конечно не использовать ресурсы, а сразу использовать формат, который
         * будет приходить с бэкэнда.
         */
        fun bind(image: Any) {
            val requestManager = Glide.with(imageView.context)
            val requestBuilder = when (image) {
                is Int -> requestManager.load(image)
                is String -> requestManager.load(image)
                else -> throw IllegalArgumentException("Unsupported image type")
            }
            requestBuilder.into(imageView)
        }
    }

    /**
     * Временно указан тип Any
     */
    fun setImageList(images: List<Any>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }
}
