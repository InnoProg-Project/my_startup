package com.innoprog.android.feature.projects.create.presentation.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemDocksBinding
import com.innoprog.android.databinding.ItemDownloadMediaButtomBinding
import com.innoprog.android.databinding.ItemImageAndVideoBinding
import com.innoprog.android.databinding.ItemProjectDirectionBinding
import com.innoprog.android.feature.projects.create.domain.model.ProjectDirectionModel
import com.innoprog.android.feature.projects.create.presentation.model.CreateProjectItemType
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.ChooseProjectDirectionViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.DocumentViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.InputDateViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.InputViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.LogoViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.MediaFilesViewHolder
import com.innoprog.android.feature.projects.create.presentation.ui.viewholders.UploadMediaFilesViewHolder
import java.time.LocalDate

class ItemProjectAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
                    ItemImageAndVideoBinding.inflate(layoutInflater, parent, false)
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

            is CreateProjectItemType.InputViewItem -> (holder as InputViewHolder).bind(
                (items[position] as CreateProjectItemType.InputViewItem).label,
                (items[position] as CreateProjectItemType.InputViewItem).hint,
                (items[position] as CreateProjectItemType.InputViewItem).input,
            )

            is CreateProjectItemType.MediaItem -> (holder as MediaFilesViewHolder).bind(
                (items[position] as CreateProjectItemType.MediaItem).url,
                (items[position] as CreateProjectItemType.MediaItem).clickListener
            )

            is CreateProjectItemType.AddLogoButtonItem -> (holder as LogoViewHolder).bind(
                (items[position] as CreateProjectItemType.AddLogoButtonItem).url,
                (items[position] as CreateProjectItemType.AddLogoButtonItem).clickListener
            )

            is CreateProjectItemType.InputDateView -> (holder as InputDateViewHolder).bind(
                (items[position] as CreateProjectItemType.InputDateView).deadLine,
            )

            is CreateProjectItemType.DirectionItem -> with(holder as ChooseProjectDirectionViewHolder) {
                holder.bind(
                    (items[position] as CreateProjectItemType.DirectionItem).direction,
                    (items[position] as CreateProjectItemType.DirectionItem).clickListener
                )
                holder.itemView.setOnClickListener {
                    items.forEach {
                        with(it as CreateProjectItemType.DirectionItem) {
                            if (it.direction.isSelected) {
                                it.direction.isSelected = false
                            }
                        }
                    }
                    (items[position] as CreateProjectItemType.DirectionItem).direction.isSelected =
                        true
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun createFirstScreenItems(
        projectName: String? = null,
        shortDescription: String? = null,
        description: String? = null,
        logoUrl: Uri?,
        addLogoClickListener: () -> Unit
    ) {
        items.clear()
        items.add(
            CreateProjectItemType.InputViewItem(
                R.layout.item_input_view,
                null,
                R.string.name_project.toString(),
                projectName)
        )
        items.add(
            CreateProjectItemType.InputViewItem(
                R.layout.item_input_view,
                null,
                R.string.short_description.toString(),
                shortDescription
            )
        )
        items.add(
            CreateProjectItemType.InputViewItem(R.layout.item_input_view, null, R.string.descriptions.toString(), description)
        )
        items.add(
            CreateProjectItemType.AddLogoButtonItem(R.layout.item_input_view, logoUrl, addLogoClickListener)
        )
        notifyDataSetChanged()
    }

    fun createSecondScreenItems(data: List<String>, clickListener: (url: String) -> Unit) {
        items.clear()
        for (direction in data) {
            items.add(CreateProjectItemType.DocumentItem(R.layout.item_project_direction, direction, clickListener))
        }
        items.add(CreateProjectItemType.AddMediaButtonItem(R.layout.item_download_media_buttom))
        notifyDataSetChanged()
    }

    fun createThreeScreenItems(
        data: String? = null,
        directions: List<String>,
        clickListener: (direction: String) -> Unit
    ) {
        items.clear()
        for (direction in directions) {
            items.add(
                CreateProjectItemType.DirectionItem(
                    R.layout.item_project_direction, ProjectDirectionModel(
                        direction,
                        direction == data
                    ), clickListener
                )
            )
        }
        notifyDataSetChanged()
    }

    fun createFourthScreenItems(data: List<String>, clickListener: (url: String) -> Unit) {
        items.clear()
        for (direction in data) {
            items.add(CreateProjectItemType.DocumentItem(R.layout.item_project_document, direction, clickListener))
        }
        items.add(CreateProjectItemType.AddMediaButtonItem(R.layout.item_download_media_buttom))
        notifyDataSetChanged()
    }

    fun createFiveScreenItems(
        investDescription: String? = null,
        deadLine: LocalDate? = null,
        webUrl: String? = null,
        appUrl: String? = null,
        socialNetworkUrl: String? = null
    ) {
        items.clear()
        items.add(
            CreateProjectItemType.InputViewItem(
                R.layout.item_input_view,
                R.string.financing_stage.toString(),
                R.string.investments_description.toString(),
                investDescription
            )
        )
        items.add(CreateProjectItemType.InputDateView(R.layout.item_input_view, deadLine))
        items.add(
            CreateProjectItemType.InputViewItem(
                R.layout.item_input_view,
                R.string.links.toString(),
                R.string.link_to_web.toString(),
                webUrl
            )
        )
        items.add(
            CreateProjectItemType.InputViewItem(R.layout.item_input_view, null, R.string.link_to_app.toString(), appUrl)
        )
        items.add(
            CreateProjectItemType.InputViewItem(
                R.layout.item_input_view,
                null,
                R.string.link_to_social_network.toString(),
                socialNetworkUrl
            )
        )
        notifyDataSetChanged()
    }
}
