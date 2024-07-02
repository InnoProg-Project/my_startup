package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemDocksBinding
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding
import com.innoprog.android.databinding.ItemImageAndVideoBinding
import com.innoprog.android.databinding.ItemProjectDirectionBinding

class ItemProjectAdapter(
    private val step: Int,
    private val data: List<String>,
    private val clickListener: (url: String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_download_media_buttom -> {
                UploadMediaFilesViewHolder(
                    ItemDownloadMediaButtomBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            R.layout.item_image_and_video -> {
                MediaFilesViewHolder(
                    ItemImageAndVideoBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            R.layout.item_project_direction -> {
                ChooseProjectDirectionViewHolder(
                    ItemProjectDirectionBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                )
            }

            R.layout.item_project_document -> {
                DocumentViewHolder(
                    ItemDocksBinding.inflate(
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
        when (getItemViewType(position)) {
            R.layout.item_download_media_buttom -> (holder as UploadMediaFilesViewHolder).bind(clickListener)
            R.layout.item_image_and_video -> (holder as MediaFilesViewHolder).bind(items[position],clickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (step) {
            STEP_ONE -> {
                R.layout.fragment_project_details
            }

            STEP_TWO -> {
                if (position == data.size) R.layout.item_download_media_buttom else R.layout.item_image_and_video
            }

            STEP_THREE -> {
                R.layout.item_project_direction
            }

            STEP_FOUR -> {
                if (position == data.size) R.layout.item_download_media_buttom else R.layout.item_project_document
            }

            else -> {
                R.layout.item_download_media_buttom
            }
        }
    }

    companion object {
        const val STEP_ONE = 1
        const val STEP_TWO = 2
        const val STEP_THREE = 3
        const val STEP_FOUR = 4
    }
}
