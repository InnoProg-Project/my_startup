package com.innoprog.android.feature.feed.newsdetails.domain.models

data class NewsDetailsModel(
    val id: String,
    val type: String,
    val author: Author,
    val projectId: String?,
    val coverUrl: List<String>?,
    val title: String,
    val content: String,
    val publishedAt: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLiked: Boolean,
    val isFavorite: Boolean,
    val comments: List<CommentModel>?
)

data class Author(
    val id: String,
    val name: String,
    val company: Company
)

data class Company(
    val companyName: String,
    val role: String
)
