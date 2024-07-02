package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding
import com.innoprog.android.feature.projects.domain.models.DocumentsModel

class ItemProjectAdapter(
    private val step: Int,
    private val data: List<String>,
    private val clickListener: (url: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<DocumentsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_download_media_buttom -> {
                return UploadMediaFilesViewHolder(
                    ItemDownloadMediaButtomBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            R.layout.item_image_and_video -> {
                return UploadMediaFilesViewHolder(
                    ItemDownloadMediaButtomBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.bind(items[position])
        holder.itemView.setOnClickListener {
            clickListener(items[position].documentURL)
        }
    }
}
