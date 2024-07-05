package com.innoprog.android.feature.newsrecycleview

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType

class NewsViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_8)

    @Suppress("Detekt.LongMethod")
    fun bind(newsWithProject: NewsWithProject) {
        binding.apply {
            val news = newsWithProject.news
            val project = newsWithProject.project

            Glide
                .with(itemView)
                .load(news.coverUrl)
                .placeholder(R.drawable.news_sample)
                .centerCrop()
                .into(ivPublicationCover)

            tvPublicationTitle.text = news.title
            tvPublicationContent.text = news.content

            if (news.type == PublicationType.IDEA.value) {
                ivIdea.isVisible = true
                projectCard.isVisible = false
            } else {
                ivIdea.isVisible = false
                projectCard.isVisible = true

                Glide
                    .with(itemView)
                    .load(project?.logoUrl)
                    .placeholder(R.drawable.ic_placeholder_logo)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .into(ivProjectLogo)

                tvProjectName.text = project?.name
                tvProjectDirection.text = project?.area
            }

            tvPublicationAuthorName.text = news.author.name
            tvCommentsCount.text = news.commentsCount.toString()
            tvLikesCount.text = news.likesCount.toString()
        }
    }
}
