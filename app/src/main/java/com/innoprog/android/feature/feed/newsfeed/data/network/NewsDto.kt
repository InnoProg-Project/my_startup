package com.innoprog.android.feature.feed.newsfeed.data.network

import com.google.gson.annotations.SerializedName
import com.innoprog.android.feature.feed.newsfeed.domain.models.AttachmentType
import com.innoprog.android.feature.feed.newsfeed.domain.models.Author
import com.innoprog.android.feature.feed.newsfeed.domain.models.Company
import com.innoprog.android.feature.feed.newsfeed.domain.models.News

data class NewsDto(
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
    val likesCount: Long,
    @SerializedName("commentsCount")
    val commentsCount: Long,
    @SerializedName("attachments")
    val attachments: List<Attachments>,
    @SerializedName("isLiked")
    val isLiked: Boolean,
    @SerializedName("isFavorited")
    val isFavorited: Boolean,
)

data class AuthorDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("company")
    val company: CompanyDto
)

data class CompanyDto(
    @SerializedName("name")
    val companyName: String,
    @SerializedName("role")
    val role: String
)

data class Attachments(
    @SerializedName("id")
    val id: String,
    @SerializedName("filePath")
    val filePath: String,
    @SerializedName("type")
    val type: String
)

fun NewsDto.mapToNews(): News {
    return News(
        id = id,
        type = type,
        author = createAuthor(author),
        projectId = projectId,
        coverUrl = createUrls(attachments),
        title = title,
        content = content,
        publishedAt = publishedAt,
        likesCount = likesCount,
        commentsCount = commentsCount
    )
}

private fun createUrls(urlList: List<Attachments>): String? {
    return urlList.firstOrNull { it.type == AttachmentType.IMAGE.value }?.filePath
}

private fun createAuthor(authorDto: AuthorDto): Author {
    return authorDto.let {
        Author(
            id = it.id,
            name = it.name,
            company = createCompany(it.company)
        )
    }
}

private fun createCompany(companyDto: CompanyDto): Company {
    return companyDto.let {
        Company(
            companyName = it.companyName,
            role = it.role,
        )
    }
}
