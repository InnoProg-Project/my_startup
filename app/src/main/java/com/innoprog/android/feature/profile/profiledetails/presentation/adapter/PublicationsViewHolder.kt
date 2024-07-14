package com.innoprog.android.feature.profile.profiledetails.presentation.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWithProject
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper

class PublicationsViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_8)

    fun bind(publication: FeedWithProject) {
        bindCommonData(publication)
        when (publication.feedWrapper) {
            is FeedWrapper.Idea -> bindIdeaData()
            is FeedWrapper.News -> bindNewsData(publication)
        }
    }

    private fun bindCommonData(publication: FeedWithProject) {
        with(binding) {
            tvPublicationTitle.text = publication.feedWrapper.title
            tvPublicationContent.text = publication.feedWrapper.content
            tvPublicationAuthorName.text = publication.feedWrapper.author.name
            tvCommentsCount.text = publication.feedWrapper.commentsCount.toString()
            tvLikesCount.text = publication.feedWrapper.likesCount.toString()

            val initials = publication.feedWrapper.author.name
                .split(' ')
                .map { it.first().uppercaseChar() }
                .joinToString(separator = "", limit = 2)
            publicationAuthorAvatar.text = initials.ifBlank { "?" }

            publication.feedWrapper.attachments?.firstOrNull()?.let { attachment ->
                Glide
                    .with(itemView)
                    .load(attachment.filePath)
                    .placeholder(R.drawable.news_sample)
                    .centerCrop()
                    .into(ivPublicationCover)
            }
        }
    }

    private fun bindIdeaData() {
        with(binding) {
            ivIdea.isVisible = true
            projectCard.isVisible = false
        }
    }

    private fun bindNewsData(publication: FeedWithProject) {
        with(binding) {
            ivIdea.isVisible = false
            projectCard.isVisible = true

            Glide
                .with(itemView)
                .load(publication.project?.logoUrl)
                .placeholder(R.drawable.ic_placeholder_logo)
                .centerCrop()
                .transform(RoundedCorners(radius))
                .into(ivProjectLogo)

            tvProjectName.text = publication.project?.name
            tvProjectDirection.text = publication.project?.area
        }
    }
}