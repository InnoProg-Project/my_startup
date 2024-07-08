package com.innoprog.android.feature.profile.profiledetails.presentation.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper

class PublicationsViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_8)

    fun bind(publication: FeedWrapper) {
        bindCommonData(publication)
        when (publication) {
            is FeedWrapper.Idea -> bindIdeaData()
            is FeedWrapper.News -> bindNewsData(publication)
        }
    }

    private fun bindCommonData(publication: FeedWrapper) {
        with(binding) {
            tvPublicationTitle.text = publication.title
            tvPublicationContent.text = publication.content
            tvPublicationAuthorName.text = publication.author.name
            tvCommentsCount.text = publication.commentsCount.toString()
            tvLikesCount.text = publication.likesCount.toString()

            val initials = publication.author.name
                .split(' ')
                .map { it.first().uppercaseChar() }
                .joinToString(separator = "", limit = 2)
            publicationAuthorAvatar.text = initials.ifBlank { "?" }

            publication.attachments?.firstOrNull()?.let { attachment ->
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

    private fun bindNewsData(publication: FeedWrapper.News) {
        with(binding) {
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
            tvProjectDirection.text = "IT"
        }
    }
}