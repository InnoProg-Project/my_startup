package com.innoprog.android.feature.profile.profiledetails.presentation.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper
import com.innoprog.android.uikit.ImageLoadingType

class PublicationsViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_8)

    fun bind(publication: FeedWrapper) {
        when (publication) {
            is FeedWrapper.Idea -> {
                binding.apply {
                    Glide
                        .with(itemView)
                        .load(publication.attachments.firstOrNull()?.filePath)
                        .placeholder(R.drawable.news_sample)
                        .centerCrop()
                        .into(ivPublicationCover)

                    tvPublicationTitle.text = publication.title
                    tvPublicationContent.text = publication.content

                    ivIdea.isVisible = true
                    projectCard.isVisible = false

                    val placeholderResId = com.innoprog.android.uikit.R.drawable.ic_person
                    val imageType = ImageLoadingType.ImageDrawable(placeholderResId)
                    publicationAuthorAvatar.loadImage(imageType)

                    tvPublicationAuthorName.text = publication.author.name
                    tvCommentsCount.text = publication.commentsCount.toString()
                    tvLikesCount.text = publication.likesCount.toString()
                }
            }

            is FeedWrapper.News -> {
                binding.apply {
                    Glide
                        .with(itemView)
                        .load("https://example.com/news_image")
                        .placeholder(R.drawable.news_sample)
                        .centerCrop()
                        .into(ivPublicationCover)

                    tvPublicationTitle.text = publication.title
                    tvPublicationContent.text = publication.content

                    ivIdea.isVisible = false
                    projectCard.isVisible = true

                    Glide
                        .with(itemView)
                        .load("https://example.com/project_logo")
                        .placeholder(R.drawable.ic_placeholder_logo)
                        .centerCrop()
                        .transform(RoundedCorners(radius))
                        .into(ivProjectLogo)

                    tvProjectName.text = "News Project"
                    tvProjectDirection.text = "News Direction"

                    val placeholderResId = com.innoprog.android.uikit.R.drawable.ic_person
                    val imageType = ImageLoadingType.ImageDrawable(placeholderResId)
                    publicationAuthorAvatar.loadImage(imageType)

                    tvPublicationAuthorName.text = publication.author.name
                    tvCommentsCount.text = publication.commentsCount.toString()
                    tvLikesCount.text = publication.likesCount.toString()
                }
            }
        }
    }
}
