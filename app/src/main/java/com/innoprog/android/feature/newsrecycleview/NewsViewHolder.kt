package com.innoprog.android.feature.newsrecycleview

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.innoprog.android.R
import com.innoprog.android.databinding.ItemNewsBinding
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.uikit.ImageLoadingType

class NewsViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val radius = binding.root.resources.getDimensionPixelSize(R.dimen.corner_radius_8)
    fun bind(news: News, onNewsClickListener: NewsAdapter.OnClickListener) {
        binding.apply {
            Glide
                .with(itemView)
                .load(news.coverUrl)
                .placeholder(R.drawable.news_sample)
                .centerCrop()
                .into(ivPublicationCover)

            tvPublicationTitle.text = news.title
            tvPublicationContent.text = news.content

            if (news.type == "idea") {
                ivIdea.isVisible = true
                projectCard.isVisible = false
            } else {
                ivIdea.isVisible = false
                projectCard.isVisible = true

                Glide
                    .with(itemView)
                    .load(
                        "https://img.freepik.com/free-vector/ai-technology-microchip-" +
                            "background-vector-digital-transformation-concept_53876-112222.jpg"
                    )
                    .placeholder(R.drawable.ic_placeholder_logo)
                    .centerCrop()
                    .transform(RoundedCorners(radius))
                    .into(ivProjectLogo)

                tvProjectName.text = "Искусственный интеллект"
                tvProjectDirection.text = "Искусственный интеллект"
            }

            val url = news.author.avatarUrl
            val placeholderResId = com.innoprog.android.uikit.R.drawable.ic_person
            val imageType =
                url?.let { ImageLoadingType.ImageNetwork(it, placeholderResId = placeholderResId) }
            if (imageType != null) {
                publicationAuthorAvatar.loadImage(imageType)
            }

            tvPublicationAuthorName.text = news.author.name
            tvCommentsCount.text = news.commentsCount.toString()
            tvLikesCount.text = news.likesCount.toString()

            itemView.setOnClickListener {
                onNewsClickListener.onItemClick(news)
            }
        }
    }
}
