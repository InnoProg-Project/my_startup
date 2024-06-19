package com.innoprog.android.feature.profile.profiledetails.domain.models

sealed class FeedWrapper {
    abstract val id: String
    abstract val type: String
    abstract val author: Author
    abstract val projectId: String
    abstract val title: String
    abstract val content: String
    abstract val publishedAt: String
    abstract val likesCount: Int
    abstract val commentsCount: Int
    abstract val attachments: List<Attachment>
    abstract val isLiked: Boolean
    abstract val isFavorite: Boolean

    data class Idea(
        override val id: String,
        override val type: String,
        override val author: Author,
        override val projectId: String,
        override val title: String,
        override val content: String,
        override val publishedAt: String,
        override val likesCount: Int,
        override val commentsCount: Int,
        override val attachments: List<Attachment>,
        override val isLiked: Boolean,
        override val isFavorite: Boolean
    ) : FeedWrapper()

    data class News(
        override val id: String,
        override val type: String,
        override val author: Author,
        override val projectId: String,
        override val title: String,
        override val content: String,
        override val publishedAt: String,
        override val likesCount: Int,
        override val commentsCount: Int,
        override val attachments: List<Attachment>,
        override val isLiked: Boolean,
        override val isFavorite: Boolean
    ) : FeedWrapper()
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
