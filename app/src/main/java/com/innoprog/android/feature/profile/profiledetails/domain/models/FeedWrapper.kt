package com.innoprog.android.feature.profile.profiledetails.domain.models

sealed interface FeedWrapper {

    data class Idea(
        val id: String,
        val author: Author,
        val projectId: String,
        val title: String,
        val content: String,
        val publishedAt: String,
        val likesCount: Int,
        val commentsCount: Int,
        val attachments: List<Attachment>,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : FeedWrapper

    data class News(
        val id: String,
        val author: Author,
        val projectId: String,
        val title: String,
        val content: String,
        val publishedAt: String,
        val likesCount: Int,
        val commentsCount: Int,
        val attachments: List<Attachment>,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : FeedWrapper
}

data class Attachment(
    val id: String,
    val filePath: String,
    val type: String
)

data class Author(
    val id: String,
    val name: String,
    val company: Company
)

data class Company(
    val name: String,
    val role: String
)
