package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemDocksBinding
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding
import com.innoprog.android.databinding.ItemImageAndVideoBinding
import com.innoprog.android.databinding.ItemProjectDirectionBinding
import com.innoprog.android.feature.projects.create.presentation.model.CreateProjectItemType

class ItemProjectAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<CreateProjectItemType>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_download_media_buttom -> {
                UploadMediaFilesViewHolder(
                    ItemDownloadMediaButtomBinding.inflate(layoutInflater, parent, false)
                )
            }

            R.layout.item_image_and_video -> {
                MediaFilesViewHolder(
                    ItemImageAndVideoBinding.inflate(
                        layoutInflater, parent, false
                    )
                )
            }

            R.layout.item_project_direction -> {
                ChooseProjectDirectionViewHolder(
                    ItemProjectDirectionBinding.inflate(
                        layoutInflater, parent, false
                    )
                )
            }

            R.layout.item_project_document -> {
                DocumentViewHolder(ItemDocksBinding.inflate(layoutInflater, parent, false))
            }

            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (items[position]) {
            is CreateProjectItemType.AddMediaButtonItem -> (holder as UploadMediaFilesViewHolder).bind(
                (items[position] as CreateProjectItemType.DocumentItem).clickListener
            )

            is CreateProjectItemType.DocumentItem -> (holder as DocumentViewHolder).bind(
                (items[position] as CreateProjectItemType.DocumentItem).url,
                (items[position] as CreateProjectItemType.DocumentItem).clickListener
            )

            is CreateProjectItemType.InputView -> TODO()
            is CreateProjectItemType.MediaItem -> (holder as MediaFilesViewHolder).bind(
                (items[position] as CreateProjectItemType.MediaItem).url,
                (items[position] as CreateProjectItemType.MediaItem).clickListener
            )

            is CreateProjectItemType.AddLogoButtonItem -> (holder as LogoViewHolder).bind(
                (items[position] as CreateProjectItemType.AddLogoButtonItem).url,
                (items[position] as CreateProjectItemType.AddLogoButtonItem).clickListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun createFirstScreenItems(
        projectName: String? = null,
        shortDescription: String? = null,
        description: String? = null,
        logoUrl: String? = null,
        clickListener: (url: String) -> Unit
    ) {
        items.clear()
        items.add(
            CreateProjectItemType.InputView(
                R.layout.item_input_view, R.string.name_project.toString(), projectName
            )
        )
        items.add(
            CreateProjectItemType.InputView(
                R.layout.item_input_view, R.string.short_description.toString(), shortDescription
            )
        )
        items.add(
            CreateProjectItemType.InputView(
                R.layout.item_input_view, R.string.descriptions.toString(), description
            )
        )
        items.add(
            CreateProjectItemType.AddLogoButtonItem(
                R.layout.item_input_view, logoUrl, clickListener
            )
        )
        notifyDataSetChanged()
    }

    fun createSecondScreenItems(data: List<String>, clickListener: (url: String) -> Unit) {
        items.clear()
        for (direction in data) {
            items.add(
                CreateProjectItemType.DocumentItem(
                    R.layout.item_project_direction, direction, clickListener
                )
            )
        }
        items.add(CreateProjectItemType.AddMediaButtonItem(R.layout.item_download_media_buttom))
        notifyDataSetChanged()
    }

    fun createThreeScreenItems(data: List<String>, clickListener: (url: String) -> Unit) {
        items.clear()
        for (direction in data) {
            items.add(
                CreateProjectItemType.DocumentItem(
                    R.layout.item_project_direction, direction, clickListener
                )
            )
        }
        notifyDataSetChanged()
    }

    fun createFourthScreenItems(data: List<String>, clickListener: (url: String) -> Unit) {
        items.clear()
        for (direction in data) {
            items.add(
                CreateProjectItemType.DocumentItem(
                    R.layout.item_project_document, direction, clickListener
                )
            )
        }
        items.add(CreateProjectItemType.AddMediaButtonItem(R.layout.item_download_media_buttom))
        notifyDataSetChanged()
    }

    private fun createFiveScreenItems() {

    }
}
