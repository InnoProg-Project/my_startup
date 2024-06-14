package com.innoprog.android.feature.profile.profiledetails.domain.models

sealed class FeedWrapper {

    data class Idea(
        val id: String,
        val type: String,
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
    ) : FeedWrapper()

    data class News(
        val id: String,
        val type: String,
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
    ) : FeedWrapper()
}

data class Project(
    val id: String,
    val name: String,
    val shortDescription: String,
    val description: String,
    val logoFilePath: String,
    val publicationsCount: Int,
    val area: String,
    val financingStage: String,
    val deadline: String,
    val siteUrls: String,
    val documentUrls: List<String>,
    val projectAttachments: List<ProjectAttachment>
)

data class Attachment(
    val id: String,
    val filePath: String,
    val type: String
)

data class ProjectAttachment(
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
