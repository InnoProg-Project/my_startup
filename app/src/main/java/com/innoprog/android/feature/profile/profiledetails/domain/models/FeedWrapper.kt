package com.innoprog.android.feature.profile.profiledetails.domain.models

sealed interface FeedWrapper {

    val title: String
    val content: String
    val author: Author
    val commentsCount: Int
    val likesCount: Int
    val attachments: List<Attachment>?

    data class Idea(
        val id: String,
        override val author: Author,
        val projectId: String,
        override val title: String,
        override val content: String,
        val publishedAt: String,
        override val likesCount: Int,
        override val commentsCount: Int,
        override val attachments: List<Attachment>,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : FeedWrapper

    data class News(
        val id: String,
        override val author: Author,
        val projectId: String,
        override val title: String,
        override val content: String,
        val publishedAt: String,
        override val likesCount: Int,
        override val commentsCount: Int,
        override val attachments: List<Attachment>,
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
