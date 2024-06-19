package com.innoprog.android.feature.profile.profiledetails.data.dto.model

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.newsfeed.domain.models.PublicationType
import com.innoprog.android.feature.profile.profiledetails.domain.models.Attachment
import com.innoprog.android.feature.profile.profiledetails.domain.models.Author
import com.innoprog.android.feature.profile.profiledetails.domain.models.Company
import com.innoprog.android.feature.profile.profiledetails.domain.models.FeedWrapper

class FeedDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("author")
    val author: AuthorDto,
    @SerializedName("projectId")
    val projectId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("likesCount")
    val likesCount: Int,
    @SerializedName("commentsCount")
    val commentsCount: Int,
    @SerializedName("attachments")
    val attachments: List<AttachmentDto>,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("isFavorite")
    val isFavorite: Boolean
)

class AttachmentDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)

class AuthorDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("company")
    val company: CompanyDto
)

class CompanyDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String
)

fun FeedDto.mapToDomain(): FeedWrapper {
    return when (type) {
        PublicationType.NEWS.value -> {
            FeedWrapper.News(
                id = id,
                author = Author(
                    id = author.id,
                    name = author.name,
                    company = Company(
                        name = author.company.name,
                        role = author.company.role
                    )
                ),
                projectId = projectId,
                title = title,
                content = content,
                publishedAt = publishedAt,
                likesCount = likesCount,
                commentsCount = commentsCount,
                attachments = attachments.map { Attachment(it.id, it.filePath, it.type) },
                isLiked = isLiked,
                isFavorite = isFavorite
            )
        }
        else -> {
            FeedWrapper.Idea(
                id = id,
                author = Author(
                    id = author.id,
                    name = author.name,
                    company = Company(
                        name = author.company.name,
                        role = author.company.role
                    )
                ),
                projectId = projectId,
                title = title,
                content = content,
                publishedAt = publishedAt,
                likesCount = likesCount,
                commentsCount = commentsCount,
                attachments = attachments.map { Attachment(it.id, it.filePath, it.type) },
                isLiked = isLiked,
                isFavorite = isFavorite
            )
        }
    }
}

